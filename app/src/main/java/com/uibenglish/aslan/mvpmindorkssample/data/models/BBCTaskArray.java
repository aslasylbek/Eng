package com.uibenglish.aslan.mvpmindorkssample.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BBCTaskArray implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("sentence")
    @Expose
    private String sentence;
    @SerializedName("definition")
    private String orDefinition;

    private String userAnswer;

    private String editTextColor;

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

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getOrDefinition() {
        return orDefinition;
    }

    public void setOrDefinition(String orDefinition) {
        this.orDefinition = orDefinition;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getEditTextColor() {
        return editTextColor;
    }

    public void setEditTextColor(String editTextColor) {
        this.editTextColor = editTextColor;
    }


    public BBCTaskArray(Parcel parcel) {
        id = parcel.readString();
        word = parcel.readString();
        sentence = parcel.readString();
        orDefinition = parcel.readString();
        userAnswer = parcel.readString();
        editTextColor = parcel.readString();
    }

    public final static Parcelable.Creator<BBCTaskArray> CREATOR = new Creator<BBCTaskArray>() {

        public BBCTaskArray createFromParcel(Parcel in) {
            return new BBCTaskArray(in);
        }

        public BBCTaskArray[] newArray(int size) {
            return new BBCTaskArray[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(word);
        dest.writeString(sentence);
        dest.writeString(orDefinition);
        dest.writeString(userAnswer);
        dest.writeString(editTextColor);
    }
}
