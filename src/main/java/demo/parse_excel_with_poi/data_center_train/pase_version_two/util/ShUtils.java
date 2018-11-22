package demo.parse_excel_with_poi.data_center_train.pase_version_two.util;

import demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.constants.Constants;
import demo.parse_excel_with_poi.data_center_train.pase_version_two.entity.LoadTablePropertyRef;
import demo.parse_excel_with_poi.data_center_train.pase_version_two.entity.TabNameAndCommentRef;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

public class ShUtils {

    public static void writeShUnstructed(List<LoadTablePropertyRef> prtRefList, TabNameAndCommentRef tnac) throws IOException {
        String shFileName = tnac.getOdsTabName();
        String absShFileName = Constants.SH_UNSTRUCTURED_PATH.concat(shFileName).concat(Constants.SH_SUFFIX);
        OutputStreamWriter writer = FileUtils.getFileWriter(absShFileName);
        StringBuffer sb = concatUnstructedShString(prtRefList, tnac);
        writer.write(sb.toString());
        writer.flush();
        writer.close();
    }

    private static StringBuffer concatUnstructedShString(List<LoadTablePropertyRef> prtRefList, TabNameAndCommentRef tnac) throws IOException {
        Map<Integer, Set<String>> dtMap = new TreeMap<>();
        Set<String> srcSet = new LinkedHashSet<>();
        Set<String> dataContentSet = new LinkedHashSet<>();
        for (LoadTablePropertyRef prt : prtRefList) {
            String tdmPrt = prt.getTdmPrt();
            if (!tdmPrt.contains(".")) {
                srcSet.add(tdmPrt);
            } else {
                srcSet.add("data");
                dataContentSet.add(tdmPrt.replaceAll("data.", ""));
            }
            dtMap.put(1, srcSet);
        }

        StringBuffer sb = new StringBuffer();
        sb.append("    lateral view json_tuple(src.json");
        addSelectTdmPrt(sb, srcSet);

        int i = 1;
        boolean hasData = false;
        Set<String> tmpSet = new LinkedHashSet<>(srcSet);
        for(String v : tmpSet) {
            if(v.equals("_id") || v.equals("id")) {
                dtMap.get(1).removeIf(item -> item.equals("_id"));
                Set<String> tmpAddSet = new LinkedHashSet<>();
                tmpAddSet.add("id");
                int tmpNum = ++i;
                dtMap.put(tmpNum, tmpAddSet);
                sb.append("    lateral view json_tuple(t1").append(".id,'\\$oid') t").append(tmpNum).append(" as id\n");
            }

            if(v.toLowerCase().contains("date") || v.toLowerCase().contains("time")) {
                dtMap.get(1).removeIf(item -> item.equals(v));
                Set<String> tmpAddSet = new LinkedHashSet<>();
                tmpAddSet.add(v.toLowerCase());
                int tmpNum = ++i;
                dtMap.put(tmpNum, tmpAddSet);
                sb.append("    lateral view json_tuple(t1").append(".")
                        .append(v).append(",'\\$numberLong') t")
                        .append(tmpNum).append(" as ")
                        .append(v.toLowerCase()).append("\n");
            }

            if(v.toLowerCase().equals("data")) {
                hasData = true;
                dtMap.get(1).removeIf(item -> item.equals(v));
                Set<String> tmpAddSet = new LinkedHashSet<>();
                tmpAddSet.add(v.toLowerCase());
                int tmpNum = ++i;
                dtMap.put(tmpNum, tmpAddSet);
                sb.append("    lateral view explode(default.stringtoarray(if(t1").append(".data = '[]', '[{}]', t1.data))) t").append(tmpNum).append(" as data\n");
            }
        }

        if (hasData) {
            Set<String> lowerContentSet = new LinkedHashSet<>();
            sb.append("    lateral view json_tuple(t").append(i).append(".data");
            for(String str : dataContentSet) {
                sb.append(", '").append(str).append("'");
                lowerContentSet.add(str.toLowerCase());
            }
            int tmpNum = ++i;
            sb.append(") t").append(tmpNum).append(" as ");
            for(String str : lowerContentSet) {
                sb.append(str).append(",");
            }
            deletextraComma(sb);
            sb.append("\n");
            dtMap.put(tmpNum, lowerContentSet);

            Set<String> tmpLowerRemoveSet = new LinkedHashSet<>(lowerContentSet);
            for(String v : tmpLowerRemoveSet) {
                if(v.contains("date") || v.contains("time")) {
                    dtMap.get(tmpNum).removeIf(item -> item.equals(v));
                    Set<String> tmpDataSet = new LinkedHashSet<>();
                    tmpDataSet.add(v);
                    int tmpDataNum = ++i;
                    dtMap.put(tmpDataNum, tmpDataSet);
                    sb.append("    lateral view json_tuple(t").append(tmpNum).append(".")
                            .append(v).append(",'\\$numberLong') t").append(tmpDataNum).append(" as ")
                            .append(v.toLowerCase()).append("\n");
                }
            }
        }
        sb.append("  WHERE src.day_id='${day_id}';\n\n")
                .append("  ALTER TABLE ${st} DROP PARTITION (day_id='${day_id}') PURGE;\n")
                .append("!");
        StringBuffer startSb = concatUnstructedShStartString(prtRefList, dtMap, tnac);
        sb.insert(0, startSb);
//        System.out.println(sb.toString());
        return sb;
    }

