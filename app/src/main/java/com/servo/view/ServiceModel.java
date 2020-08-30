package com.servo.view;

public class ServiceModel {

    private int serviceID;
    private String title, desc;
    private String[] chipText;
    private boolean isFinished;

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public String[] getChipText() {
        return chipText;
    }

    public void setChipText(String[] chipText) {
        this.chipText = chipText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
