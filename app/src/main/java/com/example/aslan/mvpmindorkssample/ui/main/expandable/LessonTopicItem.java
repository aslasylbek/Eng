package com.example.aslan.mvpmindorkssample.ui.main.expandable;

import android.os.Parcel;
import android.os.Parcelable;


public class LessonTopicItem implements Parcelable{

    private String mTopicName;
    private String mTopicPhoto;
    private int mTopicId;

    public LessonTopicItem(String mTopicName, String mTopicPhoto) {
        this.mTopicName = mTopicName;
        this.mTopicPhoto = mTopicPhoto;
    }
    protected LessonTopicItem(Parcel in){
        mTopicName = in.readString();
        mTopicPhoto = in.readString();
    }
    public String getTopicName() {
        return mTopicName;
    }

    public void setTopicName(String mTopicName) {
        this.mTopicName = mTopicName;
    }

    public String getTopicPhoto() {
        return mTopicPhoto;
    }

    public void setTopicPhoto(String mTopicPhoto) {
        this.mTopicPhoto = mTopicPhoto;
    }

    public int getTopicId() {
        return mTopicId;
    }

    public void setTopicId(int mTopicId) {
        this.mTopicId = mTopicId;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTopicName);
        dest.writeString(mTopicPhoto);

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public LessonTopicItem createFromParcel(Parcel source) {
            return new LessonTopicItem(source);
        }

        @Override
        public LessonTopicItem[] newArray(int size) {
            return new LessonTopicItem[size];
        }
    };
}
