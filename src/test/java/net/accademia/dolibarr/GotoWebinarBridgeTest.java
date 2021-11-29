package net.accademia.dolibarr;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GotoWebinarBridgeTest {

    GotoWebinarBridge db = null;

    {
        try {
            db = new AccademiaGotoWebinarBridge(new AccademiaDemoneMediator());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    void getIscrittiTest() {
        assertTrue(db.getIscritti() != 0);
    }

    @Test
    void insertContactTest() {
        assertTrue(db.insertContacts() > 1);
    }

    @Test
    void connectGotoWebinartTest() {
        db.connectGotoWebinar();
    }

    @Test
    void getTokenTest() {
        db.getToken();
    }

    @Test
    void getAccessTest() {
        db.getAccess();
    }
}
