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

public class TdmUtils {

    public static void writeTdmUnstructed(TabNameAndCommentRef ncRef) throws IOException {
        String tdmTabName = ncRef.getTdmTabName();
        String absTdmTabName = Constants.TDM_DATABASE_NAME.concat(tdmTabName);
        StringBuffer sb = new StringBuffer();
        sb.append("DROP TABLE IF EXISTS ").append(absTdmTabName).append(";\n");
        sb.append("CREATE TABLE ").append(absTdmTabName).append("(json string) \n")
                .append("PARTITIONED by (day_id string comment '分区日期') \n")
                .append("ROW FORMAT delimited fields terminated by '\\001' \n")
                .append("STORED AS INPUTFORMAT 'org.apache.hadoop.mapred.TextInputFormat' \n")
                .append("OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat' \n")
                .append("LOCATION 'hdfs://mycluster/datacenter/warehouse/hive/tdm/").append(tdmTabName).append("/';");

        String unSturctedTdmFilePath = Constants.TDM_UNSTRUCTURED_PATH.concat(tdmTabName).concat(Constants.SQL_SUFFIX);
        OutputStreamWriter writer = FileUtils.getFileWriter(unSturctedTdmFilePath);
        writer.write(sb.toString());
        writer.close();
    }

    public static void writeTdmStructed(TabNameAndCommentRef ncRef) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("DROP TABLE IF EXISTS ").append(Constants.TDM_DATABASE_NAME).append(ncRef.getTdmTabName()).append(";\n")
                .append("CREATE TABLE IF NOT EXISTS ").append(Constants.TDM_DATABASE_NAME).append(ncRef.getTdmTabName()).append("(\n");
        List<CreateTablePropertyRef> tdmPrtList = parseTdmProperty(ncRef.getSheetName());
        CommonUtils.concatStructedProperty(sb, tdmPrtList);
        sb.append("\n) \n");
        CommonUtils.concatTabComment(sb, ncRef.getTabComment());
        sb.append("PARTITIONED BY (day_id string COMMENT '当前时间,用于分区字段') \n"
                + "ROW FORMAT serde 'org.apache.hadoop.hive.contrib.serde2.MultiDelimitSerDe' \n"
                + "WITH serdeproperties('field.delim'='@|@') \n"
                + "STORED AS TEXTFILE;");

        String tdmFilePath = Constants.TDM_STRUCTURED_PATH.concat(ncRef.getTdmTabName()).concat(Constants.SQL_SUFFIX);
        OutputStreamWriter tdmWriter = FileUtils.getFileWriter(tdmFilePath);
        tdmWriter.write(sb.toString());
        tdmWriter.flush();
        tdmWriter.close();
    }

    private static List<CreateTablePropertyRef> parseTdmProperty(String sheetName) throws IOException {
        FileInputStream fis = CommonUtils.getReadExcelStream();
        Workbook workbook = CommonUtils.getWorkBook(fis);
        Sheet sheet = workbook.getSheet(sheetName);
        List<CreateTablePropertyRef> tabPrtRefList = new LinkedList<>();
        if(sheet != null) {
            for (Row nextRow : sheet) {
                int rowNum = nextRow.getRowNum();
                if (rowNum > 6) {
                    CreateTablePropertyRef tabPrtRef = new CreateTablePropertyRef();
                    tabPrtRef.setPrtComment(nextRow.getCell(1).getStringCellValue());
                    tabPrtRef.setType(nextRow.getCell(2).getStringCellValue());
//                    tabPrtRef.setEncrypt(nextRow.getCell(3).getStringCellValue());
                    tabPrtRef.setPrt(nextRow.getCell(7).getStringCellValue());
                    tabPrtRefList.add(tabPrtRef);
                }
            }
        }
        workbook.close();
        fis.close();
        return tabPrtRefList;
    }

}
