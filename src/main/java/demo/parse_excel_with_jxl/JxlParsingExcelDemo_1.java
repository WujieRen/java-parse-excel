package demo.parse_excel_with_jxl;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.io.File;

public class JxlParsingExcelDemo_1 {
    public static void main(String[] args) {
        try    {
            Workbook book  =  Workbook.getWorkbook( new File( "F:\\lemon\\dataCenter\\test.xls"));
            //  获得第一个表格对象
            Sheet sheet  =  book.getSheet( 2);
//            Sheet sheet  =  book.getSheet( "ods_rm_rb_role_info");

            //拿到表格的行数
            int row = sheet.getRows();
            //拿到表格的列数
            int col = sheet.getColumns();
            System.out.println("行："+ row);
            System.out.println("列："+ col);
            //用二维数组保存表格的数据
            String[][] result = new String[row][col];

            //遍历表格拿到表格数据
            for(int i =0;i<row;i++)
                for(int j=0;j<col;j++){
                    Cell cell =  sheet.getCell(j,i);
                    result[i][j] = cell.getContents();

                }
            //遍历二维数组输出 到控制台
            for(int i =0;i<row;i++){
                for(int j=0;j<col;j++){
                    System.out.print(result[i][j]+"\t");

                }
                System.out.println();
            }

            book.close();
        }   catch  (Exception e)   {
            //System.out.println(e);
            e.printStackTrace();
        }
    }
}
