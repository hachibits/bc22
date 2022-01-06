package bc22;

import battlecode.common.*;

public strictfp class RobotPlayer {

    static int turnCount = 0;

    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {
        while (true) {
            turnCount += 1;
            System.out.println("Age: " + turnCount + "; Location: " + rc.getLocation());

            try {
                switch (rc.getType()) {
                    case ARCHON:     Archon.run(rc);  break;
                    case MINER:      Miner.run(rc);   break;
                    case SOLDIER:    Soldier.run(rc); break;
                    case LABORATORY:
                    case WATCHTOWER:
                    case BUILDER:
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
