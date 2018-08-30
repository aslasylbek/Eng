package com.example.aslan.mvpmindorkssample.ui.main.content;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    @Ignore
    @SerializedName("questionanswer")
    @Expose
    private List<Questionanswer> questionanswer = null;
    @Ignore
    @SerializedName("truefalse")
    @Expose
    private List<Truefalse> truefalse = null;
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

    public List<Questionanswer> getQuestionanswer() {
        return questionanswer;
    }

    public void setQuestionanswer(List<Questionanswer> questionanswer) {
        this.questionanswer = questionanswer;
    }

    public List<Truefalse> getTruefalse() {
        return truefalse;
    }

    public void setTruefalse(List<Truefalse> truefalse) {
        this.truefalse = truefalse;
    }


    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

}
