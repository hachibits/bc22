package bc22;
import battlecode.common.*;

import java.awt.*;
import java.util.Arrays;

public strictfp class ArchonStrategy {
    static int miners = 0, soldiers = 0, builders = 0;

    static void run(RobotController rc) throws GameActionException {
        miners = Communication.getAlive(rc, RobotType.MINER);
        soldiers = Communication.getAlive(rc, RobotType.SOLDIER);
        builders = Communication.getAlive(rc, RobotType.BUILDER);
        if (miners < 5) {
            buildTowardsLowRubble(rc, RobotType.MINER);
        } else if (soldiers < 10) {
            buildTowardsLowRubble(rc, RobotType.SOLDIER);
        } else if (builders < 1) {
            buildTowardsLowRubble(rc, RobotType.BUILDER);
        } else if (miners < soldiers * (6/11) && rc.getTeamLeadAmount(rc.getTeam()) < 5000) {
            buildTowardsLowRubble(rc, RobotType.MINER);
        } else if (builders < soldiers / 30) {
            buildTowardsLowRubble(rc, RobotType.BUILDER);
        } else {
            buildTowardsLowRubble(rc, RobotType.SOLDIER);
        }
    }

    static void buildTowardsLowRubble(RobotController rc, RobotType type) throws GameActionException {
        Direction[] dirs = Arrays.copyOf(RobotPlayer.directions, RobotPlayer.directions.length);
        Arrays.sort(dirs, (a, b) -> getRubble(rc, a) - getRubble(rc, b));
        for (Direction d : dirs) {
            if (rc.canBuildRobot(type, d)) {
                rc.buildRobot(type, d);
                switch(type) {
                    case MINER: miners++; break;
                    case SOLDIER: soldiers++; break;
                    case BUILDER: builders++; break;
                    default: break;
                }
            }
        }
    }

    static int getRubble(RobotController rc, Direction d) {
        try {
            MapLocation loc = rc.getLocation().add(d);
            return rc.senseRubble(loc);
        } catch (GameActionException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
