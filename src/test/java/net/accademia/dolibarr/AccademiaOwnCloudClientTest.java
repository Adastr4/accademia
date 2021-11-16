package net.accademia.dolibarr;

import static org.junit.jupiter.api.Assertions.*;

import eu.cartsc.demone.CARTOwnCloudClient;
import eu.cartsc.demone.OwnCloudClient;
import org.junit.jupiter.api.Test;

class AccademiaOwnCloudClientTest {

    OwnCloudClient coct = new AccademiaOwnCloudClient();

    @Test
    void test() {
        try {
            coct.getFile("2021 - Accademia Europea/amministrazione/erpdata");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
