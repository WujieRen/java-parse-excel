package demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.entity;

import org.apache.commons.lang3.StringUtils;

public class Item {
    private String key;
    private String keyType;
    private String keyComment;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public void setKeyComment(String keyComment) {
        if(StringUtils.isNotBlank(keyComment)) {
            this.keyComment = "COMMENT '".concat(keyComment).concat("'");
        } else {
            this.keyComment = keyComment;
        }
    }

    @Override
    public String toString() {
        if(StringUtils.isBlank(keyComment))
            return key + " " + keyType;
        return key + " " + keyType + " " + keyComment;
    }
}
