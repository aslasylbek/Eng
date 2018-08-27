package com.example.aslan.mvpmindorkssample.ui.main.content;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.example.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity (tableName = "words")
public class Word implements Parcelable {
    @NonNull
    @PrimaryKey()
    @SerializedName("id")
    private String id;
    @SerializedName("word")
    private String word;
    @SerializedName("type")
    private String type;
    @SerializedName("pic_url")
    private String picUrl;
    @SerializedName("transcription")
    private String transcription;
    @SerializedName("sound_url")
    private String soundUrl;
    @SerializedName("translate_word")
    private String translateWord;
    private String topic_id;

    public Word(String id, String word, String type, String picUrl, String transcription, String soundUrl, String translateWord, String topic_id) {
        this.id = id;
        this.word = word;
        this.type = type;
        this.picUrl = picUrl;
        this.transcription = transcription;
        this.soundUrl = soundUrl;
        this.translateWord = translateWord;
        this.topic_id = topic_id;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(word);
        dest.writeString(type);
        dest.writeString(picUrl);
        dest.writeString(transcription);
        dest.writeString(soundUrl);
        dest.writeString(translateWord);
        dest.writeString(topic_id);

    }

    public Word(Parcel in) {
        this.id = in.readString();
        this.word = in.readString();
        this.type = in.readString();
        this.picUrl = in.readString();
        this.transcription = in.readString();
        this.soundUrl = in.readString();
        this.translateWord = in.readString();
        topic_id = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        @Override
        public Word createFromParcel(Parcel source) {
            return new Word(source);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
}
