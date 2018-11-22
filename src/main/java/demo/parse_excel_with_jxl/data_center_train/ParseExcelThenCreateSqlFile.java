package demo.parse_excel_with_jxl.data_center_train;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;

public class ParseExcelThenCreateSqlFile {
    private static final int READ_ROW_START = 45;
    private static final int READ_ROW_END = 91;
    private static final int READ_COL_1 = 1;
    private static final int READ_WHICH_TABLE = 1;
    private static final String SUFFIX = ".hql";

    public static void main(String[] args) {
        String filePath = "F:\\lemon\\dataCenter\\test.xls";
        execute(filePath);
    }

    private static void execute(String filelPath) {
        Workbook workbook = getWorkBook(filelPath);
        Sheet sheet = getSheet(workbook);
        int rows = sheet.getRows();
        int cols = sheet.getColumns();
        getContent(sheet, rows, cols);
        workbook.close();
    }

    private static Workbook getWorkBook(String filePath) {
        Workbook workbook = null;
        try {
           workbook = Workbook.getWorkbook(new File(filePath));
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    private static Sheet getSheet(Workbook workbook) {

        //  获得第一个表格对象，从0开始
        return workbook == null ? null : workbook.getSheet(READ_WHICH_TABLE);
    }

    private static void getContent (Sheet sheet, int rowNum, int colNum) {
        String[][] result = new String[rowNum][colNum];
        for(int i = 0; i < rowNum ; i++) {
            for(int j = 0; j < colNum; j++) {
                Cell cell =  sheet.getCell(j,i);
                result[i][j] = cell.getContents();
            }
        }


//        遍历二维数组输出 到控制台
        System.out.println(result[0][1]);
        for(int i = READ_ROW_START; i < READ_ROW_END; i++){
            for(int j = 0; j < colNum; j++){
                if(j == READ_COL_1) {
                    String curPath = "F:\\lemon\\dataCenter\\sqlFIleCreate\\ods\\";
                    String odsName = result[i][j];
                    String fileName = curPath.concat(odsName).concat(SUFFIX);
                    File file = new File(fileName);
                    fileProber(file);
                    createSqlFile(file);
                    String tdmName = odsName.replace("ods", "bdm");
                    fileName = curPath.replace("ods", "tdm").concat(tdmName).concat(SUFFIX);
                    file = new File(fileName);
                    fileProber(file);
                    createSqlFile(file);
                    System.out.print(result[i][j] + "\t");
                }
            }
            System.out.println();
        }
    }

    private static void createSqlFile(File file) {
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void fileProber(File dirFile) {
        File parentFile = dirFile.getParentFile();
        if (!parentFile.exists()) {
            fileProber(parentFile);
            parentFile.mkdirs();
        }
    }

}
