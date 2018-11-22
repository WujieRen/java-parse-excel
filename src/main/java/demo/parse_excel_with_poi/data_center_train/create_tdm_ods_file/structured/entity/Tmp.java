package demo.parse_excel_with_poi.data_center_train.create_tdm_ods_file.structured.entity;

public class Tmp {
    private String odsPrt;
    private String tdmPrt;
    private String comment;
    private String type;
    private String encrypt;

    public String getOdsPrt() {
        return odsPrt;
    }

    public void setOdsPrt(String odsPrt) {
        this.odsPrt = odsPrt;
    }

    public String getTdmPrt() {
        return tdmPrt;
    }

    public void setTdmPrt(String tdmPrt) {
        this.tdmPrt = tdmPrt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    @Override
    public String toString() {
        return odsPrt + ' ' + ' ' + type + "-COMMENT" + comment + ',' + tdmPrt + ',' +encrypt;
    }
}