    private static StringBuffer concatUnstructedShStartString(List<LoadTablePropertyRef> prtRefList,
                                                              Map<Integer, Set<String>> dtMap,
                                                              TabNameAndCommentRef tnac) {
        //开头Select语句
        StringBuffer startSb = new StringBuffer();
        startSb.append("day_id=$1\n"
                + "p_log_file=$2\n"
                + "p_shell_file=$3\n"
                + "p_layer_name=$4\n"
                + "st=tdm." + tnac.getTdmTabName() + "\n"
                + "dt=ods_lm." + tnac.getOdsTabName() + "\n"
                + "\n"
                + "curtime=`date \"+%Y%m%d%H%M\"`\n"
                + "\n"
                + "sh ${p_shell_file} ${p_layer_name}<<! >>${p_log_file} 2>&1\n"
                + "  set hive.execution.engine=mr;\n"
                + "  add jar /usr/hdp/2.5.0.0-1245/hive/lib/hive-contrib-1.2.1000.2.5.0.0-1245.jar;\n"
                + "\n"
                + "  INSERT OVERWRITE TABLE ${dt} PARTITION(dt='${day_id}')\n");
        startSb.append("  SELECT \n")
                .append("    regexp_replace(t2.id, '\\\\n|\\\\t|\\\\r', '') as id\n");
        for(LoadTablePropertyRef prt : prtRefList) {
            for(Map.Entry<Integer, Set<String>> entry : dtMap.entrySet()) {
                int key = entry.getKey();
                if(key != 2 && !entry.getValue().contains("data")) {
                    for(String value : entry.getValue()) {
                        String odsPrt = prt.getOdsPrt();
                        if(odsPrt.toLowerCase().equals(value.toLowerCase())) {
                            //TODO:这里倒是可以灵活添加odsPrt或者就用value的值，但是如果用了ods那 相对应的属性并没有在map中出现那就会导致sql报错找不到属性？？？
                            //TODO:所以是excel表的问题
                            if(StringUtils.equals(prt.getEncrypt(), Constants.ENCRYPT)) {
                                startSb.append("    ,")
                                        .append("default.enycfunc(")
                                        .append("regexp_replace(")
                                        .append("t").append(key).append(".").append(value.toLowerCase())
                                        .append(", '\\\\n|\\\\t|\\\\r', '')")
                                        .append(") as ").append(value.toLowerCase())
                                        .append("\n");
                            } else {
                                startSb.append("    ,")
                                        .append("regexp_replace(")
                                        .append("t").append(key).append(".").append(value.toLowerCase())
                                        .append(", '\\\\n|\\\\t|\\\\r', '')")
                                        .append(" as ").append(value.toLowerCase())
                                        .append("\n");
                            }
                        }
                    }
                }
            }
        }
        startSb.append( "    ,'${curtime}' as load_time\n"
                + "  FROM ${st} src\n");
        return startSb;
    }

    private static void deletextraComma(StringBuffer sb) {
        int deleteIndex = sb.lastIndexOf(",");
        if(deleteIndex != -1)
            sb.deleteCharAt(deleteIndex);
    }


