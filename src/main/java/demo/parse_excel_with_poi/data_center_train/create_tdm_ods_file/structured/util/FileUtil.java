package demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.util;

import demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.entity.Item;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileUtil {

    public static OutputStreamWriter getFileWriter(String filePath) throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(filePath);
        return new OutputStreamWriter(fos, StandardCharsets.UTF_8);
    }

//    public static void cloeWriter(OutputStreamWriter writer) {
//
//    }

    public static void writeTabComment(OutputStreamWriter writer, String tabComment) throws IOException {
        if(StringUtils.isNotBlank(tabComment)) {
            writer.write("COMMENT '" + tabComment + "' \n");
        }
    }

//    public static void writeTdmItem(List<String[]> tabNameRef) throws IOException {
//        for(String[] nameRef : tabNameRef) {
//            //write to tdm file
//            List<Item> tdmItemList = ExcelUtil.parseTdmProperty(nameRef[0]);
//            String tdmFilePath = Constants.TDM_STRUCTURED_PATH.concat(nameRef[2]).concat(Constants.HQL_SUFFIX);
//            OutputStreamWriter tdmWriter = getFileWriter(tdmFilePath);
////            List<Item> itemList = parseSheetProperty(nameRef[0]);
//            tdmWriter.write("DROP TABLE IF EXISTS "+Constants.TDM_DATABASE_NAME+nameRef[2]+";\n");
//            tdmWriter.write("CREATE TABLE IF NOT EXISTS "+Constants.TDM_DATABASE_NAME+nameRef[2]+"(\n");
//            writeProperty(tdmWriter, tdmItemList);
//            tdmWriter.write("\n)\n");
//            String tabComment = ExcelUtil.getTabComment(nameRef[0]);
//            writeTabComment(tdmWriter, tabComment);
//            tdmWriter.write("PARTITIONED BY (day_id string COMMENT '当前时间,用于分区字段') \n"
//                    + "ROW FORMAT serde 'org.apache.hadoop.hive.contrib.serde2.MultiDelimitSerDe' \n"
//                    + "WITH serdeproperties('field.delim'='@|@') \n"
//                    + "STORED AS TEXTFILE;"
//            );
//            tdmWriter.flush();
//            tdmWriter.close();
//        }
//    }

//    public static void writeOdsItem(List<String[]> tabNameRef) throws IOException {
//        for(String[] nameRef : tabNameRef) {
//            List<Item> odsItemList = ExcelUtil.parseOdsProperty(nameRef[0]);
//            //write to ods file
//            String odsFilePath = Constants.ODS_STRUCTURED_PATH.concat(nameRef[1]).concat(Constants.HQL_SUFFIX);
//            OutputStreamWriter odsWriter = getFileWriter(odsFilePath);
//            odsWriter.write("DROP TABLE IF EXISTS "
//                    +Constants.ODS_DATABASE_NAME
//                    +nameRef[1]+";\n");
//            odsWriter.write("CREATE TABLE IF NOT EXISTS "
//                    +Constants.ODS_DATABASE_NAME
//                    +nameRef[1]+"(\n");
//            writeProperty(odsWriter, odsItemList);
//            odsWriter.write("\n)\n");
//            String tabComment = ExcelUtil.getTabComment(nameRef[0]);
//            writeTabComment(odsWriter, tabComment);
//            odsWriter.write("PARTITIONED BY (dt string COMMENT '当前时间,用于分区字段') \n"
//                    + "ROW FORMAT DELIMITED FIELDS TERMINATED BY '\\001' \n"
//                    + "STORED AS orcfile \n"
//                    + "LOCATION '" + Constants.HDFS_ODS_ROOT_PATH + nameRef[1] + "/';"
//            );
//            odsWriter.flush();
//            odsWriter.close();
//            System.out.println("-------------------------------" + nameRef[0] + "-------------------------------------");
//        }
//    }

    public static void writeProperty(OutputStreamWriter writer,List<Item> itemList) throws IOException {
        Item item;
        for(int i = 0; i < itemList.size(); i++) {
            item = itemList.get(i);
//            if(!StringUtils.isBlank(item.getKey())) {
//                if(i != itemList.size() - 1)
//                    writer.write("    " + item.toString() + ", \n");
//                else
//                    writer.write("    " + item.toString() + " \n");
//            }
            if(!StringUtils.isBlank(item.getKey())) {
                if(i == 0)
                    writer.write("    " + item.toString());
                else
                    writer.write(",\n    " + item.toString());
            }
        }
    }

//    public static void writeShFile(List<String[]> tabNameRefList) throws IOException {
//        for(String[] tabNameRef : tabNameRefList) {
//            String stName = tabNameRef[2];
//            String dtName = tabNameRef[1];
//            String scriptName = tabNameRef[0].concat(Constants.SH_SUFFIX);
//            String shFilePath = Constants.SH_STRUCTURED_PATH.concat(scriptName);
//            FileUtil.createFile(shFilePath);
//            OutputStreamWriter writer = getFileWriter(shFilePath);
//            writer.write("day_id=$1\n"
//                    + "p_log_file=$2\n"
//                    + "p_shell_file=$3\n"
//                    + "p_layer_name=$4\n"
//                    + "st=tdm."+stName+"\n"
//                    + "dt=ods_lm."+dtName+"\n\n"
//                    + "curtime=`date \"+%Y%m%d%H%M\"`\n"
//                    + "\n"
//                    + "sh ${p_shell_file} ${p_layer_name}<<! >>${p_log_file} 2>&1\n"
//                    + "  set hive.execution.engine=mr;\n"
//                    + "  add jar /usr/hdp/2.5.0.0-1245/hive/lib/hive-contrib-1.2.1000.2.5.0.0-1245.jar;\n"
//                    + "\n"
//                    + "  INSERT OVERWRITE TABLE ${dt} PARTITION(dt='${day_id}')\n"
//                    + "  SELECT\n");
//
//            List<String> itemList = ExcelUtil.parseForSh(tabNameRef[0]);
//            int itemSize = itemList.size();
//            for(int i = 0; i < itemSize; i++) {
//                if(i == 0)
//                    writer.write("    t0."+itemList.get(i)+"\n");
//                else
//                    writer.write("    ,t0."+itemList.get(i)+"\n");
//            }
//            writer.write("    ,'${curtime}' as load_time\n");
//            writer.write("  FROM ${st} t0\n"
//                    + "  WHERE t0.day_id='${day_id}' \n"
//                    + "  AND lower(t0." + itemList.get(0) + ") <> '" + itemList.get(0).toLowerCase() + "';\n"
//                    + "\n"
//                    + "ALTER TABLE ${st} DROP PARTITION (day_id='${day_id}') PURGE;\n"
//                    + "!");
//            writer.flush();
//            writer.close();
//        }
//    }

    public static void createFile(String fileName) throws IOException {
        File file = new File(fileName);
        dirProvider(file);
        if(!file.exists()) {
            file.createNewFile();
        }
    }

    private static void dirProvider(File file) {
        File parentFile = file.getParentFile();
        if(!parentFile.exists()) {
            dirProvider(parentFile);
            parentFile.mkdirs();
        }
    }

    public static void deleteDir(String dir) {
        File file = new File(dir);
        deleteDir(file);
    }

    private static void deleteDir(File file) {
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for(File childFile : files) {
                deleteDir(new File(file, childFile.getName()));
            }
        }
        file.delete();
    }
}
