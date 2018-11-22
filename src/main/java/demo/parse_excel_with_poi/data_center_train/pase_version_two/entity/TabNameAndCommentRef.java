package demo.parse_excel_with_poi.data_center_train.pase_version_two.entity;

public class TabNameAndCommentRef {
    private String sheetName;
    private String odsTabName;
    private String tdmTabName;
    private String tabComment;
    private String tabType;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getOdsTabName() {
        return odsTabName;
    }

    public void setOdsTabName(String odsTabName) {
        this.odsTabName = odsTabName;
    }

    public String getTdmTabName() {
        return tdmTabName;
    }

    public void setTdmTabName(String tdmTabName) {
        this.tdmTabName = tdmTabName;
    }

    public String getTabComment() {
        return tabComment;
    }

    public void setTabComment(String tabComment) {
        this.tabComment = tabComment;
    }

    public String getTabType() {
        return tabType;
    }

    public void setTabType(String tabType) {
        this.tabType = tabType;
    }

    @Override
    public String toString() {
        return "TabNameAndCommentRef{" +
                "sheetName='" + sheetName + '\'' +
                ", odsTabName='" + odsTabName + '\'' +
                ", tdmTabName='" + tdmTabName + '\'' +
                ", tabComment='" + tabComment + '\'' +
                '}';
    }
}
