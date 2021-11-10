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
        dm.syncWebinar();
    }

    @Test
    void testSyncFatture() {
        dm.syncFatture();
    }

    /**
     * <ul>
     * <li> caricare le mail dei potenziali clienti </li>
     * <li> gestire gli stoop mail </li>
     * <li> il webinar è creato su dolibarr</li>
     * <li> il webinar è creato su gotowebinar</li>
     * <li> viene creata la form</li>
     * <li> viene creata la locandina </li>
     * <li> viene creata la timeline dell'evneto nel sito</li>
     * <li> viene creare l'item da vendere sull'ecommerce</li>
     * <li> viene creata mail</li>
     * <li> la mail è inviata a tutti i partecipanti</li>
     * <li> ogni iscritto è inserito su gotowebinar</li>
     * <li> ad ogni iscritto è creata la prefattura</li>
     * <li> ad ogni iscritto è creato l'attestato</li>
     * <li> i dati della banca sono importati periodicamente per la chiusura delle partite</li>
     * <li> creare un link di cancellazione su  mail jet</li>
     * <li> se dopo la scadenza la fattura non è incassata viene generato un insoluto e un ticket su redmine</li>
     * </ul>
     *
     *
     * */
    @Test
    void testworkflow() {
        dm.syncProspect();
    }
}
