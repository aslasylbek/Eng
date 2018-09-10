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

    @BindView(R.id.ivAccess) ImageView mIconAccess;
    @BindView(R.id.mTopicName) TextView mTopicTitle;
    @BindView(R.id.mTopicPhoto) ImageView mTopicPhoto;
    private TopicClickListener listener;
    private LessonChildItem lessonChildItem;
    private View view;

    //Can remove Context but firstly check it
    public LessonTopicViewHolder(View itemView, TopicClickListener topicClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        view = itemView;
        view.setOnClickListener(this);
        listener = topicClickListener;
    }

    public void onBind(LessonChildItem item, int flatPos){
        lessonChildItem = item;
        view.setTag(flatPos);
        if (item!=null) {
            /*if (!item.getStartTime().equals("")) {
                Log.e("AAA", "onBind: "+item.getStartTime());
                long timeStamp = System.currentTimeMillis() / 1000;
                long startTime = Long.parseLong(item.getStartTime());
                long endTime = Long.parseLong(item.getEndTime());
                //todo delete next line
                mIconAccess.setVisibility(View.INVISIBLE);
            }*/
            if (item.getIconAccess()!=0) {
                mIconAccess.setImageResource(item.getIconAccess());
                mIconAccess.setTag(item.getIconAccess());
            }
            mTopicTitle.setText(item.getTopicName());
            mTopicPhoto.setImageResource(item.getTopicPhoto());
        }
    }
    @Override
    public void onClick(View v) {
        String s = String.valueOf(mIconAccess.getTag());
        if (Integer.parseInt(s) == R.drawable.ic_lock_outline){
            listener.itemTopicNoAccess(v, lessonChildItem.getStartTime()+lessonChildItem.getEndTime());
        }
        else listener.itemTopicClick(lessonChildItem, v);



    }
}
