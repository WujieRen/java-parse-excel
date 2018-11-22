package demo.parse_excel_with_poi.data_center_train.pase_version_two;

import demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.constants.Constants;
import demo.parse_excel_with_poi.data_center_train.pase_version_two.entity.LoadTablePropertyRef;
import demo.parse_excel_with_poi.data_center_train.pase_version_two.entity.TabNameAndCommentRef;
import demo.parse_excel_with_poi.data_center_train.pase_version_two.util.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        FileUtils.delete(Constants.ODS_ROOT_PATH);
        FileUtils.delete(Constants.TDM_ROOT_PATH);
        FileUtils.delete(Constants.SH_ROOT_PATH);

        List<TabNameAndCommentRef> tabNameAndCommentRefList = CommonUtils.getContentPageTabNameRef();
        for(TabNameAndCommentRef tnr : tabNameAndCommentRefList) {
            //create files
            String tabType = tnr.getTabType();
            if(StringUtils.equals(tabType, Constants.STRUCTED_FILE_TYPE)) {
                FileUtils.createStructedFiles(tnr);
            } else if (StringUtils.equals(tabType, Constants.UNSTRUCTED_FILE_TYPE)) {
                FileUtils.createUnstructedFiles(tnr);
            }

            //write to file
            if(StringUtils.equals(tabType, Constants.STRUCTED_FILE_TYPE)) {//write to structed files
                TdmUtils.writeTdmStructed(tnr);
                OdsUtils.writeOdsStructed(tnr);
                ShUtils.writeShStructed(tnr);
            } else if(StringUtils.equals(tabType, Constants.UNSTRUCTED_FILE_TYPE)) {//write to unstructed files
                TdmUtils.writeTdmUnstructed(tnr);
                OdsUtils.writeOdsUnstructed(tnr);
                List<LoadTablePropertyRef> prtRefList =  ShUtils.parsePropertyForUnstructedSh(tnr.getSheetName());
                ShUtils.writeShUnstructed(prtRefList, tnr);
            }
        }
    }
}
