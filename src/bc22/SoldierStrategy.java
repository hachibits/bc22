package bc22;

import battlecode.common.*;

public strictfp class SoldierStrategy {
    static void run(RobotController rc) throws GameActionException {
        int radius = rc.getType().actionRadiusSquared;
        Team opponent = rc.getTeam().opponent();
        RobotInfo[] enemies = rc.senseNearbyRobots(radius, opponent);
        if (enemies.length > 0) {
            MapLocation toAttack = enemies[0].location;
            if (rc.canAttack(toAttack)) {
                rc.attack(toAttack);
            }
        } else {
            Direction dir = rc.getLocation().directionTo(Communication.getClosestEnemy(rc));
            if (dir != null && rc.canMove(dir)) {
                rc.move(dir);
            }
        }

        int directionIdx = RobotPlayer.rng.nextInt(RobotPlayer.directions.length);
        Direction dir = RobotPlayer.directions[directionIdx];
        if (rc.canMove(dir)) {
            rc.move(dir);
        }
    }
}
