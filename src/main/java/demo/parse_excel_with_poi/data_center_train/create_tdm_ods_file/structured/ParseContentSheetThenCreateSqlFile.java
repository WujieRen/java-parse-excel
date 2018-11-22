package demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured;

import demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.constants.Constants;
import demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.util.ExcelUtil;
import demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.util.FileUtil;

import java.io.IOException;
import java.util.List;

public class ParseContentSheetThenCreateSqlFile {

    public static void main(String[] args) throws IOException {
        FileUtil.deleteDir(Constants.ODS_ROOT_PATH);
        FileUtil.deleteDir(Constants.TDM_ROOT_PATH);
        FileUtil.deleteDir(Constants.SH_ROOT_PATH);

        List<String[]> nameMap = ExcelUtil.parseExcel();

//        FileUtil.writeTdmItem(nameMap);
//        FileUtil.writeOdsItem(nameMap);
//        FileUtil.writeShFile(nameMap);
    }

}
