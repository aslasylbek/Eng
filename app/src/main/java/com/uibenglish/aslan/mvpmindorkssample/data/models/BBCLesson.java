package com.uibenglish.aslan.mvpmindorkssample.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uibenglish.aslan.mvpmindorkssample.R;

import java.util.List;

public class BBCLesson {

    @SerializedName("audio_url")
    @Expose
    private String audioUrl;
    @SerializedName("vocabulary")
    @Expose
    private List<BBCTaskArray> vocabulary = null;
    @SerializedName("transcript")
    @Expose
    private List<BBCTaskArray> transcript = null;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public List<BBCTaskArray> getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(List<BBCTaskArray> vocabulary) {
        this.vocabulary = vocabulary;
    }

    public List<BBCTaskArray> getTranscript() {
        return transcript;
    }

    public void setTranscript(List<BBCTaskArray> transcript) {
        this.transcript = transcript;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public class BBCVocabulary {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("word")
        @Expose
        private String word;
        @SerializedName("definition")
        @Expose
        private String definition;

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

        public String getDefinition() {
            return definition;
        }

        public void setDefinition(String definition) {
            this.definition = definition;
        }

    }
}
