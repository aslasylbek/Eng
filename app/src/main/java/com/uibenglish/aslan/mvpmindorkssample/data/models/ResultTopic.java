package com.uibenglish.aslan.mvpmindorkssample.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultTopic {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("exercises")
    @Expose
    private List<ResultExercise> exercises = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ResultExercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<ResultExercise> exercises) {
        this.exercises = exercises;
    }
}
