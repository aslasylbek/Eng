package com.uibenglish.aslan.mvpmindorkssample.ui.bbcenglish.lesson;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.uibenglish.aslan.mvpmindorkssample.MvpApp;
import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCLesson;
import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCTaskArray;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BaseFragment;
import com.uibenglish.aslan.mvpmindorkssample.ui.grammar.GrammarFragment;
import com.uibenglish.aslan.mvpmindorkssample.widget.BaseAdapter;
import com.uibenglish.aslan.mvpmindorkssample.widget.DividerItemDecoration;
import com.uibenglish.aslan.mvpmindorkssample.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BBCTaskFragment extends BaseFragment implements BBCTaskContract.BBCTaskMvpView, BaseAdapter.OnItemClickListener {

    private static final String POSITION = "position";
    private static final String LIST = "list";
    private static final String LESSON = "lessonId";
    private static final String TAG = "BBCTaskFragment";

    @BindView(R.id.sampleRV)
    EmptyRecyclerView mRecyclerView;

    @BindView(R.id.btnCheckListening)
    Button mBtnCheckOrEnd;

    private BBCTaskAdapter adapter;
    private int position;
    private List<BBCTaskArray> bbcTaskArrays;
    private String lessonId;
    private int score = 0;
    private BBCTaskPresenter presenter;
    private Map<String, String> answers;

    public static BBCTaskFragment newInstance(int position, String lessonId, ArrayList<BBCTaskArray> list) {
        Bundle args = new Bundle();
        BBCTaskFragment fragment = new BBCTaskFragment();
        args.putInt(POSITION, position);
        args.putString(LESSON, lessonId);
        args.putParcelableArrayList(LIST, list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init(@Nullable Bundle bundle) {
        if (getArguments()!=null) {
            position = getArguments().getInt(POSITION);
            bbcTaskArrays = getArguments().getParcelableArrayList(LIST);
            lessonId = getArguments().getString(LESSON);
        }
        answers = new HashMap<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getBaseActivity()));

        adapter = new BBCTaskAdapter(new ArrayList());
        adapter.attachToRecyclerView(mRecyclerView);
        adapter.setOnItemClickListener(this);
        adapter.changeDataSet(bbcTaskArrays);
        if(bbcTaskArrays.get(0).getEditTextColor()!=null){
            mBtnCheckOrEnd.setText("Finish");
        }
        DataManager manager = ((MvpApp)getBaseActivity().getApplicationContext()).getDataManager();
        presenter = new BBCTaskPresenter(manager);
        presenter.attachView(this);
    }

    @Override
    protected int getContentResource() {
        return R.layout.fragment_bbctask;
    }

    @Override
    public void updateUI() {
        mBtnCheckOrEnd.setText("Finish");
        adapter.notifyItemRangeChanged(0, bbcTaskArrays.size());
    }

    @OnClick(R.id.btnCheckListening)
    public void checkAnswerOrEnd(View view){
        mRecyclerView.clearFocus();
        if(mBtnCheckOrEnd.getText().equals("CHECK")) {

            for (int i = 0; i < bbcTaskArrays.size(); i++) {
                BBCTaskArray questionObject =  bbcTaskArrays.get(i);
                int mEditTextColor = R.color.colorIncorrect;
                if (questionObject.getUserAnswer() != null) {
                    String convertToLower = questionObject.getUserAnswer().toLowerCase();
                    String trimText = convertToLower.trim();
                    if (questionObject.getWord().equals(trimText)) {
                        mEditTextColor = R.color.colorCorrect;
                    }
                }
                answers.put("answer["+questionObject.getId()+"]", questionObject.getUserAnswer());
                bbcTaskArrays.get(i).setUserAnswer(questionObject.getWord());
                questionObject.setEditTextColor("#" + Integer.toHexString(ContextCompat.getColor(getBaseActivity(), mEditTextColor)));
            }
            presenter.sendBBCQuestions(lessonId, position, answers);
        }
        else{ getBaseActivity().finish(); }
    }

    @Override
    public void onItemClick(@NonNull Object item) {

    }

    @Override
    public void onDestroyView() {
        if (presenter.isAttached()){
            presenter.detachView();
        }
        super.onDestroyView();
    }
}
