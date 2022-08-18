package com.applicaiton.my_auth.Model;

public class LineModel {
    private String intAuthLineId;
    private String strLineMessage1;
    private String strLineMessage2;
    private String strLineMessage3;
    private String blnIsApproved;
    private String intHeaderID;

    public LineModel() {}

    public LineModel(String intAuthLineId, String strLineMessage1, String strLineMessage2, String strLineMessage3, String blnIsApproved, String intHeaderID) {
        this.intAuthLineId = intAuthLineId;
        this.strLineMessage1 = strLineMessage1;
        this.strLineMessage2 = strLineMessage2;
        this.strLineMessage3 = strLineMessage3;
        this.blnIsApproved = blnIsApproved;
        this.intHeaderID = intHeaderID;
    }

    public String getIntAuthLineId() {
        return intAuthLineId;
    }

    public void setIntAuthLineId(String intAuthLineId) {
        this.intAuthLineId = intAuthLineId;
    }

    public String getStrLineMessage1() {
        return strLineMessage1;
    }

    public void setStrLineMessage1(String strLineMessage1) {
        this.strLineMessage1 = strLineMessage1;
    }

    public String getStrLineMessage2() {
        return strLineMessage2;
    }

    public void setStrLineMessage2(String strLineMessage2) {
        this.strLineMessage2 = strLineMessage2;
    }

    public String getStrLineMessage3() {
        return strLineMessage3;
    }

    public void setStrLineMessage3(String strLineMessage3) {
        this.strLineMessage3 = strLineMessage3;
    }

    public String getBlnIsApproved() {
        return blnIsApproved;
    }

    public void setBlnIsApproved(String blnIsApproved) {
        this.blnIsApproved = blnIsApproved;
    }

    public String getIntHeaderID() {
        return intHeaderID;
    }

    public void setIntHeaderID(String intHeaderID) {
        this.intHeaderID = intHeaderID;
    }
}
