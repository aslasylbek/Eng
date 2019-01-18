package com.uibenglish.aslan.mvpmindorkssample.ui.main.content;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


//@Entity (tableName = "grammar")
public class Grammar{

    @SerializedName("missword")
    @Expose
    private List<Missword> missword = null;
    @SerializedName("constructor")
    @Expose
    private List<Constructor> constructor = null;

    private String topic_id;

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public List<Missword> getMissword() {
        return missword;
    }

    public void setMissword(List<Missword> missword) {
        this.missword = missword;
    }

    public List<Constructor> getConstructor() {
        return constructor;
    }

    public void setConstructor(List<Constructor> constructor) {
        this.constructor = constructor;
    }
}
