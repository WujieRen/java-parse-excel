package demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.constants;

public interface Constants {
    String HQL_SUFFIX = ".hql";
    String SQL_SUFFIX = ".sql";
    String SH_SUFFIX = ".sh";
    String ODS_ROOT_PATH = "F:\\lemon\\dataCenter\\sqlFIleCreate\\ods\\";
    String ODS_STRUCTURED_PATH = "F:\\lemon\\dataCenter\\sqlFIleCreate\\ods\\structured\\";
    String ODS_UNSTRUCTURED_PATH = "F:\\lemon\\dataCenter\\sqlFIleCreate\\ods\\unstructured\\";
    String TDM_ROOT_PATH = "F:\\lemon\\dataCenter\\sqlFIleCreate\\tdm\\";
    String TDM_STRUCTURED_PATH = "F:\\lemon\\dataCenter\\sqlFIleCreate\\tdm\\structured\\";
    String TDM_UNSTRUCTURED_PATH = "F:\\lemon\\dataCenter\\sqlFIleCreate\\tdm\\unstructured\\";
    String SH_ROOT_PATH = "F:\\lemon\\dataCenter\\sqlFIleCreate\\sh\\";
    String SH_STRUCTURED_PATH = "F:\\lemon\\dataCenter\\sqlFIleCreate\\sh\\structured\\";
    String SH_UNSTRUCTURED_PATH = "F:\\lemon\\dataCenter\\sqlFIleCreate\\sh\\unstructured\\";
    String ODS_TRIM_PREFIX = "ods_rm_";
    String TDM_TRIM_PREFIX = "bdm_f_";
    String SRC_EXCEL_PATH = "F:/lemon/dataCenter/test.xls";
    String ODS_DATABASE_NAME = "ods_lm.";
    String TDM_DATABASE_NAME = "tdm.";
    String HDFS_ODS_ROOT_PATH = "hdfs://mycluster/datacenter/warehouse/hive/ods/";
    String HDFS_TDM_ROOT_PATH = "hdfs://mycluster/datacenter/warehouse/hive/tdm/";
    String CONTENT_PAGE_SHEET_NAME = "目录";
    String STRUCTED_FILE_TYPE = "mysql";
    String UNSTRUCTED_FILE_TYPE = "mongodb";


    //------------------------------------lixin----------------------------------------
    String TMP_PATH = "lixin\\";
    String LIXIN_TDM_PATH = TDM_STRUCTURED_PATH.concat(TMP_PATH);
    String LIXIN_ODS_PATH = ODS_STRUCTURED_PATH.concat(TMP_PATH);
    String LIXIN_SH_PATH = SH_STRUCTURED_PATH.concat(TMP_PATH);
//    String LIXIN_SRC_TAB_PATH = "E:\\workspaceTemp\\数据中心ODS模型设计v0.9 - 业务系统.xlsm";
    String LIXIN_SRC_TAB_PATH = "E:\\workspaceTemp\\test.xls";
    String ENCRYPT = "y";

}
