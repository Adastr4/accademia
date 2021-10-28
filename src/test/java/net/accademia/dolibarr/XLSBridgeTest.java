package net.accademia.dolibarr;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import org.junit.jupiter.api.Test;

class XLSBridgeTest {

    XLSBridge db = new XLSBridge(new AccademiaDemoneMediator());

    @Test
    void testReadXLS() {
        File iscritti = new File("/home/adastra/ISCRIZIONE WEBINAR20210224.xlsx");

        if (!iscritti.exists()) assertTrue(false);
        db.readXLS(iscritti);
    }
}
