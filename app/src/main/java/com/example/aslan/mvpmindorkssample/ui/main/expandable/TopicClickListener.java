package com.example.aslan.mvpmindorkssample.ui.main.expandable;

import android.view.View;

public interface TopicClickListener {

    void itemTopicClick(LessonChildItem lessonChildItem, View imageView);

    void itemTopicNoAccess(View view, String deadlineTime);

}
