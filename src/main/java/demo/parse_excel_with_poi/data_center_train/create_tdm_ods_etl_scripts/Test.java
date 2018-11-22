package demo.parse_excel_with_poi.data_center_train.create_tdm_ods_etl_scripts;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {

//
//        String[] tmpStrArr = {"_id",
//                "orderId",
//                "appKey",
//                "a",
//                "contractId",
//                "instDate",
//                "c",
//                "updtDate",
//                "b",
//                "data.contactPhone",
//                "data.text",
//                "data.time",
//                "data.type",
//                "data.crawlTime"};
//
//        for(String str : tmpStrArr) {
//            if(str.equals("_id")) {
//                System.out.println("lateral view json_tuple(t1.id,'\\$oid') t2 as id");
//            }
//        }
//
//
//
//        int i = 0;
//        Map<Integer, Set<String>> dtMap = new TreeMap<>();
//        Set<String> tmpSet = new LinkedHashSet<>();
//        Set<String> dataContent = new LinkedHashSet<>();
//        for(String str : tmpStrArr) {
//            if(!str.contains(".")) {
//                tmpSet.add(str);
//            } else{
//                tmpSet.add("data");
//                dataContent.add(str.replaceAll("data.",""));
//                dtMap.put(i, tmpSet);
//            }
//        }
//
////        dtMap = executeMap(dtMap, i);
//        Map<Integer, Set<String>> tmpMap = new LinkedHashMap<>(dtMap);
//        for(Map.Entry<Integer, Set<String>> entry : tmpMap.entrySet()) {
//            for(String str : entry.getValue()) {
//                String tmpStr = str.toLowerCase();
//                if(str.equals("id") || str.equals("_id")) {
//                    Set<String> childSet = new LinkedHashSet<>();
//                    i++;
//                    childSet.add("id");
//                    dtMap.put(i, childSet);
//                } else if (tmpStr.contains("time") || tmpStr.contains("date")) {
//                    Set<String> childSet = new LinkedHashSet<>();
//                    i++;
//                    childSet.add(str.toLowerCase());
//                    dtMap.put(i, childSet);
//                } else if(tmpStr.equals("data")) {
//                    Set<String> childSet = new LinkedHashSet<>();
//                    i++;
//                    childSet.add(tmpStr);
//                    dtMap.put(i, childSet);
////                    for(String dataStr : dataContent) {
////                        Set<String> dataSet = new LinkedHashSet<>();
////                        dataSet.add(dataStr.toLowerCase());
////
////                    }
//                }
//            }
//        }
//
//        i++;
//        Set<String> dataSet = new LinkedHashSet<>();
//        dataContent.forEach(item -> dataSet.add(item.toLowerCase()));
//        dtMap.put(i, dataSet);
//
//        for(Map.Entry<Integer, Set<String>> entry : dtMap.entrySet()) {
//            if(entry.getKey() == i) {
//                for(String tmpStr : entry.getValue()) {
//                    if (tmpStr.contains("time") || tmpStr.contains("date")) {
//                        Set<String> childSet = new LinkedHashSet<>();
//                        i++;
//                        childSet.add(tmpStr.toLowerCase());
//                        dtMap.put(i, childSet);
//                    }
//                }
//            }
//        }
//
//        for(Map.Entry<Integer, Set<String>> entry : dtMap.entrySet()) {
//            if(entry.getKey() == 0) {
//                System.out.print("later view json_tuple(src.json\n");
//                prtItem(entry.getValue());
//                System.out.print(") t"+entry.getKey()+" as \n");
//                prtItemLowerCase(entry.getValue());
//            }
//
//            System.out.print();
//
//
//            System.out.println(entry.getKey() + " - " + entry.getValue());
//        }

//        FileInputStream fis = new FileInputStream(new File(Constants.SRC_EXCEL_PATH));
//        Workbook workbook = new HSSFWorkbook(fis);
////        System.out.println(workbook.getAllNames().get(0).getSheetName());
//        Iterator<Sheet> iterator = workbook.iterator();
//        while(iterator.hasNext()) {
//            System.out.println(iterator.next().getSheetName());
//            Sheet sheet = iterator.next();
//            if(!"目录".equals(sheet.getSheetName())
//                    && !StringUtils.equals(sheet.getSheetName(), "更新日志")
//                    && !StringUtils.equals(sheet.getSheetName(), "模板")) {
//                System.out.print(sheet.getRow(1).getCell(1).getStringCellValue() + "\t");
//                System.out.println(sheet.getRow(2).getCell(1).getStringCellValue());
//            }
//            System.out.println(sheet.getRow(0).getCell(1).getStringCellValue());
//            String sheetName = sheet.getSheetName();
//            System.out.println(sheetName);
//        }
//        System.out.println(workbook.getSheetAt(1).getRow(45).getCell(1).getStringCellValue());
//        System.out.println(workbook.getSheetAt(1).getLastRowNum());
//        List<XSSFHyperlink> hyperLinkList = (List<XSSFHyperlink>) workbook.getSheetAt(1).getHyperlinkList();
//        for(Hyperlink link : hyperLinkList) {
//            System.out.println(link.getLabel());
//            System.out.println(link.getAddress().replaceAll("!A1", ""));
//        }
//        Sheet sheet = workbook.getSheetAt(1);
//        Row row = sheet.getRow(1);
//        System.out.println(row.getCell(0).getHyperlink().getAddress().replaceAll("!A1", ""));
//        System.out.println(row.getCell(1));
//        System.out.println(row.getCell(7));
//        Sheet sheet = workbook.getSheetAt(1);
//        Iterator<Row> iterator = sheet.iterator();
//        List<String[]> tabReflectionList = new ArrayList<>();
//        while(iterator.hasNext()) {
//            Row curRow = iterator.next();
//            int curRowNum = curRow.getRowNum();
//            String[] tabReflection = new String[3];
//            if(curRowNum >= 45 && curRowNum < 91) {
//                String sheetName = curRow.getCell(0).getHyperlink().getAddress().replaceAll("!A1", "");
//                tabReflection[0] = sheetName;
//                String odsTabName = curRow.getCell(1).getStringCellValue();
//                tabReflection[1] = odsTabName;
//                String tdmTabName = curRow.getCell(7).getStringCellValue();
//                tabReflection[2] = tdmTabName;
//                tabReflectionList.add(tabReflection);
//            }
//        }

//       for(String[] item : tabReflectionList) {
//           System.out.println(Arrays.toString(item));
//       }
//        String[] item = tabReflectionList.get(0);
//        String sheetName = item[0];
//        Sheet childSheet = workbook.getSheet(sheetName);
//        if(childSheet != null) {
//            for (Row nextRow : childSheet) {
//                int rowNum = nextRow.getRowNum();
//                if (rowNum > 6) {
//                    System.out.print(nextRow.getCell(0).getStringCellValue() + "\t");
//                    System.out.print(nextRow.getCell(1).getStringCellValue() + "\t");
//                    System.out.print(nextRow.getCell(2).getStringCellValue() + "\t");
//                    System.out.print(nextRow.getCell(7).getStringCellValue() + "\t");
//                }
//                System.out.println();
//            }
//        }

//        Sheet sheet = workbook.getSheet("ods_rm_bg_sys_role");
//        String value = sheet.getRow(4).getCell(1).getStringCellValue();
//        System.out.println(value);
//        System.out.println(value);
//        System.out.println(sheet.getNumMergedRegions());
//        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
//        for(CellRangeAddress region : mergedRegions) {
//            System.out.println(region.formatAsString());
//        }

    }

