package bc22;

import battlecode.common.*;

public strictfp class Pathing {
    private static final int ACCEPTABLE_RUBBLE = 25;
    private static Direction bugDir = null;

    static void walkTowards(RobotController rc, MapLocation target) throws GameActionException {
       if (!rc.isMovementReady()) {
           return;
       }

       MapLocation currentLoc = rc.getLocation();
       if (currentLoc.equals(target)) {
           return;
       }

       Direction d = currentLoc.directionTo(target);
       if (rc.canMove(d) && !isObstacle(rc, d)) {
           rc.move(d);
           bugDir = null;
       } else {
           if (bugDir == null) {
               bugDir = d;
           }

           for (int i = 0; i < 8; ++i) {
               if (rc.canMove(bugDir) && !isObstacle(rc, bugDir)) {
                   rc.move(bugDir);
                   bugDir = bugDir.rotateLeft();
                   break;
               } else {
                   bugDir = bugDir.rotateRight();
               }
           }
       }
    }

    private static boolean isObstacle(RobotController rc, Direction d) throws GameActionException {
        MapLocation adjacentLoc = rc.getLocation().add(d);
        int rubbleOnLoc = rc.senseRubble(adjacentLoc);
        return rubbleOnLoc > ACCEPTABLE_RUBBLE;
    }
}
