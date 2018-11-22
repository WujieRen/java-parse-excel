package demo.parse_excel_with_poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class PoiParsingExcelDemo_1 {
    public static void main(String[] args) throws IOException {
        String excelPath = "F:/lemon/dataCenter/test.xls";
        FileInputStream fis = new FileInputStream(new File(excelPath));
        Workbook workbook = new HSSFWorkbook(fis);
        Sheet firstSheet = workbook.getSheetAt(1);
        Iterator<Row> iterator = firstSheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            int rowNum = nextRow.getRowNum();
            if(rowNum > 45 && rowNum < 91) {
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                while(cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columNum = cell.getColumnIndex();
                    if(1 == columNum || 7 == columNum) {
//                        switch (cell.getCellType()) {
//                            case STRING:
//                                System.out.print(cell.getStringCellValue() + "\t");
//                                break;
//                            case NUMERIC:
//                                System.out.print(cell.getNumericCellValue() + "\t");
//                                break;
//                        }
                        System.out.print(cell.getStringCellValue() + "\t");
                    }
                }
                System.out.println();
            }
        }

        workbook.close();
        fis.close();
    }

}
