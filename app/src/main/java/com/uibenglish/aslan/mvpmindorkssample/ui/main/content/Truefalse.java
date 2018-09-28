package com.uibenglish.aslan.mvpmindorkssample.ui.main.content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Truefalse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("truefalse")
    @Expose
    private String truefalse;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTruefalse() {
        return truefalse;
    }

    public void setTruefalse(String truefalse) {
        this.truefalse = truefalse;
    }

}
