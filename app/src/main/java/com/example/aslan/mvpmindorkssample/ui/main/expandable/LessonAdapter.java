package com.example.aslan.mvpmindorkssample.ui.main.expandable;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aslan.mvpmindorkssample.R;
import com.thoughtbot.expandablerecyclerview.ExpandableListUtils;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;

import java.util.List;

public class LessonAdapter extends ExpandableRecyclerViewAdapter<LessonViewHolder, LessonTopicViewHolder> {

    private TopicClickListener listener;
    private List<? extends ExpandableGroup> mGroups;

    public LessonAdapter(List<? extends ExpandableGroup> groups, TopicClickListener topicClickListener) {
        super(groups);
        mGroups = groups;
        listener = topicClickListener;

    }

    public void addAll(List<LessonParentItem> groups) {
        ((List<LessonParentItem>)getGroups()).clear();
        ((List<LessonParentItem>)getGroups()).addAll(groups);
        ExpandableListUtils.notifyGroupDataChanged(this);
        Log.e("AAA", "addAll: Notified" );
        notifyDataSetChanged();
    }

    @Override
    public LessonViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lessons_item, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public LessonTopicViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_topic_item, parent, false);
        return new LessonTopicViewHolder(view, listener);
    }

    @Override
    public void onBindChildViewHolder(LessonTopicViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final LessonChildItem lessonChildItem = ((LessonParentItem)group).getItems().get(childIndex);
        holder.onBind(lessonChildItem, flatPosition);
    }

    @Override
    public void onBindGroupViewHolder(LessonViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setLessonTitle(group);
    }

    /*@Override
    public void onGroupExpanded(int positionStart, int itemCount) {

        if (itemCount > 0) {
            int groupIndex = expandableList.getUnflattenedPosition(positionStart).groupPos;
            notifyItemRangeInserted(positionStart, itemCount);
            for (ExpandableGroup grp : mGroups) {
                if (grp != mGroups.get(groupIndex)) {
                    if (this.isGroupExpanded(grp)) {
                        this.toggleGroup(grp);
                        this.notifyDataSetChanged();
                    }
                }
            }
        }
    }*/

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ExpandableListPosition listPos = expandableList.getUnflattenedPosition(position);
        ExpandableGroup group = expandableList.getExpandableGroup(listPos);
        switch (listPos.type) {
            case ExpandableListPosition.GROUP:
                onBindGroupViewHolder((LessonViewHolder) holder, position, group);

                if (isGroupExpanded(group)) {
                    ((LessonViewHolder) holder).expand();
                } else {
                    ((LessonViewHolder) holder).collapse();
                }
                break;
            case ExpandableListPosition.CHILD:
                onBindChildViewHolder((LessonTopicViewHolder) holder, position, group, listPos.childPos);
                break;
        }
    }
}
