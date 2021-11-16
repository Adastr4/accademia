package eu.cartsc.demone;

import com.github.sardine.Sardine;

public abstract class OwnCloudClient {

    protected Sardine sardine = null;

    protected abstract String serverUrl();

    public abstract void getFile(String string) throws Exception;
}
