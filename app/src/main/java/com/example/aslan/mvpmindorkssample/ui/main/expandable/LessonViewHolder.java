package com.example.aslan.mvpmindorkssample.ui.main.expandable;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aslan.mvpmindorkssample.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LessonViewHolder extends GroupViewHolder {

    @BindView(R.id.lessonName) TextView mLessonName;
    @BindView(R.id.imageArrow) ImageView mArrow;
    @BindView(R.id.rootItem) LinearLayout rootItem;

    public LessonViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void setLessonTitle(ExpandableGroup group){
        if (group instanceof LessonParentItem) {
            mLessonName.setText(group.getTitle());
        }

    }

    @Override
    public void expand() {
        //rootItem.setBackgroundColor(Color.rgb(3,155,231));

    }

    @Override
    public void collapse() {
        //rootItem.setBackgroundColor(Color.rgb(255, 255, 255 ));
    }


    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        mArrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        mArrow.setAnimation(rotate);
    }
}
