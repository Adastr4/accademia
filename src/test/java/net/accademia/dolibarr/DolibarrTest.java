package net.accademia.dolibarr;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.logmein.gotowebinar.api.model.Webinar;
import org.junit.jupiter.api.Test;

/**
 * Leggere i dati da gotowebinar e scrivere i dati nell'erp
 *
 * @author adastra {
 *         "access_token":"eyJraWQiOiJvYXV0aHYyLmxtaS5jb20uMDIxOSIsImFsZyI6IlJTNTEyIn0.eyJzYyI6ImNvbGxhYjoiLCJscyI6IjkwYjZmMTg4LWQ1NjItNDg3MC1hODQ1LWNiYmFhMjg0NDEwZSIsIm9nbiI6InB3ZCIsImF1ZCI6IjZkOGI1NGIwLTc4N2MtNDE4Yy1iMjBhLWUxMDA5MjQ3ZDEyNCIsInN1YiI6Ijg4OTg4NTA0OTMxNTQxOTc4ODgiLCJqdGkiOiI2YjExYmQ3OC01MzZkLTQ0OTEtYjYxNy1iNzYwMDdjMjE4MjkiLCJleHAiOjE2MzUyMDE3ODcsImlhdCI6MTYzNTE5ODE4NywidHlwIjoiYSJ9.J6zVHGba4LLJAoGxQmXdmK9WXG6PS2NpRijrG-UdT1wt4WrscaarmONxuKwB0UCNUj1bwF-ZtWMOSHi-cD2q4iNtXttBaZM9jNRLDiWMttHCIuj5TN6l1j2C8sChRhXNegv1B4mXeEA-Yyj8Oybf33YsjmFxEFSTWbqGAe2PeFyp9R_8aLjVQGjt6GoFztwggeEK95-2vjNyunuuFsrC-LXlqW3vbllQJQUDmbOSo_5zDZGWdv385YSSRb-wM6gbgiwStNvuKPhi2PDPoC53xtzuyj9-v9reU0DX4GwkH94PUeaPtlzft_CsazMa8-4LFj7mN5rEIweS_pumVESjDA",
 *         "token_type":"Bearer",
 *         "refresh_token":"eyJraWQiOiJvYXV0aHYyLmxtaS5jb20uMDIxOSIsImFsZyI6IlJTNTEyIn0.eyJzYyI6ImNvbGxhYjoiLCJscyI6IjkwYjZmMTg4LWQ1NjItNDg3MC1hODQ1LWNiYmFhMjg0NDEwZSIsIm9nbiI6InB3ZCIsImF1ZCI6IjZkOGI1NGIwLTc4N2MtNDE4Yy1iMjBhLWUxMDA5MjQ3ZDEyNCIsInN1YiI6Ijg4OTg4NTA0OTMxNTQxOTc4ODgiLCJqdGkiOiIxMWVhNGJkMS0xYTc2LTQ1NmMtYWI1ZS1mNGNkOWFkOGI1MmYiLCJleHAiOjE2Mzc3OTAxODcsImlhdCI6MTYzNTE5ODE4NywidHlwIjoiciJ9.ZevloygNydKFkLMw5Pz9DH7zFhFOJvP4FHSqYo3o8jkiZwcLjTAJt4tziMOvUnfVpryR5l_42NQzB9j5mIPciVBnRVW74_a0sUtkMwaEvl91euKBYGfdaiopmtd-4lp9Lnx_9YHEPhPuZHvhapxYYEJ7zbX_UweK4xOEVdWWBXV-MB0zjJP8BOzmO2eWuF8tR4vWuNgK3XuZWjyKB1pSqTdVTu6cYWiw31GXLUORzH0HsGJIx-9FXrAg5LIEoFkjAGSdns0nfACOGT3_7qF7lgNoQKVa2mwd4nJSxQITh-dxy13ineaB79iIyzKyXop-nbmsrn3dJLxBoRh8nFjfdw",
 *         "expires_in":3600, "account_key":"8348404185963954557",
 *         "email":"info@accademiaeuropea.net", "firstName":"Accademia",
 *         "lastName":"Europea Soc. Coop",
 *         "organizer_key":"8898850493154197888", "version":"3",
 *         "account_type":"" }
 */
public class DolibarrTest {

    AccademiaDemoneMediator am = new AccademiaDemoneMediatorMock();
    AccademiaDolibarrBridgeMock db = new AccademiaDolibarrBridgeMock(am);

    /**
     *
     * Andrea 25/10/2021 nella prima versione legge le fatture poi inserisce un
     * cliente restituisce le fatture prova
     *
     *
     */

    @Test
    void insertWebinarTest() {
        assertTrue(db.insertWebinar() == 1);
    }

    @Test
    void getCustomersTest() {
        assertTrue(db.getCustomers() == 1);
    }

    @Test
    void InsertCustomerTest() {
        Cliente c = new Cliente("vrtd10000n@pec.istruzione.it", "", "93014980234", "", Fonte.FILE);

        assertTrue(db.insertCustomer(c) == 1);
    }

    @Test
    void getFattureTest() {
        assertTrue(db.getFatture() == 1);
    }

    @Test
    void insertContactsTest() {
        db.insertContact(new Contatto("adriana.pipp4o@unito.it", "", "", "80088230018", Fonte.FILE));
    }

    @Test
    void insertInvoicesTest() {
        for (int i = 0; i < 10; i++) {
            Webinar we = new Webinar();
            we.setWebinarKey("123" + i + ".prova");
            am.getWebinars().add(we);
        }

        am.insertInvoices();
    }

    @Test
    void deleteInvoicesTest() {
        db.deleteInvoices();
    }
}
