package eu.cartsc.demone;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CARTOwnCloudClientTest {

    CARTOwnCloudClient coct = new CARTOwnCloudClient();

    @Test
    void test() {
        try {
            coct.getFile(null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
