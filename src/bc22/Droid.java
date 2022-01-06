package bc22;

import battlecode.common.*;
import java.util.Random;

public strictfp class Droid {
    RobotController rc;
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
    }

    public static void run(RobotController rc) throws GameActionException {};
}
