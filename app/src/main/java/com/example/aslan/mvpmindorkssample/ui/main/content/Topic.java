package com.example.aslan.mvpmindorkssample.ui.main.content;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


@Entity(tableName = "topics")
public class Topic {
    @NonNull
    @PrimaryKey()
    @SerializedName("topic_id")
    @Expose
    private String topicId;
    @ColumnInfo(name = "number")
    @SerializedName("number")
    @Expose
    private String number;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title;
    private boolean haveWords=false;
    private boolean haveReading=false;
    private boolean haveListening=false;
    private boolean haveGrammar=false;
    @Ignore
    @SerializedName("words")
    @Expose
    private List<Word> words = null;
    @Ignore
    @SerializedName("listening")
    @Expose
    private List<Listening> listening = null;
    @Ignore
    @SerializedName("reading")
    @Expose
    private List<Reading> reading = null;
    @Ignore
    @SerializedName("grammar")
    @Expose
    private List<Grammar> grammar = null;


    public boolean isHaveWords() {
        return haveWords;
    }

    public void setHaveWords(boolean haveWords) {
        this.haveWords = haveWords;
    }

    public boolean isHaveReading() {
        return haveReading;
    }

    public void setHaveReading(boolean haveReading) {
        this.haveReading = haveReading;
    }

    public boolean isHaveListening() {
        return haveListening;
    }

    public void setHaveListening(boolean haveListening) {
        this.haveListening = haveListening;
    }

    public boolean isHaveGrammar() {
        return haveGrammar;
    }

    public void setHaveGrammar(boolean haveGrammar) {
        this.haveGrammar = haveGrammar;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public List<Listening> getListening() {
        return listening;
    }

    public void setListening(List<Listening> listening) {
        this.listening = listening;
    }

    public List<Reading> getReading() {
        return reading;
    }

    public void setReading(List<Reading> reading) {
        this.reading = reading;
    }

    public List<Grammar> getGrammar() {
        return grammar;
    }

    public void setGrammar(List<Grammar> grammar) {
        this.grammar = grammar;
    }
}
