package com.uibenglish.aslan.mvpmindorkssample.ui.vocabulary;

public class ResultContent {

    private String chapter;
    private String topic_id;
    private int result;


    public ResultContent(String chapter,  String topic_id, int result) {
        this.chapter = chapter;
        this.topic_id = topic_id;
        this.result = result;
    }

    public ResultContent(String chapter, int result) {
        this.chapter = chapter;
        this.result = result;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
