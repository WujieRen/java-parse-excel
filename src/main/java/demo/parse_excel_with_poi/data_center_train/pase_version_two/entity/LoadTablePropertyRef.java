package demo.parse_excel_with_poi.data_center_train.pase_version_two.entity;

public class LoadTablePropertyRef {
    private String odsPrt;
    private String encrypt;
    private String tdmPrt;

    public String getOdsPrt() {
        return odsPrt;
    }

    public void setOdsPrt(String odsPrt) {
        this.odsPrt = odsPrt;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getTdmPrt() {
        return tdmPrt;
    }

    public void setTdmPrt(String tdmPrt) {
        this.tdmPrt = tdmPrt;
    }

    @Override
    public String toString() {
        return "LoadTablePropertyRef{" +
                "odsPrt='" + odsPrt + '\'' +
                ", encrypt='" + encrypt + '\'' +
                ", tdmPrt='" + tdmPrt + '\'' +
                '}';
    }
}
