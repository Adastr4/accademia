package eu.cartsc.demone;

import com.github.sardine.SardineFactory;

public class CARTOwnCloudBridge extends OwnCloudBridge {

    public CARTOwnCloudBridge() {
        super();
        sardine = SardineFactory.begin("***", "****");
    }

    protected String serverUrl() {
        return "https://cart.safelocked.net/";
    }
}
