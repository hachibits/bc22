package bc22;

import battlecode.common.*;
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

    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {
        while (true) {
            turnCount += 1;
            System.out.println("Age: " + turnCount + "; Location: " + rc.getLocation());

            try {
                switch (rc.getType()) {
                    case ARCHON:     ArchonStrategy.run(rc);  break;
                    case MINER:      MinerStrategy.run(rc);   break;
                    case SOLDIER:    SoldierStrategy.run(rc); break;
                    case LABORATORY: LaboratoryStrategy.run(rc); break;
                    case WATCHTOWER: WatchTowerStrategy.run(rc); break;
                    case BUILDER: BuilderStrategy.run(rc); break;
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
