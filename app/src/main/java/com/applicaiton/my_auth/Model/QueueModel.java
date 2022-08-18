package com.applicaiton.my_auth.Model;

public class QueueModel {
    private String intAuthLineId;
    private int IsAuthorized;
    private String Instructions;

    public QueueModel(){}

    public QueueModel(String intAuthLineId, int isAuthorized, String instructions) {
        this.intAuthLineId = intAuthLineId;
        IsAuthorized = isAuthorized;
        Instructions = instructions;
    }

    public String getIntAuthLineId() {
        return intAuthLineId;
    }

    public void setIntAuthLineId(String intAuthLineId) {
        this.intAuthLineId = intAuthLineId;
    }

    public int getIsAuthorized() {
        return IsAuthorized;
    }

    public void setIsAuthorized(int isAuthorized) {
        IsAuthorized = isAuthorized;
    }

    public String getInstructions() {
        return Instructions;
    }

    public void setInstructions(String instructions) {
        Instructions = instructions;
    }
}
