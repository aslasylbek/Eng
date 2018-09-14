package com.example.aslan.mvpmindorkssample.ui.main.expandable;

import android.os.Parcel;
import android.os.Parcelable;


public class LessonChildItem implements Parcelable{

    private String mTopicName;
    private int mTopicPhoto;
    private int mTopicPhotoS;
    private String mTopicId;
    private String mLessonName;
    private String mStartTime;
    private String mEndTime;
    private int mIconAccess;

    public LessonChildItem(String mTopicName, int mTopicPhoto, int mTopicPhotoS, String mTopicId, String mLessonName, String mStartTime, String mEndTime, int mIconAccess) {
        this.mTopicName = mTopicName;
        this.mTopicPhoto = mTopicPhoto;
        this.mTopicId = mTopicId;
        this.mTopicPhotoS = mTopicPhotoS;
        this.mLessonName = mLessonName;
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        this.mIconAccess = mIconAccess;

    }
    protected LessonChildItem(Parcel in){
        mTopicName = in.readString();
        mTopicPhoto = in.readInt();
        mTopicPhotoS = in.readInt();
        mTopicId = in.readString();
        mLessonName = in.readString();
        mStartTime = in.readString();
        mEndTime = in.readString();
        mIconAccess = in.readInt();
    }
    public String getTopicName() {
        return mTopicName;
    }

    public void setTopicName(String mTopicName) {
        this.mTopicName = mTopicName;
    }

    public int getTopicPhoto() {
        return mTopicPhoto;
    }

    public void setTopicPhoto(int mTopicPhoto) {
        this.mTopicPhoto = mTopicPhoto;
    }

    public String getTopicId() {
        return mTopicId;
    }

    public void setTopicId(String mTopicId) {
        this.mTopicId = mTopicId;
    }

    public String getLessonName() {
        return mLessonName;
    }

    public void setLessonName(String mLessonName) {
        this.mLessonName = mLessonName;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }

    public int getIconAccess() {
        return mIconAccess;
    }

    public void setIconAccess(int mIconAccess) {
        this.mIconAccess = mIconAccess;
    }

    public int getTopicPhotoS() {
        return mTopicPhotoS;
    }

    public void setTopicPhotoS(int mTopicPhotoS) {
        this.mTopicPhotoS = mTopicPhotoS;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTopicName);
        dest.writeInt(mTopicPhoto);
        dest.writeInt(mTopicPhotoS);
        dest.writeString(mTopicId);
        dest.writeString(mLessonName);
        dest.writeString(mStartTime);
        dest.writeString(mEndTime);
        dest.writeInt(mIconAccess);

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public LessonChildItem createFromParcel(Parcel source) {
            return new LessonChildItem(source);
        }

        @Override
        public LessonChildItem[] newArray(int size) {
            return new LessonChildItem[size];
        }
    };
}
