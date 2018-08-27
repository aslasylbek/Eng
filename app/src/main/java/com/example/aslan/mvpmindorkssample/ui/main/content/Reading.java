package com.example.aslan.mvpmindorkssample.ui.main.content;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity (tableName = "reading")
public class Reading {

    @NonNull
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("reading")
    @Expose
    private String reading;
    @SerializedName("sound_url")
    @Expose
    private String soundUrl;
    @SerializedName("translate")
    @Expose
    private String translate;

    private String topic_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public String getSoundUrl() {
        return soundUrl;
    }

    public void setSoundUrl(String soundUrl) {
        this.soundUrl = soundUrl;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }
}
