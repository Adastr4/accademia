package net.accademia.dolibarr;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class DemoneMediatorTest {

    DemoneMediator dm = new AccademiaDemoneMediator();

    @Test
    void testSyncIscrittitoDolibarr() {
        dm.SyncIscrittitoDolibarr();
    }

    @Test
    void testCreaWebinar() {
        fail("Not yet implemented");
    }

    /**
     * test generale di inserimento delle fatture
     *
     */
    @Test
    void insertInvoicesTest() {
        ((AccademiaDemoneMediator) dm).insertInvoices();
    }
}
