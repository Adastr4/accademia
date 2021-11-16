package eu.cartsc.demone;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.apache.commons.io.IOUtils;

public class CARTOwnCloudClient extends OwnCloudClient {

    public CARTOwnCloudClient() {
        super();
        sardine = SardineFactory.begin("***", "****");
    }

    public void getFile(String path) throws Exception {
        if (path == null) return;
        path = path.replaceAll(" ", "%20");
        List<DavResource> resources = sardine.list(serverUrl() + "remote.php/webdav/" + path);
        for (DavResource res : resources) {
            if (res.getName().endsWith(".ods")) {
                downloadFile(res);
            }
        }
    }

    private void downloadFile(DavResource resource) {
        try {
            InputStream in = sardine.get(serverUrl() + resource.getHref());
            OutputStream out = new FileOutputStream(resource.getName());
            IOUtils.copy(in, out);
            in.close();
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    protected String serverUrl() {
        return "https://cart.safelocked.net/";
    }
}
