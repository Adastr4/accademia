package net.accademia.dolibarr;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

public class OdsBridge {

    DemoneMediator dm;

    public OdsBridge(DemoneMediator demoneMediator) {
        dm = demoneMediator;
    }

    public void readODS(File file) {
        SpreadSheet spreadsheet;
        try {
            // Getting the 0th sheet for manipulation| pass sheet name as string

            spreadsheet = SpreadSheet.createFromFile(file);

            // Get row count and column count
            int nColCount = spreadsheet.getSheet(0).getColumnCount();
            int nRowCount = spreadsheet.getSheet(0).getRowCount();

            // Iterating through each row of the selected sheet
            MutableCell cell = null;
            for (int nRowIndex = 1; nRowIndex < nRowCount; nRowIndex++) {
                // Iterating through each column
                String input =
                    "{\"lastname\":\"" +
                    spreadsheet.getSheet(0).getCellAt(3, nRowIndex).getValue() +
                    "\", \"firstname\":\"" +
                    spreadsheet.getSheet(0).getCellAt(2, nRowIndex).getValue() +
                    "\", \"email\":\"" +
                    spreadsheet.getSheet(0).getCellAt(1, nRowIndex).getValue() +
                    "\"}";

                //				if (!contattoEsiste((String) spreadsheet.getSheet(0).getCellAt(1, nRowIndex).getValue()))
                //					insertContact(input, (String) spreadsheet.getSheet(0).getCellAt(1, nRowIndex).getValue());

                try {
                    input =
                        "{\"name\": \"" +
                        ((String) spreadsheet.getSheet(0).getCellAt(8, nRowIndex).getValue()).replace('\"', '\'') +
                        "\",\"client\": \"1\",\"prospect\": 0,\"fournisseur\": \"0\",	\"code_client\": \"auto\",\"email\":\"" +
                        (String) spreadsheet.getSheet(0).getCellAt(11, nRowIndex).getValue() +
                        "\",\"tva_intra\":\"" +
                        getVat(spreadsheet.getSheet(0).getCellAt(12, nRowIndex)) +
                        "\"}";
                    //					if (!customerEsiste((String) spreadsheet.getSheet(0).getCellAt(11, nRowIndex).getValue()))
                    //						insertCustomer(input, (String) spreadsheet.getSheet(0).getCellAt(11, nRowIndex).getValue());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getVat(MutableCell<SpreadSheet> cellAt) {
        if (cellAt.getValue() instanceof String) return (String) cellAt.getValue();
        if (cellAt.getValue() instanceof BigDecimal) return ((BigDecimal) cellAt.getValue()).toPlainString();
        return null;
    }
}
