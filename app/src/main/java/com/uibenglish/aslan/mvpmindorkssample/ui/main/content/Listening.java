package com.uibenglish.aslan.mvpmindorkssample.ui.main.content;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Listening implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("listening")
    @Expose
    private String listening;
    @SerializedName("translate")
    @Expose
    private String translate;
    @SerializedName("questionanswer")
    @Expose
    private List<Questionanswer> questionanswer = null;

    @SerializedName("sound_url")
    @Expose
    private String sound_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSound_url() {
        return sound_url;
    }

    public void setSound_url(String sound_url) {
        this.sound_url = sound_url;
    }

    public String getListening() {
        return listening;
    }

    public void setListening(String listening) {
        this.listening = listening;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public List<Questionanswer> getQuestionanswer() {
        return questionanswer;
    }

    public void setQuestionanswer(List<Questionanswer> questionanswer) {
        this.questionanswer = questionanswer;
    }

    public Listening(String id, String listening, String translate, List<Questionanswer> questionanswer, String sound_url) {
        this.id = id;
        this.listening = listening;
        this.translate = translate;
        this.questionanswer = questionanswer;
        this.sound_url = sound_url;
    }

    public Listening(Parcel in) {
        id = in.readString();
        listening = in.readString();
        translate = in.readString();
        in.readList(this.questionanswer, Questionanswer.class.getClassLoader());
        sound_url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(listening);
        dest.writeString(translate);
        dest.writeList(questionanswer);
        dest.writeString(sound_url);

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        @Override
        public Listening createFromParcel(Parcel source) {
            return new Listening(source);
        }

        @Override
        public Listening[] newArray(int size) {
            return new Listening[size];
        }
    };
}
