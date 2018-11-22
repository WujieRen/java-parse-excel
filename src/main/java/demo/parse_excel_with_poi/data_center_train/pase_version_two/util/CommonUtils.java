package demo.parse_excel_with_poi.data_center_train.pase_version_two.util;

import demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.constants.Constants;
import demo.parse_excel_with_poi.data_center_train.pase_version_two.entity.TabNameAndCommentRef;
import demo.parse_excel_with_poi.data_center_train.pase_version_two.entity.CreateTablePropertyRef;
import org.apache.commons.lang3.StringUtils;
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
import java.util.List;

public class CommonUtils {

    public static void concatStructedProperty(StringBuffer sb, List<CreateTablePropertyRef> itemList) throws IOException {
        //TODO:如果要通用这个方法就得泛华这两个属性为一个，如果不想泛华那就重新写一个方法，获取不同属性值
        CreateTablePropertyRef tpr;
        for(int i = 0; i < itemList.size(); i++) {
            tpr = itemList.get(i);
            if(!StringUtils.isBlank(tpr.getPrt())) {
                if(i == 0)
                    sb.append("    ").append(tpr.toString());
                else
                    sb.append(",\n    ").append(tpr.toString());
            }
        }
    }

    public static void concatTabComment(StringBuffer sb, String tabComment) throws IOException {
        if(StringUtils.isNotBlank(tabComment)) {
            sb.append("COMMENT '").append(tabComment).append("' \n");
        }
    }

    public static List<TabNameAndCommentRef> getContentPageTabNameRef() throws IOException {
        FileInputStream fis = getReadExcelStream();
        Workbook workbook = getWorkBook(fis);
        Sheet contentSheet = workbook.getSheet(Constants.CONTENT_PAGE_SHEET_NAME);
        Iterator<Row> iterator = contentSheet.iterator();
        List<TabNameAndCommentRef> tabReflectionList = new ArrayList<>();
        while(iterator.hasNext()) {
            Row curRow = iterator.next();
            int curRowNum = curRow.getRowNum();
            TabNameAndCommentRef tabNameAndCommentRef = new TabNameAndCommentRef();
            //TODO:这个地方也应该写成可配置化的，去掉了第一行
//            if(curRowNum > 146 && curRowNum < 167) {   //如果想选出(a~b)行，那么这里的范围就应该是((a-1) ~ (b+1))
//            if(curRowNum > 45 && curRowNum < 91) {   //如果想选出(a~b)行，那么这里的范围就应该是((a-1) ~ (b+1))
            if(curRowNum > 0 && StringUtils.isNotBlank(curRow.getCell(1).getStringCellValue())) {   //如果想选出(a~b)行，那么这里的范围就应该是((a-1) ~ (b+1))
                String sheetName = curRow.getCell(0).getHyperlink().getAddress().replaceAll("!A1", "");
                tabNameAndCommentRef.setSheetName(sheetName);
                String tabComment = workbook.getSheet(sheetName).getRow(4).getCell(1).getStringCellValue();
                tabNameAndCommentRef.setTabComment(tabComment);

                String odsTabName = curRow.getCell(1).getStringCellValue();
//                String odsFileName = Constants.ODS_STRUCTURED_PATH.concat(odsTabName).concat(Constants.HQL_SUFFIX);
//                FileUtil.createFile(odsFileName);
                tabNameAndCommentRef.setOdsTabName(odsTabName);
                System.out.print(odsTabName + "\t");

                String tdmTabName = curRow.getCell(7).getStringCellValue();
//                String tdmFileName = Constants.TDM_STRUCTURED_PATH.concat(tdmTabName).concat(Constants.HQL_SUFFIX);
//                FileUtil.createFile(tdmFileName);
                tabNameAndCommentRef.setTdmTabName(tdmTabName);
                System.out.println(tdmTabName + "\t");

                //TODO:这里的约定是第4列是业务源类型，如果不符合此约定则此处需要修改
                String tabType = curRow.getCell(3).getStringCellValue();
                tabNameAndCommentRef.setTabType(tabType);

                tabReflectionList.add(tabNameAndCommentRef);
            }
        }
        closeWorkBook(workbook);
        closeReadExcelStream(fis);
        return tabReflectionList;
    }

    public static void closeReadExcelStream(FileInputStream fis) throws IOException {
        fis.close();
    }

    public static void closeWorkBook(Workbook workbook) throws IOException {
        workbook.close();
    }

    public static Workbook getWorkBook(FileInputStream fis) throws IOException {
        //TODO:这里应该写成能自动判断采用HSSF还是XSSF来处理的形式
        return new HSSFWorkbook(fis);
        //lixin
//        return new XSSFWorkbook(fis);
    }

    public static FileInputStream getReadExcelStream() throws FileNotFoundException {
        //TODO:做成UI的话这个肯定是要配置的
        FileInputStream fis = new FileInputStream(new File(Constants.SRC_EXCEL_PATH));
        return fis;
    }
}
