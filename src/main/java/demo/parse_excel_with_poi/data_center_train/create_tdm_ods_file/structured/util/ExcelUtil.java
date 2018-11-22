package demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.util;

import demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.constants.Constants;
import demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.entity.Item;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ExcelUtil {

    static List<String> parseForSh(String sheetName) throws IOException {
        FileInputStream fis = getExcelStream();
        Workbook workbook = getWorkBook(fis);
        Sheet sheet = workbook.getSheet(sheetName);
        List<String> proList = new ArrayList<>();
        if(null != sheet) {
            for(Row row : sheet) {
                int rowNum = row.getRowNum();
                if(rowNum > 6 && rowNum < sheet.getLastRowNum()) {
                    proList.add(row.getCell(7).getStringCellValue());
                }
            }
        }
        closeWorkBook(workbook);
        closeExcelStream(fis);
        return proList;
    }

    public static List<Item> parseTdmProperty(String sheetName) throws IOException {
//        FileInputStream fis = new FileInputStream(new File(Constants.SRC_EXCEL_PATH));
//        Workbook workbook = new HSSFWorkbook(fis);
        FileInputStream fis = getExcelStream();
        Workbook workbook = getWorkBook(fis);
        Sheet firstSheet = workbook.getSheet(sheetName);
        List<Item> cellItemList = new LinkedList<>();
        if(firstSheet != null) {
            for (Row nextRow : firstSheet) {
                int rowNum = nextRow.getRowNum();
                if (rowNum > 6) {
                    Item item = new Item();
                    item.setKey(nextRow.getCell(7).getStringCellValue());
                    item.setKeyComment(nextRow.getCell(1).getStringCellValue());
                    item.setKeyType(nextRow.getCell(2).getStringCellValue());
                    cellItemList.add(item);
                }
            }
        }
        closeWorkBook(workbook);
        closeExcelStream(fis);
        return cellItemList;
    }

    public static List<Item> parseOdsProperty(String sheetName) throws IOException {
//        FileInputStream fis = new FileInputStream(new File(Constants.SRC_EXCEL_PATH));
//        Workbook workbook = new HSSFWorkbook(fis);
        FileInputStream fis = getExcelStream();
        Workbook workbook = getWorkBook(fis);
        Sheet firstSheet = workbook.getSheet(sheetName);
        List<Item> cellItemList = new LinkedList<>();
        if(firstSheet != null) {
            for (Row nextRow : firstSheet) {
                int rowNum = nextRow.getRowNum();
                if (rowNum > 6) {
                    Item item = new Item();
                    item.setKey(nextRow.getCell(0).getStringCellValue());
                    item.setKeyComment(nextRow.getCell(1).getStringCellValue());
                    item.setKeyType(nextRow.getCell(2).getStringCellValue());
                    cellItemList.add(item);
                }
            }
        }
        closeWorkBook(workbook);
        closeExcelStream(fis);
        return cellItemList;
    }

    public static String getTabComment(String sheetName) throws IOException {
        FileInputStream fis = getExcelStream();
        Workbook workbook = getWorkBook(fis);
        Sheet sheet = workbook.getSheet(sheetName);
        return sheet.getRow(4).getCell(1).getStringCellValue();
    }

    public static FileInputStream getExcelStream() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(new File(Constants.SRC_EXCEL_PATH));
//        FileInputStream fis = new FileInputStream(new File(Constants.LIXIN_SRC_TAB_PATH));
        return fis;
    }

    public static Workbook getWorkBook(FileInputStream fis) throws IOException {
        return new HSSFWorkbook(fis);
        //lixin
//        return new XSSFWorkbook(fis);
    }

    public static void closeExcelStream(FileInputStream fis) throws IOException {
        if(null != fis)
            fis.close();
    }

    public static void closeWorkBook(Workbook workbook) throws IOException {
        if(workbook != null)
            workbook.close();
    }



    public static List<String[]> parseExcel() throws IOException {
        FileInputStream fis = getExcelStream();
        Workbook workbook = getWorkBook(fis);
        Sheet contentSheet = workbook.getSheet("目录");
        Iterator<Row> iterator = contentSheet.iterator();
        List<String[]> tabReflectionList = new ArrayList<>();
        while(iterator.hasNext()) {
            Row curRow = iterator.next();
            int curRowNum = curRow.getRowNum();
            String[] tabReflection = new String[3];
//            if(curRowNum >= 45 && curRowNum < 91) {
            if(curRowNum > 146 && curRowNum < 167) {   //如果想选出(a~b)行，那么这里的范围就应该是((a-1) ~ (b+1))
                String sheetName = curRow.getCell(0).getHyperlink().getAddress().replaceAll("!A1", "");
                tabReflection[0] = sheetName;

                String odsTabName = curRow.getCell(1).getStringCellValue();
                String odsFileName = Constants.ODS_STRUCTURED_PATH.concat(odsTabName).concat(Constants.HQL_SUFFIX);
                FileUtil.createFile(odsFileName);
                tabReflection[1] = odsTabName;
                System.out.print(odsTabName + "\t");

                String tdmTabName = curRow.getCell(7).getStringCellValue();
                String tdmFileName = Constants.TDM_STRUCTURED_PATH.concat(tdmTabName).concat(Constants.HQL_SUFFIX);
                FileUtil.createFile(tdmFileName);
                tabReflection[2] = tdmTabName;
                System.out.println(tdmTabName + "\t");

                tabReflectionList.add(tabReflection);
            }
        }
        closeWorkBook(workbook);
        closeExcelStream(fis);
        return tabReflectionList;
    }
}