    private static void addSelectTdmPrt(StringBuffer sb, Set<String> tmpSet) {
        for(String str : tmpSet) {
            if(StringUtils.isNotBlank(str)) {
                sb.append(", '").append(str).append( "'");
            }
        }

        sb.append(") t1").append(" as ");
        for(String str : tmpSet) {
            if(StringUtils.isNotBlank(str)) {
                if(StringUtils.equals(str, "_id")) {
                    sb.append("id").append(",");
                } else {
                    sb.append(str.toLowerCase()).append(",");
                }
            }
        }
        deletextraComma(sb);
        sb.append("\n");
    }

    public static List<LoadTablePropertyRef> parsePropertyForUnstructedSh(String sheetName) throws IOException {
        FileInputStream fis = CommonUtils.getReadExcelStream();
        Workbook workbook = CommonUtils.getWorkBook(fis);
        Sheet sheet = workbook.getSheet(sheetName);
        List<LoadTablePropertyRef> prtRefList = new ArrayList<>();
        if(null != sheet) {
            for(Row row : sheet) {
                int rowNum = row.getRowNum();
                if(rowNum > 6 && rowNum < sheet.getLastRowNum()) {
                    LoadTablePropertyRef ltpr = new LoadTablePropertyRef();
                    ltpr.setOdsPrt(row.getCell(0).getStringCellValue());
                    ltpr.setEncrypt(row.getCell(3).getStringCellValue());
                    ltpr.setTdmPrt(row.getCell(7).getStringCellValue());
                    prtRefList.add(ltpr);
                }
            }
        }
        workbook.close();
        fis.close();
        return prtRefList;
    }

    public static void writeShStructed(TabNameAndCommentRef ncRef) throws IOException {
            String tdmTabName = ncRef.getTdmTabName();
            String odsTabName = ncRef.getOdsTabName();
            StringBuffer sb = new StringBuffer();
            sb.append("day_id=$1\n"
                        + "p_log_file=$2\n"
                        + "p_shell_file=$3\n"
                        + "p_layer_name=$4\n"
                        + "st=tdm.").append(tdmTabName).append("\n")
                    .append("dt=ods_lm.").append(odsTabName).append("\n\n")
                    .append("curtime=`date \"+%Y%m%d%H%M\"`\n").append("\n")
                    .append("sh ${p_shell_file} ${p_layer_name}<<! >>${p_log_file} 2>&1\n")
                    .append("  set hive.execution.engine=mr;\n")
                    .append("  add jar /usr/hdp/2.5.0.0-1245/hive/lib/hive-contrib-1.2.1000.2.5.0.0-1245.jar;\n")
                    .append("\n")
                    .append("  INSERT OVERWRITE TABLE ${dt} PARTITION(dt='${day_id}')\n")
                    .append("  SELECT\n");
            String sheetName = ncRef.getSheetName();
            List<String> shItemList = parsePropertyForStructedSh(sheetName);
            concatStructedShProperty(sb, shItemList);
            sb.append("    ,'${curtime}' as load_time\n");
            sb.append("  FROM ${st} t0\n"
                        + "  WHERE t0.day_id='${day_id}' \n"
                        + "  AND lower(t0.").append(shItemList.get(0)).append(") <> '").append(shItemList.get(0).toLowerCase()).append("';\n")
                    .append("\n")
                    .append("ALTER TABLE ${st} DROP PARTITION (day_id='${day_id}') PURGE;\n").append("!");

            String shScriptName = odsTabName.concat(Constants.SH_SUFFIX);
            String shFileAbsName = Constants.SH_STRUCTURED_PATH.concat(shScriptName);
            OutputStreamWriter writer = FileUtils.getFileWriter(shFileAbsName);
            writer.write(sb.toString());
            writer.flush();
            writer.close();
    }

    private static void concatStructedShProperty(StringBuffer sb, List<String> shItemList) {
        for(int i = 0; i < shItemList.size(); i++) {
            if(i == 0)
                sb.append("    t0.").append(shItemList.get(i)).append("\n");
            else
                sb.append("    ,t0.").append(shItemList.get(i)).append("\n");
        }
    }

    private static List<String> parsePropertyForStructedSh(String sheetName) throws IOException {
        FileInputStream fis = CommonUtils.getReadExcelStream();
        Workbook workbook = CommonUtils.getWorkBook(fis);
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
        workbook.close();
        fis.close();
        return proList;
    }

}
