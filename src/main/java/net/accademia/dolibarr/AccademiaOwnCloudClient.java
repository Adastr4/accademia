package net.accademia.dolibarr;

import com.github.sardine.SardineFactory;
import eu.cartsc.demone.OwnCloudClient;

public class AccademiaOwnCloudClient extends OwnCloudClient {

    @Override
    protected String serverUrl() {
        return "https://cart.safelocked.net/";
    }

    public AccademiaOwnCloudClient() {
        super();
        sardine = SardineFactory.begin("accademia01", "4f2YDhAb");
    }

    @Override
    public void getFile(String string) {
        // TODO Auto-generated method stub

    }
}
