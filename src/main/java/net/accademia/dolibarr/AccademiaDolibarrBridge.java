package net.accademia.dolibarr;

import com.logmein.gotowebinar.api.common.ApiException;
import com.logmein.gotowebinar.api.model.Registrant;
import com.logmein.gotowebinar.api.model.Webinar;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class AccademiaDolibarrBridge extends DolibarrBridge {

    public AccademiaDolibarrBridge(AccademiaDemoneMediator dm) {
        super(dm);
    }

    public int insertWebinar() {
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/products";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("DOLAPIKEY", DolibarrKey);

        try {
            for (Webinar m : ((AccademiaDemoneMediator) dm).getWebinars()) {
                String input =
                    "{\"ref\":\"WEBINAR.2021." +
                    m.getWebinarKey() +
                    "\", \"label\":\"" +
                    m.getSubject() +
                    "\", \"status\":\"1\",\"status_buy\":\"1\", \"type\":\"1\" }";

                HttpEntity<String> entity = new HttpEntity<>(input, headers);

                ResponseEntity<String> ret = restTemplate.exchange(insertapi, HttpMethod.POST, entity, String.class);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * Per tutti gli iscritti al webinar dai form on line, inserire fattura alla
     * loro azienda
     *
     *
     * inserisci la fattura
     * https://www.accademiaeuropa.it/dolibarr/api/index.php/invoices {@code
     *
     *       "socid": "574",
     *       "type": "0",
     *       "date": 1614639600,
     *       "paye": "1"
     *       }
     *
     *
     * inserisci la linea
     * https://www.accademiaeuropa.it/dolibarr/api/index.php/invoices/9/lines
     *
     * {@code { "ref": "WEBINAR.2021.1019932687784993804", "qty": "1", "fk_product":
     * "12" } }
     *
     * validi la fattura
     * https://www.accademiaeuropa.it/dolibarr/api/index.php/invoices/9/validate
     */
    @Override
    public int insertInvoices() {
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/products";
        for (Webinar webinar : ((AccademiaDemoneMediator) dm).getWebinars()) {
            try {
                for (Registrant registrants : ((AccademiaDemoneMediator) dm).getAllRegistrantsForWebinar(webinar.getWebinarKey())) {
                    Invoice fattura = dm
                        .getFatture()
                        .stream()
                        .filter(invoice -> invoice.isIdServizio(webinar.getWebinarKey()) && invoice.isPartecipante(registrants.getEmail()))
                        .findAny()
                        .orElse(null);
                    if (fattura == null) {
                        dm.getFatture().add(new Invoice(webinar.getWebinarKey(), registrants.getEmail()));
                    }
                }
            } catch (ApiException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        for (Invoice fattura : dm.getFatture()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);

            headers.set("DOLAPIKEY", DolibarrKey);

            try {
                for (Webinar m : ((AccademiaDemoneMediator) dm).getWebinars()) {
                    String input =
                        "{\"ref\":\"WEBINAR.2021." +
                        m.getWebinarKey() +
                        "\", \"label\":\"" +
                        m.getSubject() +
                        "\", \"status\":\"1\",\"status_buy\":\"1\", \"type\":\"1\" }";

                    HttpEntity<String> entity = new HttpEntity<>(input, headers);

                    ResponseEntity<String> ret = restTemplate.exchange(insertapi, HttpMethod.POST, entity, String.class);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    int insertInvoice(Invoice invoice) {
        return 0;
    }
}
