package com.uibenglish.aslan.mvpmindorkssample.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BBCEnglish {
    @SerializedName("categories")
    @Expose
    private List<BBCCategories> categories = null;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<BBCCategories> getCategories() {
        return categories;
    }

    public void setCategories(List<BBCCategories> categories) {
        this.categories = categories;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
