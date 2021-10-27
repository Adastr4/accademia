package net.accademia.dolibarr;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DemoneMediatorTest {

    DemoneMediator dm = new DemoneMediator();

    @Test
    void testSyncIscrittitoDolibarr() {
        dm.SyncIscrittitoDolibarr();
    }

    @Test
    void testCreaWebinar() {
        fail("Not yet implemented");
    }
}
