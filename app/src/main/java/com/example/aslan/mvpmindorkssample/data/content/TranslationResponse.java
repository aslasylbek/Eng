package com.example.aslan.mvpmindorkssample.data.content;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TranslationResponse implements Parcelable {

    @SerializedName("error_msg")
    @Expose
    private String errorMsg;
    @SerializedName("translate_source")
    @Expose
    private String translateSource;
    @SerializedName("is_user")
    @Expose
    private Integer isUser;
    @SerializedName("word_forms")
    @Expose
    private List<WordForm> wordForms = null;
    @SerializedName("pic_url")
    @Expose
    private String picUrl;
    @SerializedName("translate")
    @Expose
    private List<Translate> translate = null;
    @SerializedName("transcription")
    @Expose
    private String transcription;
    @SerializedName("word_id")
    @Expose
    private Integer wordId;
    @SerializedName("word_top")
    @Expose
    private Integer wordTop;
    @SerializedName("sound_url")
    @Expose
    private String soundUrl;


    public TranslationResponse(String errorMsg, String translateSource, Integer isUser, List<WordForm> wordForms, String picUrl, List<Translate> translate, String transcription, Integer wordId, Integer wordTop, String soundUrl) {
        this.errorMsg = errorMsg;
        this.translateSource = translateSource;
        this.isUser = isUser;
        this.wordForms = wordForms;
        this.picUrl = picUrl;
        this.translate = translate;
        this.transcription = transcription;
        this.wordId = wordId;
        this.wordTop = wordTop;
        this.soundUrl = soundUrl;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getTranslateSource() {
        return translateSource;
    }

    public void setTranslateSource(String translateSource) {
        this.translateSource = translateSource;
    }

    public Integer getIsUser() {
        return isUser;
    }

    public void setIsUser(Integer isUser) {
        this.isUser = isUser;
    }

    public List<WordForm> getWordForms() {
        return wordForms;
    }

    public void setWordForms(List<WordForm> wordForms) {
        this.wordForms = wordForms;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public List<Translate> getTranslate() {
        return translate;
    }

    public void setTranslate(List<Translate> translate) {
        this.translate = translate;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public Integer getWordId() {
        return wordId;
    }

    public void setWordId(Integer wordId) {
        this.wordId = wordId;
    }

    public Integer getWordTop() {
        return wordTop;
    }

    public void setWordTop(Integer wordTop) {
        this.wordTop = wordTop;
    }

    public String getSoundUrl() {
        return soundUrl;
    }

    public void setSoundUrl(String soundUrl) {
        this.soundUrl = soundUrl;
    }

    public TranslationResponse(Parcel in) {
        errorMsg = in.readString();
        translateSource = in.readString();
        isUser = in.readInt();
        in.readList(this.wordForms, WordForm.class.getClassLoader());
        picUrl = in.readString();
        in.readList(this.translate, Translate.class.getClassLoader());
        transcription = in.readString();
        wordId = in.readInt();
        wordTop = in.readInt();
        soundUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(errorMsg);
        dest.writeString(translateSource);
        dest.writeInt(isUser);
        dest.writeList(wordForms);
        dest.writeString(picUrl);
        dest.writeList(translate);
        dest.writeString(transcription);
        dest.writeInt(wordId);
        dest.writeInt(wordTop);
        dest.writeString(soundUrl);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        @Override
        public TranslationResponse createFromParcel(Parcel source) {
            return new TranslationResponse(source);
        }

        @Override
        public TranslationResponse[] newArray(int size) {
            return new TranslationResponse[size];
        }
    };
}

