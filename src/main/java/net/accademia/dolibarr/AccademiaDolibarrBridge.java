package net.accademia.dolibarr;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.logmein.gotowebinar.api.common.ApiException;
import com.logmein.gotowebinar.api.model.Registrant;
import com.logmein.gotowebinar.api.model.Webinar;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

public class AccademiaDolibarrBridge extends DolibarrBridge {

    public AccademiaDolibarrBridge(AccademiaDemoneMediator dm) {
        super(dm);
        DolibarrKey = "VR576iFzqo5Q4Y6CEUgz01Ag0QQmelt0";
        headers.set("DOLAPIKEY", DolibarrKey);
    }

    /**
     * A fine mese sono emesse le fatture Per tutti gli iscritti al webinar dai form
     * on line, inserire fattura alla loro azienda per tutti i webinar a cui hanno
     * partecipato in quel mese
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
        int i = 0, j = 0, k = 0;
        for (Webinar webinar : ((AccademiaDemoneMediator) dm).getWebinars()) {
            i++;
            Invoice fattura = null;
            try {
                for (Registrant registrants : ((AccademiaDemoneMediator) dm).getAllRegistrantsForWebinar(webinar.getWebinarKey())) {
                    j++;
                    /**
                     * ci può essere una sola fattura per un determianto evento e un determianto
                     * partecipante
                     *
                     */
                    Map servizio = getCodiceServizio(webinar.getWebinarKey());
                    String clientid = getCodiceCliente(registrants.getEmail());
                    fattura =
                        dm
                            .getFatture()
                            .stream()
                            .filter(invoice -> invoice.isDraft(webinar.getTimes().get(0).getStartTime(), clientid))
                            .findAny()
                            .orElse(null);
                    try {
                        if (fattura == null) {
                            fattura =
                                new Invoice(
                                    webinar.getWebinarKey(),
                                    registrants.getEmail(),
                                    webinar.getTimes().get(0).getStartTime(),
                                    clientid
                                );
                            dm.getFatture().add(fattura);
                            k++;
                        } else {
                            fattura.getInvoiceLines().add(new InvoiceLine(registrants.getEmail(), webinar.getWebinarKey()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("webinar." + i + ".parecipante." + j + ".fattura." + k);
                }
            } catch (ApiException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Invoice fattura : dm.getFatture()) {
            insertInvoice(fattura);
        }
        return 0;
    }

    /**
     * @param idservizio
     * @return
     */
    protected Map getCodiceServizio(String idservizio) {
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/products";

        entity = new HttpEntity<>(null, headers);

        if (servicesList == null) {
            try {
                ret = restTemplate.exchange(insertapi, HttpMethod.GET, entity, String.class);

                try {
                    Type listType = new TypeToken<List<Object>>() {}.getType();

                    servicesList = new Gson().fromJson(ret.getBody(), listType);
                } catch (Exception e) {}
            } catch (Exception e) {}
        }

        for (Object map : servicesList) {
            String id = (String) ((Map) map).get("email");
            if (id.equalsIgnoreCase(idservizio)) {
                return (Map) map;
            }
        }

        return null;
    }

    public int insertWebinar() {
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/products";

        String input = null;

        ResponseEntity<String> ret = null;

        Map<String, Object> json = null;
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        try {
            for (Webinar m : ((AccademiaDemoneMediator) dm).getWebinars()) {
                input =
                    "{\"ref\":\"WEBINAR." +
                    new SimpleDateFormat("yyyyMMdd").format(m.getTimes().get(0).getStartTime()) +
                    "." +
                    m.getWebinarKey() +
                    "\", \"label\":\"" +
                    m.getSubject() +
                    "\", \"status\":\"1\",\"status_buy\":\"1\", \"type\":\"1\" ," +
                    "\"price\": \"90.00000000\"" +
                    ",\"date_validation\": \"" +
                    sm.format(m.getTimes().get(0).getStartTime()) +
                    "\"}";
                json = parser.parseMap(input);
                entity = new HttpEntity<>(input, headers);
                try {
                    ret =
                        restTemplate.exchange(
                            insertapi +
                            "?sqlfilters=(t.ref:like:'WEBINAR.2021." +
                            new SimpleDateFormat("yyyyMMdd").format(m.getTimes().get(0).getStartTime()) +
                            "." +
                            m.getWebinarKey() +
                            "%') ",
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
