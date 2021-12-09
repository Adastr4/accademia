package eu.cartsc.demone;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RedmineBridgeTest {

    RedmineBridge rmb = new CARTRedmineBridge();

    @Test
    void getIssueTest() {
        assertTrue(rmb.getIssue() != null);
    }

    @Test
    void getgetTimeEntriesTest() {
        assertTrue(rmb.getTimeEntries() != null);
    }
}
