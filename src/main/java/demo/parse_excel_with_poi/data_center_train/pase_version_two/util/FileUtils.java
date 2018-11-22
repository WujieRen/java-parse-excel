package demo.parse_excel_with_poi.data_center_train.pase_version_two.util;

import demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.constants.Constants;
import demo.parse_excel_with_poi.data_center_train.pase_version_two.entity.TabNameAndCommentRef;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static void createStructedFiles(TabNameAndCommentRef tnr) throws IOException {
        String odsTabName = tnr.getOdsTabName();
        String tdmTabName = tnr.getTdmTabName();
        String odsFileName = Constants.ODS_STRUCTURED_PATH.concat(odsTabName).concat(Constants.SQL_SUFFIX);
        FileUtils.createFile(odsFileName);

        String shFileName = Constants.SH_STRUCTURED_PATH.concat(odsTabName).concat(Constants.SH_SUFFIX);
        FileUtils.createFile(shFileName);

        String tdmFileName = Constants.TDM_STRUCTURED_PATH.concat(tdmTabName).concat(Constants.SQL_SUFFIX);
        FileUtils.createFile(tdmFileName);
    }

    public static void createUnstructedFiles(TabNameAndCommentRef tnr) throws IOException {
        String odsTabName = tnr.getOdsTabName();
        String tdmTabName = tnr.getTdmTabName();
        String odsFileName = Constants.ODS_UNSTRUCTURED_PATH.concat(odsTabName).concat(Constants.SQL_SUFFIX);
        FileUtils.createFile(odsFileName);

        String shFileName = Constants.SH_UNSTRUCTURED_PATH.concat(odsTabName).concat(Constants.SH_SUFFIX);
        FileUtils.createFile(shFileName);

        String tdmFileName = Constants.TDM_UNSTRUCTURED_PATH.concat(tdmTabName).concat(Constants.SQL_SUFFIX);
        FileUtils.createFile(tdmFileName);
    }

    static OutputStreamWriter getFileWriter(String filePath) throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(filePath);
        return new OutputStreamWriter(fos, StandardCharsets.UTF_8);
    }

    public static void delete(String fileAbsPath) {
        deleteFile(new File(fileAbsPath));
    }

    private static void deleteFile(File file) {
        if(file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if(null != childFiles) {
                for(File childFile : childFiles) {
                    deleteFile(new File(file, childFile.getName()));
                }
            }
        }
        file.delete();
    }

    private static void createFile(String fileAbsPathName) throws IOException {
        File file = new File(fileAbsPathName);
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
}
