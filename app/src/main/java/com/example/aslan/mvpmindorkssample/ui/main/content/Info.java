package com.example.aslan.mvpmindorkssample.ui.main.content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {
    @SerializedName("fio")
    @Expose
    private String fio;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("program")
    @Expose
    private String program;
    @SerializedName("course")
    @Expose
    private String course;

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

}
