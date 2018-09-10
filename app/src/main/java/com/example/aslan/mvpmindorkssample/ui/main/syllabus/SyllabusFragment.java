package com.example.aslan.mvpmindorkssample.ui.main.syllabus;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.example.aslan.mvpmindorkssample.MvpApp;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.ui.base.BaseFragment;
import com.example.aslan.mvpmindorkssample.ui.main.Main2Activity;
import com.example.aslan.mvpmindorkssample.ui.main.content.Topic;
import com.example.aslan.mvpmindorkssample.ui.main.content.Word;
import com.example.aslan.mvpmindorkssample.ui.main.expandable.LessonAdapter;
import com.example.aslan.mvpmindorkssample.ui.main.expandable.LessonParentItem;
import com.example.aslan.mvpmindorkssample.ui.main.expandable.LessonChildItem;
import com.example.aslan.mvpmindorkssample.ui.main.expandable.TopicClickListener;
import com.example.aslan.mvpmindorkssample.ui.tasks.TaskChoiceActivity;
import com.example.aslan.mvpmindorkssample.utils.DateTimeUtils;
import com.example.aslan.mvpmindorkssample.widget.DividerItemDecoration;
import com.thoughtbot.expandablerecyclerview.listeners.OnGroupClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SyllabusFragment extends BaseFragment implements TopicClickListener, SyllabusMvpView {

    private static final String TAG = "SyllabusFragment";
    private static final String EMPTY = "";

    @BindView(R.id.mRecyclerView) RecyclerView mRView;

    private LessonAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private SyllabusPresenter syllabusPresenter;
    //private View view;


    @Override
    protected void init(@Nullable Bundle bundle) {
        List<LessonParentItem> empty = new ArrayList<>();
        adapter = new LessonAdapter(empty, this);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mRView.setLayoutManager(linearLayoutManager);
        mRView.setAdapter(adapter);
        if (getActivity()!=null)
            mRView.addItemDecoration(new DividerItemDecoration(getActivity()));
        mRView.setHasFixedSize(true);
        DataManager dataManager = ((MvpApp)getActivity().getApplication()).getDataManager();
        syllabusPresenter = new SyllabusPresenter(dataManager);
        syllabusPresenter.attachView(this);
        syllabusPresenter.getTopicsInformation();
    }

    @Override
    protected int getContentResource() {
        return R.layout.fragment_syllabus;
    }

    @Override
    public void itemTopicClick(LessonChildItem lessonChildItem, View view) {
        ImageView imageView = view.findViewById(R.id.mTopicPhoto);
        TaskChoiceActivity.navigate(getBaseActivity(), imageView, lessonChildItem);
    }

    @Override
    public void itemTopicNoAccess(View view, String deadline) {
        Snackbar.make(view, "Available"+deadline, Snackbar.LENGTH_LONG).show();
    }

    public void changedData(List<LessonParentItem> lessonParentItems){
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(mRView.getContext(), resId);
        //adapter.getGroups().clear();
        adapter.addAll(lessonParentItems);
        //adapter = new LessonAdapter(lessonParentItems, this);
        mRView.setLayoutAnimation(controller);
        //mRView.setAdapter(adapter);
        adapter.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(int flatPos) {
                linearLayoutManager.scrollToPositionWithOffset(flatPos, linearLayoutManager.getPaddingTop());
                return false;
            }
        });
    }

    @Override
    public void setTopicsData(List<Topic> topicsData) {
        List<LessonParentItem> parent = new ArrayList<>();
        long timeStamp = System.currentTimeMillis()/1000;
        for (int j = 0; j <topicsData.size(); j++){
            String mTitle = topicsData.get(j).getTitle();
            Topic topic = topicsData.get(j);
            List<LessonChildItem> list = new ArrayList<>();

            if (!topic.getGrammar().isEmpty()) {
                LessonChildItem lessonChildItem = addToList(topic.getStartGram(), topic.getEndGram(), timeStamp, R.string.item_grammar, R.drawable.grammar_background, mTitle, topic.getTopicId());
                list.add(lessonChildItem);
            }

            if (!topic.getListening().isEmpty()) {
                LessonChildItem lessonChildItem = addToList(topic.getStartListen(), topic.getEndListen(), timeStamp, R.string.item_listening, R.drawable.listening_background, mTitle, topic.getTopicId());
                list.add(lessonChildItem);
            }

            if (!topic.getReading().isEmpty()) {
                LessonChildItem lessonChildItem = addToList(topic.getStartRead(), topic.getEndRead(), timeStamp, R.string.item_reading, R.drawable.reading_background, mTitle, topic.getTopicId());
                list.add(lessonChildItem);
            }

            if (!topic.getWords().isEmpty()) {
                LessonChildItem lessonChildItem = addToList(topic.getStartVoc(), topic.getEndVoc(), timeStamp, R.string.item_vocabulary, R.drawable.vocabulary_background, mTitle, topic.getTopicId());
                list.add(lessonChildItem);
            }
            parent.add(new LessonParentItem(mTitle, list));
        }
        changedData(parent);
    }

    public LessonChildItem addToList(String startChapterTime, String endChapterTime, long timeStamp, int childTitle, int childBackground, String title, String topicId){
        int imageId = 0;
        String startFormatDate = "";
        String endFormatDate = "";
        if (!startChapterTime.equals(EMPTY) && !endChapterTime.equals(EMPTY)) {
            long startTime = Long.parseLong(startChapterTime);
            long endTime = Long.parseLong(endChapterTime);
            startFormatDate = " from "+DateTimeUtils.formatRelativeTime(startTime);
            endFormatDate = " until "+DateTimeUtils.formatRelativeTime(endTime);
            if (timeStamp > startTime && timeStamp < endTime)
                imageId = R.drawable.ic_lock_open;
            else imageId = R.drawable.ic_lock_outline;
        }
        else if (!startChapterTime.equals(EMPTY) && endChapterTime.equals(EMPTY)){
            long startTime = Long.parseLong(startChapterTime);
            startFormatDate = " from "+DateTimeUtils.formatRelativeTime(startTime);
            if (timeStamp > startTime)
                imageId = R.drawable.ic_lock_open;
            else imageId = R.drawable.ic_lock_outline;
        }
        else if (startChapterTime.equals(EMPTY) && !endChapterTime.equals(EMPTY)){
            long endTime = Long.parseLong(endChapterTime);
            endFormatDate = " until "+DateTimeUtils.formatRelativeTime(endTime);
            if (timeStamp < endTime)
                imageId = R.drawable.ic_lock_open;
            else imageId = R.drawable.ic_lock_outline;
        }
        else
            imageId = R.drawable.ic_lock_open;

        return new LessonChildItem(
                getString(childTitle),
                childBackground,
                topicId,
                title,
                startFormatDate,
                endFormatDate,
                imageId);
    }

    @Override
    public void onDestroyView() {
        if (adapter!=null && mRView!=null){
            mRView.setAdapter(null);
            adapter = null;
        }
        syllabusPresenter.detachView();
        super.onDestroyView();

    }

    static class WordItemAnimator extends DefaultItemAnimator {

        @Override
        public boolean animateMove(
                RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
            return false;
        }
    }
}
