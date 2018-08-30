package com.example.aslan.mvpmindorkssample.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WordCollection {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("pic_url")
    @Expose
    private String picUrl;
    @SerializedName("transcription")
    @Expose
    private String transcription;
    @SerializedName("sound_url")
    @Expose
    private String soundUrl;
    @SerializedName("translate_word")
    @Expose
    private String translateWord;
    @SerializedName("rating")
    @Expose
    private String rating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public String getSoundUrl() {
        return soundUrl;
    }

    public void setSoundUrl(String soundUrl) {
        this.soundUrl = soundUrl;
    }

    public String getTranslateWord() {
        return translateWord;
    }

    public void setTranslateWord(String translateWord) {
        this.translateWord = translateWord;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

}
