package com.uibenglish.aslan.mvpmindorkssample.data.content;

import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.English;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Info;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EngInformationResponse {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("info")
    @Expose
    private List<Info> info = null;
    @SerializedName("english")
    @Expose
    private List<English> english = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Info> getInfo() {
        return info;
    }

    public void setInfo(List<Info> info) {
        this.info = info;
    }

    public List<English> getEnglish() {
        return english;
    }

    public void setEnglish(List<English> english) {
        this.english = english;
    }
}
