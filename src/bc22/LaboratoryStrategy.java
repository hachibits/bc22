package bc22;

import battlecode.common.*;

public strictfp class LaboratoryStrategy {
    static void run(RobotController rc) throws GameActionException {
        if (rc.getTeamLeadAmount(rc.getTeam()) > 5000 && rc.canTransmute()) {
            rc.transmute();
        }
    }
}
