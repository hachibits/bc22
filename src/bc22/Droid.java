package bc22;

import battlecode.common.*;
import java.util.Random;

public strictfp class Droid {
    RobotController rc;
    MapLocation loc;
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

    public Droid(RobotController rc) {
        this.rc = rc;
        this.loc = new MapLocation(0,0);
    }

    public static void run(RobotController rc) throws GameActionException {};

    public void updateLoc() {
        this.loc = this.rc.getLocation();
    }
}
