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
    String[] iscritti = {};
    String[] iscrittiv2 = {
        "1DufQe4vdthJyspL6NrNKGNEfYFH2rTQR9usHMEAolCE", // 14 dicembre 2021
        "1t9TRMQ_FgU_1sljq8ysCJhht9svLoo8Gy1Moa-KBkzs", // 11 novembre 2021
        "19s7q3fr3tdGOyabGxDrZ4KCYzU8UTY-SQqDeYqYt9UM", // 14 Ottobre 2021
        "1Ew2EakRY0D9YIKDiIOVqMLEExYVngrMBqaJtTkTMqU0", // 08-15-27/07/2021 Luglio 2021
        "1XU2ODADHKR818-D5G4vZHS_OxLZdz6IBJpTGoOIymtY", // 17-22-29/06/2021 Giugno 2021
        "1N41gWtRb60nQtizq7NYHPvxk8_TJQQqSfOsSRHMyWTI", // 13-27/05/2021 Maggio 2021
        "1CjrzozfP4ZpQKSFM3Ta6na3BwwEpwD71OQvuzg2HAjk", // 15-29/04/2021 Aprile 2021
    };
    int formato[] = { 14, 11, 15, 13, 5 }; // sono cambiate le colonne del fil e di goobgle

    public AdastraDemoneMediator() {
        super();
        csvinvoicefolder = "/home/adastra/Scaricati/fatture";
        odsfolder = "";
        xlsfolder = "";

        try {
            bg = new AdastraDolibarrBridge(this);
            gtb = new AdastraGotoWebinarBridge(this);
            csvb = new CSVBridge(this);
            gs = new AdastraGoogleSheet(this);
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
        if (actual.listFiles() != null) for (File f : actual.listFiles()) {
            odb.readData(f.getAbsolutePath());
        }
        actual = new File(xlsfolder);
        if (actual.listFiles() != null) for (File f : actual.listFiles()) {
            xb.readData(f.getAbsolutePath());
        }
        for (String element : iscritti) {
            gs.getIscritti(element, null);
        }

        for (String element : iscrittiv2) {
            gs.getIscritti(element, formato);
        }

        //syncFatture();

        return clienti.size();
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

    int syncWebinar() {
        gtb.getWebinars();
        return ((AdastraDolibarrBridge) bg).insertWebinar();
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

    @Override
    protected void importTasks() {
        // TODO Auto-generated method stub

    }
}
