package net.accademia.dolibarr;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import org.junit.jupiter.api.Test;

class XLSBridgeTest {

    XLSBridge db = null;

    {
        try {
            db = new XLSBridge(new AccademiaDemoneMediator());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    void testReadXLS() {
        File iscritti = new File("/home/adastra/ISCRIZIONE WEBINAR20210224.xlsx");

        if (!iscritti.exists()) assertTrue(false);
        db.readData("/home/adastra/ISCRIZIONE WEBINAR20210224.xlsx");
    }
}
