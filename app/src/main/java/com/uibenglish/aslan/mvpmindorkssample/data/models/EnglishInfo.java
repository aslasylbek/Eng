package com.uibenglish.aslan.mvpmindorkssample.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.English;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Info;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Topic;

import java.util.List;

public class EnglishInfo {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("info")
    @Expose
    private List<Info> info = null;
    @SerializedName("english")
    @Expose
    private List<EnglishWithoutTopics> english = null;

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

    public List<Info> getInfo() {
        return info;
    }

    public void setInfo(List<Info> info) {
        this.info = info;
    }

    public List<EnglishWithoutTopics> getEnglish() {
        return english;
    }

    public void setEnglish(List<EnglishWithoutTopics> english) {
        this.english = english;
    }

    public class EnglishWithoutTopics {

        @SerializedName("course_id")
        @Expose
        private String courseId;
        @SerializedName("disp_name")
        @Expose
        private String dispName;
        @SerializedName("teacher_id")
        @Expose
        private String teacherId;
        @SerializedName("teacher_fio")
        @Expose
        private String teacherFio;
        @SerializedName("vsk1")
        @Expose
        private String vsk1;
        @SerializedName("vsk2")
        @Expose
        private String vsk2;
        @SerializedName("exam")
        @Expose
        private String exam;
        @SerializedName("total")
        @Expose
        private String total;
        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getDispName() {
            return dispName;
        }

        public void setDispName(String dispName) {
            this.dispName = dispName;
        }

        public String getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(String teacherId) {
            this.teacherId = teacherId;
        }

        public String getTeacherFio() {
            return teacherFio;
        }

        public void setTeacherFio(String teacherFio) {
            this.teacherFio = teacherFio;
        }

        public String getVsk1() {
            return vsk1;
        }

        public void setVsk1(String vsk1) {
            this.vsk1 = vsk1;
        }

        public String getVsk2() {
            return vsk2;
        }

        public void setVsk2(String vsk2) {
            this.vsk2 = vsk2;
        }

        public String getExam() {
            return exam;
        }

        public void setExam(String exam) {
            this.exam = exam;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    }
}
