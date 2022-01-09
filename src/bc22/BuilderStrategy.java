package bc22;

import battlecode.common.*;

public strictfp class BuilderStrategy {
    static int turn = 0;

    static void run(RobotController rc) throws GameActionException{
        turn++;
        MapLocation me = rc.getLocation();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                MapLocation repLocation = new MapLocation(me.x + dx, me.y + dy);
                while (rc.canRepair(repLocation)) {
                    rc.repair(repLocation);
                }
            }
        }

        RobotInfo[] robots = rc.senseNearbyRobots();
        int distance = Integer.MAX_VALUE;
        MapLocation target = null;
        for(RobotInfo robot : robots){
            if(robot.getTeam().equals(rc.getTeam()) && robot.type.isBuilding()  &&robot.health < robot.type.getMaxHealth(robot.level)){
                if(rc.getLocation().distanceSquaredTo(robot.location) < distance){
                    target = robot.getLocation();
                    distance = rc.getLocation().distanceSquaredTo(robot.location);
                }

            }
        }

        if (target != null) {
            Pathing.walkTowards(rc, target);
        }

        int directionIdx = RobotPlayer.rng.nextInt(RobotPlayer.directions.length);
        Direction dir = RobotPlayer.directions[directionIdx];
        if (rc.canMove(dir)) {
            rc.move(dir);
        }

        if (rc.getTeamLeadAmount(rc.getTeam()) > 7000 && turn % 150 == 0 && rc.canBuildRobot(RobotType.LABORATORY, dir))  {
            rc.buildRobot(RobotType.LABORATORY, dir);
        } else if(rc.getTeamLeadAmount(rc.getTeam()) > 7000 && turn % 100 == 0 && rc.canBuildRobot(RobotType.WATCHTOWER, dir)){
            rc.buildRobot(RobotType.WATCHTOWER, dir);
        }
    }
}
