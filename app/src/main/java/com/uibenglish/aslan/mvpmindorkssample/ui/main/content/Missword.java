package com.uibenglish.aslan.mvpmindorkssample.ui.main.content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Missword {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sentence")
    @Expose
    private String sentence;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("translate")
    @Expose
    private String translate;
    @SerializedName("sound_url")
    @Expose
    private String soundUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
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

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getSoundUrl() {
        return soundUrl;
    }

    public void setSoundUrl(String soundUrl) {
        this.soundUrl = soundUrl;
    }
}
