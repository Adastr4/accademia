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
            if ((dm == null) || (dm.getClienti() == null) || (dm.getContatti() == null)) return 0;

            spreadsheet = SpreadSheet.createFromFile(file);

            // Get row count and column count
            int nColCount = spreadsheet.getSheet(0).getColumnCount();
            int nRowCount = spreadsheet.getSheet(0).getRowCount();

            // Iterating through each row of the selected sheet
            MutableCell cell = null;
            for (int nRowIndex = 1; nRowIndex < nRowCount; nRowIndex++) {
                final int currindex = nRowIndex;
                // Iterating through each column

                try {
                    Cliente c = dm
                        .getClienti()
                        .stream()
                        .filter(cliente -> getVat(spreadsheet.getSheet(0).getCellAt(11, currindex)).equals(cliente.getmail()))
                        .findAny()
                        .orElse(null);
                    if (c == null) {
                        dm
                            .getClienti()
                            .add(
                                new Cliente(
                                    getVat(spreadsheet.getSheet(0).getCellAt(11, nRowIndex)),
                                    getVat(spreadsheet.getSheet(0).getCellAt(8, nRowIndex)).replace('\"', '\''),
                                    getVat(spreadsheet.getSheet(0).getCellAt(12, nRowIndex)),
                                    getVat(spreadsheet.getSheet(0).getCellAt(10, nRowIndex)),
                                    Fonte.FILE
                                )
                            );
                    }

                    Contatto co = dm
                        .getContatti()
                        .stream()
                        .filter(Contatto -> ((String) spreadsheet.getSheet(0).getCellAt(1, currindex).getValue()).equals(Contatto.getmail())
                        )
                        .findAny()
                        .orElse(null);
                    if (co == null) {
                        dm
                            .getContatti()
                            .add(
                                new Contatto(
                                    (String) spreadsheet.getSheet(0).getCellAt(1, nRowIndex).getValue(),
                                    (String) spreadsheet.getSheet(0).getCellAt(3, nRowIndex).getValue(),
                                    (String) spreadsheet.getSheet(0).getCellAt(2, nRowIndex).getValue(),
                                    getVat(spreadsheet.getSheet(0).getCellAt(12, nRowIndex)),
                                    Fonte.FILE
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
