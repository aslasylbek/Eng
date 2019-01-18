package com.uibenglish.aslan.mvpmindorkssample.ui.main.content;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Questionanswer implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("answer")
    @Expose
    private String answer;

    private String userAnswer;

    private String editTextColor;

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public Questionanswer(Parcel in) {
        id = in.readString();
        question = in.readString();
        answer = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(question);
        dest.writeString(answer);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        @Override
        public Questionanswer createFromParcel(Parcel source) {
            return new Questionanswer(source);
        }

        @Override
        public Questionanswer[] newArray(int size) {
            return new Questionanswer[size];
        }
    };
}
