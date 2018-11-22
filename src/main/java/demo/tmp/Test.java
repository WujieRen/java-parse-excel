package demo.tmp;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        String[] tdmPrt = {"_id",
                "orderId",
                "appKey",
                "contractId",
                "instDate",
                "updtDate",
                "data.contactPhone",
                "data.text",
                "data.time",
                "data.type",
                "data.crawlTime"};

        String[] odsPrt = {
                "id",
                "orderid",
                "appkey",
                "contractid",
                "instdate",
                "updtdate",
                "contactphone",
                "text",
                "time",
                "type",
                "crawltime"
        };

//        writeToShUnstructed(tdmPrt, odsPrt);
    }


//    public static void writeToShUnstructed(List<String> tdmPrt, List<String> odsPrt) {
    public static void writeToShUnstructed(List<String> tdmPrt, List<String> odsPrt) {
//    public static void writeToShUnstructed(String[] tdmPrt, String[]odsPrt) {
//        int i = 1;
//        Map<Integer, Set<String>> dtMap = new TreeMap<>();
//        Set<String> srcSet = new LinkedHashSet<>();
//        Set<String> dataContent = new LinkedHashSet<>();
//        for (String str : tdmPrt) {
//            if (!str.contains(".")) {
//                srcSet.add(str);
//            } else {
//                srcSet.add("data");
//                dataContent.add(str.replaceAll("data.", ""));
//                dtMap.put(i, srcSet);
//            }
//        }
//
//        StringBuffer sb = new StringBuffer();
//        sb.append("  lateral view json_tuple(src.json");
//        add1(sb, srcSet, i);
//
//        Map<Integer, Set<String>> tmpMap = new LinkedHashMap<>();
//        Set<String> tmpSet = new LinkedHashSet<>(srcSet);
//        tmpMap.put(1, tmpSet);
////        for(Map.Entry<Integer, Set<String>> entry : tmpMap.entrySet()) {
////        int num = tmpMap.entrySet().iterator().next().getKey();
//        int num = 1;
//        boolean hasData = false;
//        int datasNum = 0;
//        for(String v : tmpSet) {
//            if(v.equals("_id")) {
//                dtMap.get(1).removeIf(item -> item.equals("_id"));
//                Set<String> tmpAddSet = new LinkedHashSet<>();
//                int tmpNum = ++i;
//                tmpAddSet.add("id");
//                dtMap.put(tmpNum, tmpAddSet);
//                sb.append("  lateral view json_tuple(t").append(num).append("._id,'\\$oid') t").append(tmpNum).append(" as id\n");
//            }
//
//            if(v.toLowerCase().contains("date") || v.toLowerCase().contains("time")) {
//                dtMap.get(1).removeIf(item -> item.equals(v));
//                Set<String> tmpAddSet = new LinkedHashSet<>();
//                int tmpNum = ++i;
//                tmpAddSet.add(v.toLowerCase());
//                dtMap.put(tmpNum, tmpAddSet);
//                sb.append("  lateral view json_tuple(t").append(num).append(".")
//                        .append(v).append(",'\\$numberLong') t")
//                        .append(tmpNum).append(" as ")
//                        .append(v.toLowerCase()).append("\n");
//
//            }
//
//            if(v.toLowerCase().equals("data")) {
//                hasData = true;
//                dtMap.get(1).removeIf(item -> item.equals(v));
//                Set<String> tmpAddSet = new LinkedHashSet<>();
//                int tmpNum = ++i;
//                tmpAddSet.add(v.toLowerCase());
//                dtMap.put(tmpNum, tmpAddSet);
//                datasNum = tmpNum;
//                sb.append("  lateral view explode(default.stringtoarray(if(t").append(num).append(".data = '[]', '[{}]', t1.data))) t").append(tmpNum).append(" as data\n");
//            }
////            }
//        }
//
//        if (hasData) {
//            Set<String> tmpAddLowerSet = new LinkedHashSet<>();
//            sb.append("  lateral view json_tuple(t"+datasNum+".data");
//            for(String str : dataContent) {
//                sb.append(",'").append(str).append("'");
//                tmpAddLowerSet.add(str.toLowerCase());
//            }
//            int tmpNum = ++i;
//            sb.append(") t").append(tmpNum).append(" as ");
//            for(String str : tmpAddLowerSet) {
//                sb.append(str).append(",");
//            }
//            int deleteIndex = sb.lastIndexOf(",");
//            if(deleteIndex != -1)
//                sb.deleteCharAt(deleteIndex).append("\n");
//            dtMap.put(tmpNum, tmpAddLowerSet);
//
//            //TODO:6 - [contactphone, text, time, type, crawltime]
//            Set<String> tmpLowerRemoveSet = new LinkedHashSet<>(tmpAddLowerSet);
//            for(String v : tmpLowerRemoveSet) {
//                if(v.contains("date") || v.contains("time")) {
////                    dtMap.get(1).removeIf(item -> item.equals(v));
//                    dtMap.get(tmpNum).removeIf(item -> item.equals(v));
//                    Set<String> tmpDataSet = new LinkedHashSet<>();
//                    int tmpDataNum = ++i;
//                    tmpDataSet.add(v);
//                    dtMap.put(tmpDataNum, tmpDataSet);
//                    sb.append("  lateral view json_tuple(t").append(tmpNum).append(".")
//                            .append(v).append(",'\\$numberLong') t")
//                            .append(tmpDataNum).append(" as ")
//                            .append(v.toLowerCase()).append("\n");
//
//                }
//            }
//
//        }
//
//        sb.append("WHERE src.day_id='${day_id}';\n");
//
//        sb.insert(0, "  ,'${curtime}' as load_time\nFROM ${st} src\n");
//        StringBuffer startSb = new StringBuffer();
//        startSb.append("SELECT \n").append("  t2.id\n");
//
//        for(String odsTmpPrt : odsPrt) {
//            for(Map.Entry<Integer, Set<String>> entry : dtMap.entrySet()) {
//                int key = entry.getKey();
//                if(key != 2 && !entry.getValue().contains("data")) {
//                    for(String value : entry.getValue()) {
//                        if(odsTmpPrt.toLowerCase().equals(value.toLowerCase())) {
//                            //TODO:这里倒是可以灵活添加odsPrt或者就用value的值，但是如果用了ods那 相对应的属性并没有在map中出现那就会导致sql报错找不到属性？？？
//                            //TODO:所以是excel表的问题
//                            startSb.append("  ,").append("t").append(key).append(".").append(value.toLowerCase()).append("\n");
//                        }
//                    }
//                }
//            }
//        }
//        sb.insert(0, startSb);
//        System.out.println(sb);
//        //TODO:遍历
//        for(Map.Entry<Integer, Set<String>> entry : dtMap.entrySet()) {
//            System.out.println(entry.getKey() + " - " + entry.getValue());
//        }

    }


    private static void add1(StringBuffer sb, Set<String> tmpSet, int i) {
        for(String str : tmpSet) {
            if(StringUtils.isNotBlank(str)) {
                sb.append(",'" + str + "'");
            }
        }

        sb.append(") t" + i + " as ");
        for(String str : tmpSet) {
            if(StringUtils.isNotBlank(str)) {
                sb.append(str.toLowerCase()+",");
            }
        }
        int deleteIndex = sb.lastIndexOf(",");
        if(deleteIndex != -1)
            sb.deleteCharAt(deleteIndex);
        sb.append("\n");
    }
}
