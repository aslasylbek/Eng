package com.example.aslan.mvpmindorkssample.ui.main.expandable;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aslan.mvpmindorkssample.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LessonTopicViewHolder extends ChildViewHolder implements View.OnClickListener{

    @BindView(R.id.mTopicName) TextView mTopicTitle;
    @BindView(R.id.mTopicPhoto) ImageView mTopicPhoto;
    private TopicClickListener listener;
    private LessonTopicItem lessonTopicItem ;


    //Can remove Context but firstly check it
    public LessonTopicViewHolder(View itemView, TopicClickListener topicClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        listener = topicClickListener;
    }

    public void onBind(LessonTopicItem item){
        lessonTopicItem  = item;
        if (item!=null) {
            mTopicTitle.setText(item.getTopicName());
            if (item.getTopicPhoto()!=null && !item.getTopicPhoto().isEmpty()){
                Glide.with(mTopicPhoto.getContext())
                        .asDrawable()
                        .load(item.getTopicPhoto())
                        .apply(new RequestOptions()
                                .centerCrop()
                                .error(R.drawable.ic_sentiment_very_dissatisfied)
                        )
                        .into(mTopicPhoto);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Log.d("AAA", "onClick: "+lessonTopicItem.getTopicName());
        listener.itemTopicClick(lessonTopicItem, v);

    }
}
