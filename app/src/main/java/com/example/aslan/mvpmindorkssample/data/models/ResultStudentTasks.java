package com.example.aslan.mvpmindorkssample.data.models;

import com.example.aslan.mvpmindorkssample.ui.main.content.Topic;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultStudentTasks {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("topics")
    @Expose
    private List<ResultTopic> topics = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultTopic> getTopics() {
        return topics;
    }

    public void setTopics(List<ResultTopic> topics) {
        this.topics = topics;
    }
}
