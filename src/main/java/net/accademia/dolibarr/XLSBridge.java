package net.accademia.dolibarr;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSBridge extends DataSource implements FileReaderBridge {

    public XLSBridge(DemoneMediator dm) {
        super(dm);
        // TODO Auto-generated constructor stub
    }

    public int readData(String sfile) {
        File ffile = new File(sfile);
        try {
            FileInputStream file = new FileInputStream(ffile);

            // Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            // Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            // Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            if ((dm == null) || (dm.getClienti() == null)) return 0;
            boolean intestazione = true;
            int i = 0;
            while (rowIterator.hasNext()) {
                i++;
                if (intestazione) {
                    rowIterator.next(); // la prima riga ha i titoli
                    intestazione = false;
                }
                Row row = rowIterator.next();
                Cell cell = row.getCell(13);
                if (cell == null) {
                    cell = row.createCell(13);
                    cell.setCellValue("");
                }
                if (row.getCell(15).getCellType() == CellType.NUMERIC) {
                    DataFormatter formatter = new DataFormatter();
                    String strValue = formatter.formatCellValue(row.getCell(15));
                    row.getCell(15).setCellValue(strValue);
                }
                if (row.getCell(5).getCellType() == CellType.NUMERIC) {
                    DataFormatter formatter = new DataFormatter();
                    String strValue = formatter.formatCellValue(row.getCell(5));
                    row.getCell(5).setCellValue(strValue);
                }
                if (row.getCell(12).getCellType() == CellType.NUMERIC) {
                    DataFormatter formatter = new DataFormatter();
                    String strValue = formatter.formatCellValue(row.getCell(12));
                    row.getCell(12).setCellValue(strValue);
                }
                try {
                    Cliente ctemp = new Cliente(
                        row.getCell(13).getStringCellValue(),
                        row.getCell(7).getStringCellValue().replace('\"', '\''),
                        row.getCell(15).getStringCellValue(),
                        row.getCell(12).getStringCellValue(),
                        row.getCell(5).getStringCellValue(),
                        ffile.getAbsolutePath() + ":" + i
                    );

                    Cliente c = dm.getClienti().stream().filter(cliente -> cliente.equals(ctemp)).findAny().orElse(null);
                    if (c == null) {
                        c = ctemp;
                        dm.getClienti().add(c);
                    }

                    Contatto co = c
                        .getContatti()
                        .stream()
                        .filter(Contatto -> (row.getCell(1).getStringCellValue()).equals(Contatto.getmail()))
                        .findAny()
                        .orElse(null);
                    if (co == null) {
                        c
                            .getContatti()
                            .add(
                                new Contatto(
                                    row.getCell(1).getStringCellValue(),
                                    row.getCell(2).getStringCellValue(),
                                    row.getCell(3).getStringCellValue(),
                                    row.getCell(5).getStringCellValue(),
                                    c,
                                    ffile.getAbsolutePath() + ":" + i
                                )
                            );
                    }
                } catch (Exception e) {
                    System.err.println("riga" + row.getRowNum());
                    e.printStackTrace();
                }
            }
            file.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
