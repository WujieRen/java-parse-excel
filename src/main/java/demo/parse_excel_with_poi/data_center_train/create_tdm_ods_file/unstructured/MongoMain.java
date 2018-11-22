package demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.unstructured;

import demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.constants.Constants;
import demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.entity.Item;
import demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.entity.Tmp;
import demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.util.ExcelUtil;
import demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.util.FileUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MongoMain {
    public static void main(String[] args) throws IOException {
//        FileUtil.deleteDir(Constants.TDM_UNSTRUCTURED_PATH);
//        FileUtil.deleteDir(Constants.ODS_UNSTRUCTURED_PATH);
//        FileUtil.deleteDir(Constants.SH_UNSTRUCTURED_PATH);

        List<String[]> tabNameRefList = getTabNameRef();
        for(String[] tabNameRef : tabNameRefList) {
            String tdmFilePath = Constants.TDM_UNSTRUCTURED_PATH.concat(tabNameRef[2]).concat(Constants.HQL_SUFFIX);
            FileUtil.createFile(tdmFilePath);
            OutputStreamWriter tdmWriter = FileUtil.getFileWriter(tdmFilePath);
//            writeToTdmUnstructed(tdmWriter, tabNameRef[2]);
            closeWriter(tdmWriter);

            String odsFilePath = Constants.ODS_UNSTRUCTURED_PATH.concat(tabNameRef[1]).concat(Constants.HQL_SUFFIX);
            FileUtil.createFile(odsFilePath);
            OutputStreamWriter odsWriter = FileUtil.getFileWriter(odsFilePath);
            writeToOdsUnstructed(odsWriter, tabNameRef[0], tabNameRef[1]);
            closeWriter(odsWriter);

            String shFilePath = Constants.SH_UNSTRUCTURED_PATH.concat(tabNameRef[0]).concat(Constants.SH_SUFFIX);
            FileUtil.createFile(shFilePath);
            OutputStreamWriter shWriter = FileUtil.getFileWriter(shFilePath);
            List<Tmp> itemList = parseForSh(tabNameRef[0]);
            writeToSh(shWriter, tabNameRef, itemList);
            closeWriter(shWriter);
        }

//        List<String[]> tabNameRef = getTabNameRef();
//        for(String[] str : tabNameRef) {
//            System.out.println(str[0]);
//            List<Tmp> outShPrt = parseForSh(str[0]);
//            List<String> tdmPrt = new ArrayList<>();
//            List<String> odsPrt = new ArrayList<>();
//            for(Tmp tmp : outShPrt) {
//                tdmPrt.add(tmp.getTdmPrt());
//                odsPrt.add(tmp.getOdsPrt());
//            }
//            Test.writeToShUnstructed(tdmPrt, odsPrt);
//        }
    }

    private static List<String[]> getTabNameRef() throws IOException {
        FileInputStream fis = ExcelUtil.getExcelStream();
        Workbook workbook = ExcelUtil.getWorkBook(fis);
        Sheet sheet =  workbook.getSheet("目录");
        List<String[]> tabNameRefList = new ArrayList<>();
//        for(int i = 96; Double.isNaN((sheet.getRow(i).getCell(0).getNumericCellValue())); i++) {
        for(int i = 96; i < 102; i++) {
            String[] tabNameRef = new String[4];
            String sheetName = sheet.getRow(i).getCell(0).getHyperlink().getAddress().replaceAll("!A1", "");
            tabNameRef[0] = sheetName;
            String odsTabName = sheet.getRow(i).getCell(1).getStringCellValue();
            tabNameRef[1] = odsTabName;
            String dsType = sheet.getRow(i).getCell(3).getStringCellValue();
            tabNameRef[3] = dsType;
            String tdmTabName = sheet.getRow(i).getCell(7).getStringCellValue();
            tabNameRef[2] = tdmTabName;
            tabNameRefList.add(tabNameRef);
        }
        return tabNameRefList;
    }

    public static List<Tmp> parseForSh(String sheetName) throws IOException {
        FileInputStream fis = ExcelUtil.getExcelStream();
        Workbook workbook = ExcelUtil.getWorkBook(fis);
        Sheet sheet = workbook.getSheet(sheetName);
        List<Tmp> cellItemList = new LinkedList<>();
        if(sheet != null) {
            for (Row nextRow : sheet) {
                int rowNum = nextRow.getRowNum();
                if (rowNum > 6) {
                    Tmp item = new Tmp();
                    item.setOdsPrt(nextRow.getCell(0).getStringCellValue());
                    item.setComment(nextRow.getCell(1).getStringCellValue());
                    item.setType(nextRow.getCell(2).getStringCellValue());
                    item.setEncrypt(nextRow.getCell(3).getStringCellValue());
                    item.setTdmPrt(nextRow.getCell(7).getStringCellValue());
                    cellItemList.add(item);
                }
            }
        }
        ExcelUtil.closeWorkBook(workbook);
        ExcelUtil.closeExcelStream(fis);
        return cellItemList;
    }

    private static void writeToSh(OutputStreamWriter writer, String[] tabNameRef, List<Tmp> itemList) throws IOException {

    }

    private static void writeToOdsUnstructed(OutputStreamWriter writer, String sheetName, String odsTabName) throws IOException {
        String absTabName = Constants.ODS_DATABASE_NAME + odsTabName;
        writer.write("DROP TABLE IF EXISTS "+ absTabName + ";\n");
        writer.write("CREATE TABLE  " + absTabName +"(\n");
        List<Item> itemList = ExcelUtil.parseOdsProperty(sheetName);
        FileUtil.writeProperty(writer, itemList);
        writer.write("\n)\n");
        String tabComment = ExcelUtil.getTabComment(sheetName);
        FileUtil.writeTabComment(writer, tabComment);
        writer.write("PARTITIONED BY (dt string COMMENT '当前时间,用于分区字段') \n"
                + "ROW FORMAT DELIMITED FIELDS TERMINATED BY '\\001' \n"
                + "STORED AS rcfile \n"
                + "LOCATION 'hdfs://mycluster/datacenter/warehouse/hive/ods/"+ odsTabName +"/';");
    }

//    private static void writeToTdmUnstructed(OutputStreamWriter writer, String tdmTabName) throws IOException {
//        String absTabName = Constants.TDM_DATABASE_NAME+tdmTabName;
//        writer.write("DROP TABLE IF EXISTS "+ absTabName +";\n");
//        writer.write("CREATE TABLE " + absTabName + "(json string) \n"
//                + "PARTITIONED by (day_id string comment '分区日期') \n"
//                + "ROW FORMAT delimited fields terminated by '\\001' \n"
//                + "STORED AS INPUTFORMAT 'org.apache.hadoop.mapred.TextInputFormat' \n"
//                + "OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat' \n"
//                + "LOCATION 'hdfs://mycluster/datacenter/warehouse/hive/tdm/" + tdmTabName + "/';");
//    }

    private static void closeWriter(OutputStreamWriter writer) throws IOException {
        writer.flush();
        writer.close();
    }

}
