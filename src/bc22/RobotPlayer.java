package bc22;

import battlecode.common.*;

import java.awt.*;
import java.util.Random;

public strictfp class RobotPlayer {

    static int turnCount = 0;
    static final Random rng = new Random(6147);
    static final Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };

    static RobotInfo[] nearbyEnemies = null;

    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {
        if (rc.getType() == RobotType.ARCHON) {
            final int x = rc.getLocation().x, y = rc.getLocation().y;
            Communication.reportEnemy(rc, new MapLocation(x, rc.getMapHeight() - y - 1));
            Communication.reportEnemy(rc, new MapLocation(rc.getMapWidth() - x - 1, y));
            Communication.reportEnemy(rc, new MapLocation(rc.getMapWidth() - x - 1, rc.getMapHeight() - y - 1));
        }
        while (true) {
            turnCount += 1;
            System.out.println("Age: " + turnCount + "; Location: " + rc.getLocation());

            Communication.reportAlive(rc);
            Communication.clearObsoleteEnemies(rc);

            RobotInfo[] nearbyEnemies = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
            for (RobotInfo r : nearbyEnemies) {
                Communication.reportEnemy(rc, r.location);
            }

            rc.setIndicatorString(Integer.toString(Communication.getAlive(rc, rc.getType())));

            try {
                switch (rc.getType()) {
                    case ARCHON:     ArchonStrategy.run(rc);  break;
                    case MINER:      MinerStrategy.run(rc);   break;
                    case SOLDIER:    SoldierStrategy.run(rc); break;
                    case LABORATORY: LaboratoryStrategy.run(rc); break;
                    case WATCHTOWER: WatchTowerStrategy.run(rc); break;
                    case BUILDER:    BuilderStrategy.run(rc); break;
                    case SAGE:       break;
                }
            } catch (GameActionException e) {
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();

            } catch (Exception e) {
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();

            } finally {
                Clock.yield();
            }
        }
    }
}
