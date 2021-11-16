package cloud.adastraiot.demone;

import com.logmein.gotowebinar.api.common.ApiException;
import com.logmein.gotowebinar.api.model.Registrant;
import com.logmein.gotowebinar.api.model.Webinar;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.accademia.dolibarr.CSVBridge;
import net.accademia.dolibarr.Cliente;
import net.accademia.dolibarr.DemoneMediator;
import net.accademia.dolibarr.GotoWebinarBridge;
import net.accademia.dolibarr.IWebinarMediator;
import net.accademia.dolibarr.OdsBridge;

public class AdastraDemoneMediator extends DemoneMediator implements IWebinarMediator {

    List<Webinar> webinars = new ArrayList<>();
    CSVBridge csvb = null;

    public AdastraDemoneMediator() {
        super();
        csvinvoicefolder = "/home/adastra/Scaricati/fatture";
        odsfolder = "";
        xlsfolder = "";

        try {
            bg = new AdastraDolibarrBridge(this);
            gtb = new AdastraGotoWebinarBridge(this);
            csvb = new CSVBridge(this);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public int insertInvoices() {
        // gtb.getWebinars();
        return bg.insertInvoices();
    }

    @Override
    public Cliente getMe() {
        if (me == null) {
            me = new Cliente("adastrastartup@pec.it", "Adastra Startup Soc. Coop", "04315980401", "", "", "autogenerato");
            me.setId("1622");
        }
        return me;
    }

    @Override
    public int SyncIscrittitoDolibarr() {
        File actual = new File(odsfolder);
        for (File f : actual.listFiles()) {
            odb.readData(f.getAbsolutePath());
        }
        actual = new File(xlsfolder);
        for (File f : actual.listFiles()) {
            xb.readData(f.getAbsolutePath());
        }

        syncFatture();

        // syncWebinar();

        /**
         * Prima i dati legacy
         *
         */
        // odb.readData("/home/adastra/iscrizionewebinar.ods");
        // xb.readXLS(new File("/home/adastra/ISCRIZIONE WEBINAR20210224.xlsx"));
        //
        // String[] iscritti = { "10hI-OeiU1huDcO2Z0Aq6ibwVPQlz3jbG2aQhJcMn-AY",
        // "1S0m1x5j9sxZyCtflqJs8pV2NIFMrwUb3GlLhGzDRGEQ",
        // "1pAT4iJZISaSjdjWUczjpQ8Kb0p31hekvhiEBzS4yj_w",
        // "11ozxzipNGmx5GK2gLXaYFpZOlBMx7KQ30aQsuX74RWA",
        // "1xDb7EBPP2iawB24-0P_1uYVjH6pbKnFAl3VnVzP9HCU",
        // "11R9B7bB1fK0851jehQLsL4TeMEbUfywbSlb5zy49qpc",
        // "1RrSjh4wdBiJOQUJUnpOF1sWY_xwrWfu3jFnn5XVwbqM",
        //
        // };
        // String[] iscrittiv2 = { "1crjWiXjIKsT5PHkM_Nh8onbkGLf8ZRIK6VHYzMfyKuQ",
        // "1MbsoIz64GQb6IuauBfPVW6xciFGfK9Eq7ILfliherQc",
        // "11R9B7bB1fK0851jehQLsL4TeMEbUfywbSlb5zy49qpc", };
        // int formato[] = { 14, 11, 15, 13, 5 }; // sono cambiate le colonne del fil e
        // di goobgle
        //
        // for (String element : iscritti) {
        // gs.getIscritti(element, null);
        // }
        //
        // for (String element : iscrittiv2) {
        // gs.getIscritti(element, formato);
        // }
        //
        // /**
        // * Poi i dati di goto webinar
        // */
        // gtb.getIscritti();

        // bg.InsertCustomers();
        // syncWebinar();

        return 1;
    }

    public int syncFatture() {
        // clienti.add(getMe());

        File actual = new File(csvinvoicefolder);
        for (File f : actual.listFiles()) {
            csvb.readData(f.getAbsolutePath());
        }

        insertInvoices();

        return 0;
    }

    void syncWebinar() {
        gtb.getWebinars();
        ((AdastraDolibarrBridge) bg).insertWebinar();
    }

    @Override
    public List<Registrant> getAllRegistrantsForWebinar(String webinarKey) throws ApiException {
        return gtb.getAllRegistrantsForWebinar(webinarKey);
    }

    @Override
    public List<Webinar> getWebinars() {
        // TODO Auto-generated method stub
        return webinars;
    }

    @Override
    public int syncProspect() {
        // TODO Auto-generated method stub
        return 0;
    }
}
