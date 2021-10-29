package net.accademia.dolibarr;

import com.logmein.gotowebinar.api.common.ApiException;
import com.logmein.gotowebinar.api.model.Registrant;
import com.logmein.gotowebinar.api.model.Webinar;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

public class AccademiaDolibarrBridge extends DolibarrBridge {

    public AccademiaDolibarrBridge(AccademiaDemoneMediator dm) {
        super(dm);
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
        for (Webinar webinar : ((AccademiaDemoneMediator) dm).getWebinars()) {
            try {
                for (Registrant registrants : ((AccademiaDemoneMediator) dm).getAllRegistrantsForWebinar(webinar.getWebinarKey())) {
                    /**
                     * ci può essere una sola fattura per un determianto evento e un determianto
                     * partecipante
                     *
                     */
                    String clientid = getCodiceCliente(registrants.getLastName(), registrants.getEmail());
                    Invoice fattura = dm
                        .getFatture()
                        .stream()
                        .filter(invoice -> invoice.isIdServizio(webinar.getWebinarKey()) && invoice.isCliente(clientid))
                        .findAny()
                        .orElse(null);
                    if (fattura == null) {
                        fattura = new Invoice(webinar.getWebinarKey(), clientid, registrants.getEmail());
                        dm.getFatture().add(fattura);
                    } else fattura.partecipanti.add(registrants.getEmail());
                }
            } catch (ApiException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        for (Invoice fattura : dm.getFatture()) {
            insertInvoice(fattura);
        }
        return 0;
    }

    public int insertWebinar() {
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/products";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("DOLAPIKEY", DolibarrKey);
        String input = null;
        HttpEntity<String> entity = new HttpEntity<>(input, headers);
        ResponseEntity<String> ret = null;
        JsonParser parser = new BasicJsonParser();
        Map<String, Object> json = null;
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        try {
            for (Webinar m : ((AccademiaDemoneMediator) dm).getWebinars()) {
                input =
                    "{\"ref\":\"WEBINAR.2021." +
                    m.getWebinarKey() +
                    "\", \"label\":\"" +
                    m.getSubject() +
                    "\", \"status\":\"1\",\"status_buy\":\"1\", \"type\":\"1\" ," +
                    "\"price\": \"90.00000000\"" +
                    ",\"date_validation\": \"" +
                    sm.format(m.getTimes().get(0).getStartTime()) +
                    "\"}";
                json = parser.parseMap(input);
                try {
                    ret =
                        restTemplate.exchange(
                            insertapi + "?sqlfilters=(t.ref:like:'WEBINAR.2021." + m.getWebinarKey() + "%') ",
                            HttpMethod.GET,
                            entity,
                            String.class
                        );
                    if (ret.getStatusCode() == HttpStatus.OK) {
                        List jsonl = parser.parseList(ret.getBody());
                        String conid = (String) ((Map<?, ?>) jsonl.get(0)).get("id");
                        entity = new HttpEntity<>(input, headers);
                        ret = restTemplate.exchange(insertapi + "/" + conid, HttpMethod.PUT, entity, String.class);
                        continue;
                    }
                } catch (RestClientException e1) {
                    // webinar non esiste

                }

                ret = restTemplate.exchange(insertapi, HttpMethod.POST, entity, String.class);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 1;
    }
}
