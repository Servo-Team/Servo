package com.servo.view;

public class ServiceModel {

    private String title, desc;
    private String[] chipText;

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
