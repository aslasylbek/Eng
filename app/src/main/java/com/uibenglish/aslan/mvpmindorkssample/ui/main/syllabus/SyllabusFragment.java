package com.uibenglish.aslan.mvpmindorkssample.ui.main.syllabus;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.uibenglish.aslan.mvpmindorkssample.MvpApp;
import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BaseFragment;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Topic;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.expandable.LessonAdapter;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.expandable.LessonParentItem;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.expandable.LessonChildItem;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.expandable.TopicClickListener;
import com.uibenglish.aslan.mvpmindorkssample.ui.tasks.TaskChoiceActivity;
import com.uibenglish.aslan.mvpmindorkssample.utils.DateTimeUtils;
import com.uibenglish.aslan.mvpmindorkssample.widget.DividerItemDecoration;
import com.thoughtbot.expandablerecyclerview.listeners.OnGroupClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SyllabusFragment extends BaseFragment implements TopicClickListener, SyllabusMvpView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "SyllabusFragment";
    private static final String EMPTY = "";

    @BindView(R.id.mRecyclerView) RecyclerView mRView;

    @BindView(R.id.srl_content_container)
    SwipeRefreshLayout mSwipeContainer;

    private LessonAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private SyllabusPresenter syllabusPresenter;
    private AlarmManager am;
    private BroadcastReceiver receiver;
    private PendingIntent pendingIntent;
    private long nearUpdateTime;


    @Override
    protected void init(@Nullable Bundle bundle) {
        Log.e(TAG, "init: " );
        mSwipeContainer.setOnRefreshListener(this);
        mSwipeContainer.setColorSchemeColors(ContextCompat.getColor(getBaseActivity(), R.color.colorAccent));
        List<LessonParentItem> empty = new ArrayList<>();
        adapter = new LessonAdapter(empty, this);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mRView.setLayoutManager(linearLayoutManager);
        mRView.setAdapter(adapter);
        /***
         * Check Item decor
         */
        if (getActivity()!=null)
            mRView.addItemDecoration(new DividerItemDecoration(getActivity()));
        mRView.setHasFixedSize(true);
        DataManager dataManager = ((MvpApp)getActivity().getApplication()).getDataManager();
        syllabusPresenter = new SyllabusPresenter(dataManager);
        syllabusPresenter.attachView(this);
        syllabusPresenter.getTopicsInformation();
        startBroadcast();
    }

    /***
     * YOUR LAST CHANGE
     */
    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: "+nearUpdateTime);
        if (nearUpdateTime>System.currentTimeMillis()/1000){
            restartNotify(nearUpdateTime);
        }
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
       // LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(mRView.getContext(), resId);
        //adapter.getGroups().clear();
        //adapter = new LessonAdapter(lessonParentItems, this);
        //mRView.setAdapter(adapter);

        adapter.addAll(lessonParentItems);
        Log.e(TAG, "changedData: "+nearUpdateTime);
        if (nearUpdateTime>0)
            restartNotify(nearUpdateTime);
        //mRView.setLayoutAnimation(controller);
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
        mSwipeContainer.setRefreshing(false);
        List<LessonParentItem> parent = new ArrayList<>();
        long timeStamp = System.currentTimeMillis()/1000;
        nearUpdateTime = 0;
        for (int j = 0; j <topicsData.size(); j++){
            String mTitle = topicsData.get(j).getTitle();
            Topic topic = topicsData.get(j);
            List<LessonChildItem> list = new ArrayList<>();

            if (!topic.getGrammar().get(0).getMissword().isEmpty()||!topic.getGrammar().get(0).getConstructor().isEmpty()) {
                LessonChildItem lessonChildItem = addToList(
                        topic.getStartGram(),
                        topic.getEndGram(),
                        timeStamp, R.string.item_grammar,
                        R.drawable.rsz_grammar_background,
                        R.drawable.grammar_background,
                        mTitle,
                        topic.getTopicId());
                list.add(lessonChildItem);
            }

            if (!topic.getListening().isEmpty()) {
                LessonChildItem lessonChildItem = addToList(
                        topic.getStartListen(),
                        topic.getEndListen(),
                        timeStamp,
                        R.string.item_listening,
                        R.drawable.rsz_listening_background,
                        R.drawable.listening_background11,
                        mTitle,
                        topic.getTopicId());
                list.add(lessonChildItem);
            }

            if (!topic.getReading().isEmpty()) {
                LessonChildItem lessonChildItem = addToList(
                        topic.getStartRead(),
                        topic.getEndRead(),
                        timeStamp,
                        R.string.item_reading,
                        R.drawable.rsz_reading_background,
                        R.drawable.reading_background,
                        mTitle,
                        topic.getTopicId());
                list.add(lessonChildItem);
            }

            if (!topic.getWords().isEmpty()) {
                LessonChildItem lessonChildItem = addToList(
                        topic.getStartVoc(),
                        topic.getEndVoc(),
                        timeStamp,
                        R.string.item_vocabulary,
                        R.drawable.rsz_vocabulary_background,
                        R.drawable.vocabulary_background,
                        mTitle,
                        topic.getTopicId());
                list.add(lessonChildItem);
            }
            parent.add(new LessonParentItem(mTitle, list));
        }
        changedData(parent);
    }

    @Override
    public void setOnErrorMessage() {
        mSwipeContainer.setRefreshing(false);
    }

    public LessonChildItem addToList(String startChapterTime, String endChapterTime, long timeStamp, int childTitle, int childBackground, int childBackS, String title, String topicId){
        int imageId;
        String startFormatDate = "";
        String endFormatDate = "";
        if (!startChapterTime.equals(EMPTY) && !endChapterTime.equals(EMPTY)) {
            long startTime = Long.parseLong(startChapterTime);
            long endTime = Long.parseLong(endChapterTime);
            startFormatDate = " from "+DateTimeUtils.formatRelativeTime(startTime);
            endFormatDate = " until "+DateTimeUtils.formatRelativeTime(endTime);
            if (timeStamp >= startTime && timeStamp <= endTime) {
                imageId = 0;
                if (nearUpdateTime<=endTime)
                    nearUpdateTime = endTime;
            }
            else {
                imageId = R.drawable.ic_lock_outline;
                if (timeStamp<startTime && nearUpdateTime<startTime)
                    nearUpdateTime = startTime;
            }
        }
        else if (!startChapterTime.equals(EMPTY) && endChapterTime.equals(EMPTY)){
            long startTime = Long.parseLong(startChapterTime);
            startFormatDate = " from "+DateTimeUtils.formatRelativeTime(startTime);
            if (timeStamp >= startTime) {
                imageId = 0;
            }
            else {
                imageId = R.drawable.ic_lock_outline;
                if (nearUpdateTime<startTime)
                    nearUpdateTime = startTime;
            }
        }
        else if (startChapterTime.equals(EMPTY) && !endChapterTime.equals(EMPTY)){
            long endTime = Long.parseLong(endChapterTime);
            endFormatDate = " until "+DateTimeUtils.formatRelativeTime(endTime);
            if (timeStamp <= endTime) {
                imageId = 0;
                if (nearUpdateTime<=endTime)
                    nearUpdateTime = endTime;
            }
            else imageId = R.drawable.ic_lock_outline;
        }
        else
            imageId = 0;

        return new LessonChildItem(
                getString(childTitle),
                childBackground,
                childBackS,
                topicId,
                title,
                startFormatDate,
                endFormatDate,
                imageId);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void startBroadcast(){
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent i) {
                syllabusPresenter.getTopicsInformation();
            }
        };
        getBaseActivity().registerReceiver(receiver, new IntentFilter("com.authorwjf.wakeywakey") );
        pendingIntent = PendingIntent.getBroadcast( getBaseActivity(), 0, new Intent("com.authorwjf.wakeywakey"),
                PendingIntent.FLAG_UPDATE_CURRENT );
    }

    private void restartNotify(long timeStamp) {
        am = (AlarmManager) getBaseActivity().getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, timeStamp*1000, pendingIntent);
    }

    @Override
    public void onRefresh() {
        if (am!=null){
            am.cancel(pendingIntent);
        }
        syllabusPresenter.getTopicsInformation();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (am!=null){
            am.cancel(pendingIntent);
        }
    }

    @Override
    public void onDestroyView() {
        if (adapter!=null && mRView!=null){
            mRView.setAdapter(null);
            adapter = null;
        }
        syllabusPresenter.detachView();

        if (am!=null&&pendingIntent!=null) {
            am.cancel(pendingIntent);
        }
        if (receiver!=null)
            getBaseActivity().unregisterReceiver(receiver);
        super.onDestroyView();

    }
}
