package demo.parse_excel_with_poi.data_center_train.pase_version_two.util;

import demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.constants.Constants;
import demo.parse_excel_with_poi.data_center_train.pase_version_two.entity.CreateTablePropertyRef;
import demo.parse_excel_with_poi.data_center_train.pase_version_two.entity.TabNameAndCommentRef;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

public class OdsUtils {

    public static void writeOdsUnstructed(TabNameAndCommentRef ncRef) throws IOException {
        String odsTabName = ncRef.getOdsTabName();
        String absTabName = Constants.ODS_DATABASE_NAME.concat(odsTabName);
        StringBuffer sb = new StringBuffer();
        sb.append("DROP TABLE IF EXISTS ").append(absTabName).append(";\n")
                .append("CREATE TABLE  ").append(absTabName).append("(\n");
        List<CreateTablePropertyRef> odsItemList = parseOdsProperty(ncRef.getSheetName());
        CommonUtils.concatStructedProperty(sb, odsItemList);
        sb.append("\n) \n");
        CommonUtils.concatTabComment(sb, ncRef.getTabComment());
        sb.append("PARTITIONED BY (dt string COMMENT '当前时间,用于分区字段') \n"
                + "ROW FORMAT DELIMITED FIELDS TERMINATED BY '\\001' \n"
                + "STORED AS rcfile \n"
                + "LOCATION 'hdfs://mycluster/datacenter/warehouse/hive/ods/")
                .append(odsTabName).append("/';");

        String odsFilePath = Constants.ODS_UNSTRUCTURED_PATH.concat(odsTabName).concat(Constants.SQL_SUFFIX);
        OutputStreamWriter odsWriter = FileUtils.getFileWriter(odsFilePath);
        odsWriter.write(sb.toString());
        odsWriter.flush();
        odsWriter.close();
    }

    public static void writeOdsStructed(TabNameAndCommentRef ncRef) throws IOException {
        String odsTabName = ncRef.getOdsTabName();
        String absOdsTabName = Constants.ODS_DATABASE_NAME.concat(odsTabName);
        StringBuffer sb = new StringBuffer();
        sb.append("DROP TABLE IF EXISTS ").append(absOdsTabName).append(";\n")
                .append("CREATE TABLE IF NOT EXISTS ").append(absOdsTabName).append("(\n");
        List<CreateTablePropertyRef> odsItemList = parseOdsProperty(ncRef.getSheetName());
        CommonUtils.concatStructedProperty(sb, odsItemList);
        sb.append("\n) \n");
        CommonUtils.concatTabComment(sb, ncRef.getTabComment());
        sb.append("PARTITIONED BY (dt string COMMENT '当前时间,用于分区字段') \n"
                + "ROW FORMAT DELIMITED FIELDS TERMINATED BY '\\001' \n"
                + "STORED AS orcfile \n"
                + "LOCATION '")
                .append(Constants.HDFS_ODS_ROOT_PATH).append(odsTabName).append("/';");

        String odsFilePath = Constants.ODS_STRUCTURED_PATH.concat(odsTabName).concat(Constants.SQL_SUFFIX);
        OutputStreamWriter odsWriter = FileUtils.getFileWriter(odsFilePath);
        odsWriter.write(sb.toString());
        odsWriter.flush();
        odsWriter.close();
    }

    private static List<CreateTablePropertyRef> parseOdsProperty(String sheetName) throws IOException {
        FileInputStream fis = CommonUtils.getReadExcelStream();
        Workbook workbook = CommonUtils.getWorkBook(fis);
        Sheet sheet = workbook.getSheet(sheetName);
        List<CreateTablePropertyRef> tabPrtRefList = new LinkedList<>();
        if(sheet != null) {
            for (Row nextRow : sheet) {
                int rowNum = nextRow.getRowNum();
                if (rowNum > 6) {
                    CreateTablePropertyRef tabPrtRef = new CreateTablePropertyRef();
                    tabPrtRef.setPrt(nextRow.getCell(0).getStringCellValue());
                    tabPrtRef.setPrtComment(nextRow.getCell(1).getStringCellValue());
                    tabPrtRef.setType(nextRow.getCell(2).getStringCellValue());
//                    tabPrtRef.setEncrypt(nextRow.getCell(3).getStringCellValue());
                    tabPrtRefList.add(tabPrtRef);
                }
            }
        }
        workbook.close();
        fis.close();
        return tabPrtRefList;
    }

}
