package com.uibenglish.aslan.mvpmindorkssample.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BBCLesson {

    @SerializedName("audio_url")
    @Expose
    private String audioUrl;
    @SerializedName("vocabulary")
    @Expose
    private List<BBCTranscript> vocabulary = null;
    @SerializedName("transcript")
    @Expose
    private List<BBCTranscript> transcript = null;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public List<BBCTranscript> getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(List<BBCTranscript> vocabulary) {
        this.vocabulary = vocabulary;
    }

    public List<BBCTranscript> getTranscript() {
        return transcript;
    }

    public void setTranscript(List<BBCTranscript> transcript) {
        this.transcript = transcript;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public class BBCTranscript {

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

        private String userAnswer = "";

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
