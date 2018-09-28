package com.uibenglish.aslan.mvpmindorkssample.data.content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Translate {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("votes")
    @Expose
    private Integer votes;
    @SerializedName("is_user")
    @Expose
    private Integer isUser;
    @SerializedName("pic_url")
    @Expose
    private String picUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Integer getIsUser() {
        return isUser;
    }

    public void setIsUser(Integer isUser) {
        this.isUser = isUser;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

}

