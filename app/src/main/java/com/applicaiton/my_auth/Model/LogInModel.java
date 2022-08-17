package com.applicaiton.my_auth.Model;

public class LogInModel {
    private int userId;
    private String userName;
    private int groupId;
    private int companyId;

    public LogInModel() {}

    public LogInModel(int userId, String userName, int groupId, int companyId) {
        this.userId = userId;
        this.userName = userName;
        this.groupId = groupId;
        this.companyId = companyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
