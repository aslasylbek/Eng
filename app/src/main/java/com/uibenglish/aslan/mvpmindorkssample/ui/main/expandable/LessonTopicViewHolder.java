package com.uibenglish.aslan.mvpmindorkssample.ui.main.expandable;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.uibenglish.aslan.mvpmindorkssample.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LessonTopicViewHolder extends ChildViewHolder implements View.OnClickListener{

    @BindView(R.id.ivAccess) ImageView mIconAccess;
    @BindView(R.id.mTopicName) TextView mTopicTitle;
    @BindView(R.id.mTopicPhoto)
    ImageView mTopicPhoto;
    @BindView(R.id.ivTaskOne)
    ImageButton mTaskOne;

    private TopicClickListener listener;
    private LessonChildItem lessonChildItem;
    private View view;

    //Can remove Context but firstly check it
    public LessonTopicViewHolder(View itemView, TopicClickListener topicClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        view = itemView;
        view.setOnClickListener(this);
        mIconAccess.setOnClickListener(this);
        mTaskOne.setOnClickListener(this);
        listener = topicClickListener;
    }

    public void onBind(LessonChildItem item, int flatPos){
        lessonChildItem = item;
        view.setTag(flatPos);
        if (item!=null) {
            if (item.getIconAccess()!=0) {
                mIconAccess.setImageResource(item.getIconAccess());
                mIconAccess.setTag(item.getIconAccess());
                mIconAccess.setVisibility(View.VISIBLE);
                if (item.getTopicName().equals("Vocabulary")) {
                    mTaskOne.setImageResource(R.drawable.ic_university);
                    mTaskOne.setVisibility(View.VISIBLE);
                }
                else mTaskOne.setVisibility(View.INVISIBLE);

            }
            else if(item.getIconAccess()==0){
                mIconAccess.setVisibility(View.INVISIBLE);
                mIconAccess.setTag(item.getIconAccess());
                mTaskOne.setVisibility(View.INVISIBLE);
            }
            mTopicTitle.setText(item.getTopicName());
            mTopicPhoto.setImageResource(item.getTopicPhoto());
        }
    }
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.ivAccess){
            listener.itemTopicNoAccess(v, lessonChildItem.getStartTime() + lessonChildItem.getEndTime());
        }
        else if (v.getId()==R.id.ivTaskOne){
            listener.itemOpenTaskOne(lessonChildItem.getTopicId());
        }
        else {
            String s = String.valueOf(mIconAccess.getTag());
            if (Integer.parseInt(s) == R.drawable.ic_lock_outline) {
                listener.itemTopicNoAccess(v, lessonChildItem.getStartTime() + lessonChildItem.getEndTime());
            } else listener.itemTopicClick(lessonChildItem, v);
        }

    }
}
