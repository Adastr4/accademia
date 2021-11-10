package cloud.adastraiot.demone;

import java.util.List;
import java.util.Map;
import net.accademia.dolibarr.DemoneMediator;
import net.accademia.dolibarr.IDtype;
import net.accademia.dolibarr.Invoice;
import net.accademia.dolibarr.WebinarDolibarrBridge;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;

public class AdastraDolibarrBridge extends WebinarDolibarrBridge {

    @Override
    public int insertInvoices() {
        int i = 0;
        for (Invoice fattura : dm.getFatture()) {
            i++;
            verificaclienti(fattura);
            insertInvoice(fattura);
        }
        return i;
    }

    /**
     *
     * @param fattura
     */
    private void verificaclienti(Invoice fattura) {
        final String insertapi = uri + "/thirdparties";
        String conid = null;
        entity = new HttpEntity<>(null, headers);
        try {
            ret =
                restTemplate.exchange(
                    insertapi + "?sqlfilters=(t.tva_intra:like:'" + fattura.getPiva() + "%') ",
                    HttpMethod.GET,
                    entity,
                    String.class
                );
            if (ret.getStatusCode() == HttpStatus.OK) {
                List<?> jsonl = parser.parseList(ret.getBody());
                conid = (String) ((Map<?, ?>) jsonl.get(0)).get("ref");
                fattura.setClientId(conid, IDtype.IDBOLIBARR);
            }
        } catch (RestClientException e) {
            // contatto già presente
            System.err.println(e.getMessage());
        }
    }

    public AdastraDolibarrBridge(DemoneMediator dm) {
        super(dm);
        uri = "https://www.adastraiot.cloud/erp/api/index.php";
        DolibarrKey = "A6P7I24pVHD27jVz0b6owzRHs2j0aNsC";
        headers.set("DOLAPIKEY", DolibarrKey);
    }
}
