package com.uibenglish.aslan.mvpmindorkssample.data.content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WordForm {
    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("type")
    @Expose
    private String type;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
