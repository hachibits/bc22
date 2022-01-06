package bc22;

import battlecode.common.*;

public strictfp class Miner extends Droid {
    public Miner(RobotController rc) { super(rc); }

     public static void run(RobotController rc) throws GameActionException {
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
        int distanceToTarget = Integer.MAX_VALUE;
        for (MapLocation loc : nearbyLocs) {
            if (rc.senseLead(loc) > 0 || rc.senseGold(loc) > 0) {
                int distance = me.distanceSquaredTo(loc);
                if (distance < distanceToTarget) {
                    target = loc;
                    distanceToTarget = distance;
                }
            }
        }
        if (target != null) {
            Direction toMove = me.directionTo(target);
            if (rc.canMove(toMove)) {
                rc.move(toMove);
            }
        }

        Direction dir = directions[rng.nextInt(directions.length)];
        if (rc.canMove(dir)) {
            rc.move(dir);
        }
    }
}
