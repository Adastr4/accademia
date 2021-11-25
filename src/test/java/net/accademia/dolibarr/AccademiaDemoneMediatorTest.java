package net.accademia.dolibarr;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AccademiaDemoneMediatorTest {

    DemoneMediator dm = new AccademiaDemoneMediator();

    @Test
    void testInsertInvoices() {
        //fail("Not yet implemented");
    }

    @Test
    void testSyncIscrittitoDolibarr() {
        assertTrue(dm.SyncIscrittitoDolibarr() > 0);
    }

    @Test
    void testGetMe() {
        //fail("Not yet implemented");
    }

    @Test
    void testAccademiaDemoneMediator() {
        //fail("Not yet implemented");
    }

    @Test
    void testsyncWebinar() {
        ((AccademiaDemoneMediator) dm).syncWebinar();
    }

    @Test
    void testGetAllRegistrantsForWebinar() {
        //fail("Not yet implemented");
    }

    @Test
    void testGetWebinars() {
        //fail("Not yet implemented");
    }

    /**
     * <ul>
     * <li> il webinar è creato su gotowebinar</li>
     * <li> caricare le mail dei potenziali clienti </li>
     * <li> gestire gli stoop mail </li>
     * <li> il webinar è creato su dolibarr</li>
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
        // assertTrue(dm.syncWebinar()>0); // OK sincronizza correttamente
        assertTrue(dm.SyncIscrittitoDolibarr() > 0);
        assertTrue(dm.syncProspect() > 0);
    }
}
