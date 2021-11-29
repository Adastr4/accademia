package eu.cartsc.demone;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CARTOwnCloudClientTest {

    CARTOwnCloudBridge coct = new CARTOwnCloudBridge();

    @Test
    void test() {
        try {
            coct.getFiles(null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
