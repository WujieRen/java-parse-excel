package demo.parse_excel_with_poi.data_center_train.pase_version_two.entity;

import org.apache.commons.lang3.StringUtils;

public class CreateTablePropertyRef {
    private String prt;
    private String prtComment;
    private String type;
//    private String encrypt;

    public String getPrt() {
        return prt;
    }

    public void setPrt(String prt) {
        this.prt = prt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public String getEncrypt() {
//        return encrypt;
//    }
//
//    public void setEncrypt(String encrypt) {
//        this.encrypt = encrypt;
//    }

    public String getPrtComment() {
        return prtComment;
    }

    public void setPrtComment(String prtComment) {
        if(StringUtils.isNotBlank(prtComment)) {
            this.prtComment = "COMMENT '".concat(prtComment).concat("'");
        } else {
            this.prtComment = prtComment;
        }
    }

    @Override
    public String toString() {
        if(StringUtils.isBlank(this.getPrtComment()))
            return getPrt() + " " + getType();
        return getPrt() + " " + getType() + " " + getPrtComment();
    }
}
