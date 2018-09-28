package com.uibenglish.aslan.mvpmindorkssample.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Word;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WordCollection implements Parcelable{

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
    public final static Parcelable.Creator<WordCollection> CREATOR = new Creator<WordCollection>() {


        @SuppressWarnings({
                "unchecked"
        })
        public WordCollection createFromParcel(Parcel in) {
            return new WordCollection(in);
        }

        public WordCollection[] newArray(int size) {
            return (new WordCollection [size]);
        }

    }
            ;

    protected WordCollection(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.word = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.picUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.transcription = ((String) in.readValue((String.class.getClassLoader())));
        this.soundUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.translateWord = ((String) in.readValue((String.class.getClassLoader())));
        this.rating = ((String) in.readValue((String.class.getClassLoader())));
    }

    public WordCollection() {
    }

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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(word);
        dest.writeValue(type);
        dest.writeValue(picUrl);
        dest.writeValue(transcription);
        dest.writeValue(soundUrl);
        dest.writeValue(translateWord);
        dest.writeValue(rating);
    }

    public int describeContents() {
        return 0;
    }

}

