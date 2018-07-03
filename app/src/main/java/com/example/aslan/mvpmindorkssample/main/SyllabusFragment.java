package com.example.aslan.mvpmindorkssample.main;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.main.expandable.LessonAdapter;
import com.example.aslan.mvpmindorkssample.main.expandable.LessonItem;
import com.example.aslan.mvpmindorkssample.main.expandable.LessonTopicItem;
import com.example.aslan.mvpmindorkssample.main.expandable.TopicClickListener;
import com.example.aslan.mvpmindorkssample.tasks.TaskChoiceActivity;
import com.example.aslan.mvpmindorkssample.widget.DividerItemDecoration;
import com.thoughtbot.expandablerecyclerview.listeners.OnGroupClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SyllabusFragment extends Fragment implements TopicClickListener {

    private static final String TAG = "SyllabusFragment";

    @BindView(R.id.mRecyclerView) RecyclerView mRView;

    private LessonAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private View view;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view==null) {
             view = inflater.inflate(R.layout.fragment_syllabus, container, false);
        }
        ButterKnife.bind(this, view);

        List<LessonItem> empty = new ArrayList<>();
        adapter = new LessonAdapter(empty, this);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mRView.setLayoutManager(linearLayoutManager);
        mRView.setAdapter(adapter);
        if (getActivity()!=null)
            mRView.addItemDecoration(new DividerItemDecoration(getActivity()));
        mRView.setHasFixedSize(true);
        changedData(getDummyDataToPass());
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void itemTopicClick(LessonTopicItem lessonTopicItem, View view) {
        ImageView imageView = view.findViewById(R.id.mTopicPhoto);
        TaskChoiceActivity.navigate((Main2Activity)getActivity(), imageView, lessonTopicItem);
    }

    public void changedData(List<LessonItem> lessonItems){
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(mRView.getContext(), resId);
        adapter.getGroups().clear();
        adapter = new LessonAdapter(lessonItems, this);
        //mRView.setLayoutAnimation(controller);
        mRView.setAdapter(adapter);
        adapter.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(int flatPos) {
                linearLayoutManager.scrollToPositionWithOffset(flatPos, linearLayoutManager.getPaddingTop());
                return false;
            }
        });
    }

    private void scroolList(int pos){

        mRView.post(new Runnable() {
            int index;
            @Override
            public void run() {
                mRView.smoothScrollToPosition(index);

            }
            public Runnable init (int pos){
                this.index = pos;
                return (this);
            }
        }.init(pos));
    }


    private List<LessonItem> getDummyDataToPass() {
        List<LessonItem> parent = new ArrayList<>();
        List<LessonTopicItem> list = new ArrayList<>();

        for (int i=0; i<2; i++) {
            list.add(new LessonTopicItem("Topic"+i, "https://static.adweek.com/adweek.com-prod/wp-content/uploads/2017/10/VSCO-brands-CONTENT-2017.jpg"));
        }
        for (int j = 0; j <15; j++){
            parent.add(new LessonItem("Lesson"+j, list));
        }
        return parent;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
        if (adapter!=null && mRView!=null){
            mRView.setAdapter(null);
            adapter = null;
        }
    }
}
