package net.accademia.dolibarr;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GotoWebinarBridgeTest {

    GotoWebinarBridge db = new GotoWebinarBridge(null);

    @Test
    void getIscrittiTest() {
        assertTrue(db.getIscritti() != 0);
    }

    @Test
    void insertContactTest() {
        assertTrue(db.insertContacts() == 1);
    }

    @Test
    void connectGotoWebinartTest() {
        db.connectGotoWebinar();
    }

    @Test
    void getTokenTest() {
        db.getToken();
    }
}
