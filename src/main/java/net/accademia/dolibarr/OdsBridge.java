package net.accademia.dolibarr;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

public class OdsBridge extends DataSource {

    public OdsBridge(DemoneMediator dm) {
        super(dm);
        // TODO Auto-generated constructor stub
    }

    private String getVat(MutableCell<SpreadSheet> cellAt) {
        if (cellAt.getValue() instanceof String) return (String) cellAt.getValue();
        if (cellAt.getValue() instanceof BigDecimal) return ((BigDecimal) cellAt.getValue()).toPlainString();
        return null;
    }

    public int readODS(File file) {
        SpreadSheet spreadsheet;
        try {
            // Getting the 0th sheet for manipulation| pass sheet name as string
            if ((dm == null) || (dm.getClienti() == null)) return 0;

            spreadsheet = SpreadSheet.createFromFile(file);

            // Get row count and column count

            int nRowCount = spreadsheet.getSheet(0).getRowCount();

            // Iterating through each row of the selected sheet
            MutableCell cell = null;
            for (int nRowIndex = 1; nRowIndex < nRowCount; nRowIndex++) {
                final int currindex = nRowIndex;
                // Iterating through each column

                Cliente ctemp = new Cliente(
                    getVat(spreadsheet.getSheet(0).getCellAt(11, nRowIndex)),
                    getVat(spreadsheet.getSheet(0).getCellAt(7, nRowIndex)).replace('\"', '\''),
                    getVat(spreadsheet.getSheet(0).getCellAt(12, nRowIndex)),
                    getVat(spreadsheet.getSheet(0).getCellAt(10, nRowIndex)),
                    getVat(spreadsheet.getSheet(0).getCellAt(5, nRowIndex)),
                    file.getAbsolutePath() + ":" + nRowIndex
                );
                try {
                    Cliente c = dm.getClienti().stream().filter(cliente -> cliente.equals(ctemp)).findAny().orElse(null);
                    if (c == null) {
                        c = ctemp;
                        dm.getClienti().add(ctemp);
                    }

                    Contatto co = dm.searchContattofromCliente(
                        (String) spreadsheet.getSheet(0).getCellAt(1, currindex).getValue(),
                        (String) spreadsheet.getSheet(0).getCellAt(3, nRowIndex).getValue(),
                        (String) spreadsheet.getSheet(0).getCellAt(2, nRowIndex).getValue(),
                        ""
                    );
                    if (co == null) {
                        c
                            .getContatti()
                            .add(
                                new Contatto(
                                    (String) spreadsheet.getSheet(0).getCellAt(1, nRowIndex).getValue(),
                                    (String) spreadsheet.getSheet(0).getCellAt(2, nRowIndex).getValue(),
                                    (String) spreadsheet.getSheet(0).getCellAt(3, nRowIndex).getValue(),
                                    getVat(spreadsheet.getSheet(0).getCellAt(5, nRowIndex)),
                                    c,
                                    file.getAbsolutePath() + ":" + nRowIndex
                                )
                            );
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
