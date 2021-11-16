package net.accademia.dolibarr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class CSVBridge extends DataSource implements FileReaderBridge {

    String line = "";
    String splitBy = ";";

    public CSVBridge(DemoneMediator dm) {
        super(dm);
        // TODO Auto-generated constructor stub
    }

    /**
     * legge i dati dei cleinti e delle fatture dal file dell'agenzia delle entrate
     * e inserisce clienti e fatture
     */
    @Override
    public int readData(String ffile) {
        try {
            Invoice fattura = null;
            // parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(ffile, StandardCharsets.UTF_8));
            int i = 0;
            Map<String, String> json = null;
            while ((line = br.readLine()) != null) { // returns a Boolean value
                i++;
                if (i == 1) continue;
                String[] employee = line.split(splitBy); // use comma as separator

                Cliente c = new Cliente(null, employee[5].replace("\'", ""), employee[4].replace("\'", ""), "", null, ffile + i);
                dm.getClienti().add(c);
                json = new HashMap<>();

                json.put("ref", "");
                json.put("qty", "1");
                json.put("id", "");
                json.put("price", employee[6].replace(",", ".").replace("\"", ""));
                fattura =
                    new Invoice(
                        new InvoiceLine(employee[4].replace("\'", ""), json),
                        new SimpleDateFormat("dd/MM/yyyy").parse(employee[3]),
                        employee[4].replace("\'", ""),
                        IDtype.PIVA
                    );
                dm.getFatture().add(fattura);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
