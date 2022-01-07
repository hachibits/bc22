package bc22;

import battlecode.common.*;

public strictfp class MinerStrategy {
    static Direction exploreDir = null;

     static void run(RobotController rc) throws GameActionException {
        if (exploreDir == null) {
            RobotPlayer.rng.setSeed(rc.getID());
            exploreDir = RobotPlayer.directions[RobotPlayer.rng.nextInt(RobotPlayer.directions.length)];
        }
        rc.setIndicatorString(exploreDir.toString());

        MapLocation me = rc.getLocation();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                MapLocation mineLocation = new MapLocation(me.x + dx, me.y + dy);
                while (rc.canMineGold(mineLocation)) {
                    rc.mineGold(mineLocation);
                }
                while (rc.canMineLead(mineLocation)) {
                    rc.mineLead(mineLocation);
                }
            }
        }

        int visionRadius = rc.getType().visionRadiusSquared;
        MapLocation[] nearbyLocs = rc.getAllLocationsWithinRadiusSquared(me, visionRadius);

        MapLocation target = null;
        int distToTarget = Integer.MAX_VALUE;
        for (MapLocation loc : nearbyLocs) {
            if (rc.senseLead(loc) > 0 || rc.senseGold(loc) > 0) {
                int distance = me.distanceSquaredTo(loc);
                if (distance < distToTarget) {
                    target = loc;
                    distToTarget = distance;
                }
            }
        }
        if (target != null) {
            Direction toMove = me.directionTo(target);
            if (rc.canMove(toMove)) {
                rc.move(toMove);
            }
        } else {
            if (rc.canMove(exploreDir)) {
                rc.move(exploreDir);
            } else if (!rc.onTheMap(rc.getLocation().add(exploreDir))) {
                exploreDir = exploreDir.opposite();
            }
        }

        int directionIdx = RobotPlayer.rng.nextInt(RobotPlayer.directions.length);
        Direction dir = RobotPlayer.directions[directionIdx];
        if (rc.canMove(dir)) {
            rc.move(dir);
        }
    }
}
