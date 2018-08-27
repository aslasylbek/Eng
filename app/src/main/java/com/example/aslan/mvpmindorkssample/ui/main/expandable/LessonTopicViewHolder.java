package com.example.aslan.mvpmindorkssample.ui.main.expandable;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aslan.mvpmindorkssample.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LessonTopicViewHolder extends ChildViewHolder implements View.OnClickListener{

    @BindView(R.id.mTopicName) TextView mTopicTitle;
    @BindView(R.id.mTopicPhoto) ImageView mTopicPhoto;
    private TopicClickListener listener;
    private LessonChildItem lessonChildItem;


    //Can remove Context but firstly check it
    public LessonTopicViewHolder(View itemView, TopicClickListener topicClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        listener = topicClickListener;
    }

    public void onBind(LessonChildItem item){
        lessonChildItem = item;
        if (item!=null) {
            mTopicTitle.setText(item.getTopicName());
            mTopicPhoto.setImageResource(item.getTopicPhoto());
        }
    }
    @Override
    public void onClick(View v) {
        listener.itemTopicClick(lessonChildItem, v);

    }
}
