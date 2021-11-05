package net.accademia.dolibarr;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.logmein.gotowebinar.api.common.ApiException;
import com.logmein.gotowebinar.api.model.Registrant;
import com.logmein.gotowebinar.api.model.Webinar;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

public class AccademiaDolibarrBridge extends DolibarrBridge {

    public AccademiaDolibarrBridge(AccademiaDemoneMediator dm) {
        super(dm);
        uri = "https://www.accademiaeuropa.it/dolibarr/api/index.php";
        DolibarrKey = "VR576iFzqo5Q4Y6CEUgz01Ag0QQmelt0";
        headers.set("DOLAPIKEY", DolibarrKey);
    }

    /**
     * A fine mese sono emesse le fatture Per tutti gli iscritti al webinar dai form
     * on line, inserire fattura alla loro azienda per tutti i webinar a cui hanno
     * partecipato in quel mese
     *
     * non deve essere possibile inserire fatture precedenti l'ultima data di fatturazione
     * inserisci la fattura: https://www.accademiaeuropa.it/dolibarr/api/index.php/invoices
     * inserisci la linea: https://www.accademiaeuropa.it/dolibarr/api/index.php/invoices/9/lines
     * validi la fattura: https://www.accademiaeuropa.it/dolibarr/api/index.php/invoices/9/validate
     */
    @Override
    public int insertInvoices() {
        int i = 0, j = 0, k = 0;
        // TODO: verificare di non inserre fatture precedenti l'ultima data di fatturazione
        for (Webinar webinar : ((IWebinarMediator) dm).getWebinars()) {
            i++;
            Invoice fattura = null;
            try {
                for (Registrant registrants : ((IWebinarMediator) dm).getAllRegistrantsForWebinar(webinar.getWebinarKey())) {
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
                                    new InvoiceLine(registrants.getEmail(), servizio),
                                    webinar.getTimes().get(0).getStartTime(),
                                    clientid
                                );
                            dm.getFatture().add(fattura);
                            k++;
                        } else {
                            fattura.getInvoiceLines().add(new InvoiceLine(registrants.getEmail(), servizio));
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
            String id = (String) ((Map) map).get("ref");
            if (id.contains(idservizio)) {
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
            for (Webinar m : ((IWebinarMediator) dm).getWebinars()) {
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
