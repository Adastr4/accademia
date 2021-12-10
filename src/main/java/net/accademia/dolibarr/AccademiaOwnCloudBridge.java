package net.accademia.dolibarr;

import com.github.sardine.SardineFactory;
import eu.cartsc.demone.OwnCloudBridge;

public class AccademiaOwnCloudBridge extends OwnCloudBridge {

    String path = "index.php/apps/files/?dir=/2021 - Accademia Europea/amministrazione/erpdata&fileid=282126";

    @Override
    protected String serverUrl() {
        return "https://cart.safelocked.net/";
    }

    public AccademiaOwnCloudBridge() {
        super();
        sardine = SardineFactory.begin("accademia01", "4f2YDhAb");
    }
}
