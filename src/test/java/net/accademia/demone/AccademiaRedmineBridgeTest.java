package net.accademia.demone;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.internal.RequestParam;
import eu.cartsc.demone.RedmineBridge;
import java.util.List;
import org.junit.jupiter.api.Test;

class AccademiaRedmineBridgeTest {

    RedmineBridge rmb = new AccademiaRedmineBridge();

    @Test
    void eseguiTicketTest() {
        assertTrue(rmb.getIssue() != null);
    }

    @Test
    void eseguiMyTicketTest() {
        List<Issue> ret = rmb.getMyIssue();
        if (ret == null) {
            assertTrue(false);
            return;
        }
        if (ret.size() == 0) {
            assertTrue(false);
            return;
        }
        try {
            ret.get(0).setStatusId(5);
            ret.get(0).setNotes("eseguito con successo");

            ret.get(0).update();
        } catch (RedmineException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
