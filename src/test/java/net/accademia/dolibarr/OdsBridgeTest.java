package net.accademia.dolibarr;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import org.junit.jupiter.api.Test;

class OdsBridgeTest {

    OdsBridge db = null;

    {
        try {
            db = new OdsBridge(new AccademiaDemoneMediator());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    void testReadODS() {
        File iscritti = new File("/home/adastra/iscrizionewebinar.ods");

        if (!iscritti.exists()) assertTrue(false);
        db.readData("/home/adastra/iscrizionewebinar.ods");
    }
}