//    private static void prtItem(Set<String> setString) {
//        for(String str : setString) {
//            System.out.print("    ,"+str+"\n");
//        }
//    }
//
//    private static void prtItemLowerCase(Set<String> setString) {
//        for(String str : setString) {
//            System.out.print("    ,"+str.toLowerCase()+"\n");
//        }
//    }

//    private static Map<Integer, Set<String>> executeMap(Map<Integer, Set<String>> dtMap, int i) {
//        Map<Integer, Set<String>> tmpMap = new LinkedHashMap<>(dtMap);
//        for(Map.Entry<Integer, Set<String>> entry : tmpMap.entrySet()) {
//            for(String str : entry.getValue()) {
//                String tmpStr = str.toLowerCase();
//                if(str.equals("id") || str.equals("_id")) {
//                    Set<String> childSet = new LinkedHashSet<>();
//                    i++;
//                    childSet.add("id");
//                    dtMap.put(i, childSet);
//                } else if (tmpStr.contains("time") || tmpStr.contains("date")) {
//                    Set<String> childSet = new LinkedHashSet<>();
//                    i++;
//                    childSet.add(str.toLowerCase());
//                    dtMap.put(i, childSet);
//                } else if(tmpStr.equals("data")) {
//                    Set<String> childSet = new LinkedHashSet<>();
//                    i++;
//                    childSet.add(tmpStr);
//                    dtMap.put(i, childSet);
////                    for(String dataStr : dataContent) {
////                        Set<String> dataSet = new LinkedHashSet<>();
////                        dataSet.add(dataStr.toLowerCase());
////
////                    }
//                }
//            }
//        }
//        return dtMap;
//    }



}
