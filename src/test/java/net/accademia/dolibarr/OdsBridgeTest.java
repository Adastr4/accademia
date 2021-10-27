package net.accademia.dolibarr;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import org.junit.jupiter.api.Test;

class OdsBridgeTest {

    OdsBridge db = new OdsBridge(null);

    @Test
    void testReadODS() {
        File iscritti = new File("/home/adastra/iscrizionewebinar.ods");

        if (!iscritti.exists()) assertTrue(false);
        db.readODS(iscritti);
    }
}
