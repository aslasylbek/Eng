package com.example.aslan.mvpmindorkssample.ui.main.expandable;

import android.os.Parcel;
import android.os.Parcelable;


public class LessonChildItem implements Parcelable{

    private String mTopicName;
    private int mTopicPhoto;
    private String mTopicId;

    public LessonChildItem(String mTopicName, int mTopicPhoto, String mTopicId) {
        this.mTopicName = mTopicName;
        this.mTopicPhoto = mTopicPhoto;
        this.mTopicId = mTopicId;
    }
    protected LessonChildItem(Parcel in){
        mTopicName = in.readString();
        mTopicPhoto = in.readInt();
        mTopicId = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTopicName);
        dest.writeInt(mTopicPhoto);
        dest.writeString(mTopicId);

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
