package cloud.adastraiot.demone;

import static org.junit.jupiter.api.Assertions.*;

import net.accademia.dolibarr.AccademiaDemoneMediator;
import net.accademia.dolibarr.Cliente;
import net.accademia.dolibarr.DemoneMediator;
import org.junit.jupiter.api.Test;

class AdastraDemoneMediatorTest {

    AdastraDemoneMediator dm = new AdastraDemoneMediator();

    @Test
    void testInsertInvoices() {
        fail("Not yet implemented");
    }

    @Test
    void testSyncIscrittitoDolibarr() {
        dm.SyncIscrittitoDolibarr();
    }

    @Test
    void testCreaCliente() {
        Cliente c = dm.getMe();
        dm.getClienti().add(c);
        dm.SyncIscrittitoDolibarr();
    }

    @Test
    void testImportWebinar() {
        dm.SyncIscrittitoDolibarr();
    }
}
