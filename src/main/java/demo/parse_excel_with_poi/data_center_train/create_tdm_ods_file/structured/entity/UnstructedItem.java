package demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.entity;

import org.apache.commons.lang3.StringUtils;

public class UnstructedItem extends Item {
    private String encrypt;

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public boolean isEncrypt() {
        return StringUtils.equals(getEncrypt(), "y");
    }
}
