package com.applicaiton.my_auth.Model;

public class HeaderModel {
    private String intHeaderID;
    private String strMessage;
    private String date;
    private String strUserName;
    private String strCell;
    private String strEmail;
    private int companyID;
    private int groupID;

    public HeaderModel() {}

    public HeaderModel(String intHeaderID, String strMessage, String date, String strUserName, String strCell, String strEmail, int companyID, int groupID) {
        this.intHeaderID = intHeaderID;
        this.strMessage = strMessage;
        this.date = date;
        this.strUserName = strUserName;
        this.strCell = strCell;
        this.strEmail = strEmail;
        this.companyID = companyID;
        this.groupID = groupID;
    }

    public String getIntHeaderID() {
        return intHeaderID;
    }

    public void setIntHeaderID(String intHeaderID) {
        this.intHeaderID = intHeaderID;
    }

    public String getStrMessage() {
        return strMessage;
    }

    public void setStrMessage(String strMessage) {
        this.strMessage = strMessage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStrUserName() {
        return strUserName;
    }

    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }

    public String getStrCell() {
        return strCell;
    }

    public void setStrCell(String strCell) {
        this.strCell = strCell;
    }

    public String getStrEmail() {
        return strEmail;
    }

    public void setStrEmail(String strEmail) {
        this.strEmail = strEmail;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
}
