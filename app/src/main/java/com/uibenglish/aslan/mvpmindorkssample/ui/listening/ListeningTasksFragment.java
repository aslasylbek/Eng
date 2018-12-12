package com.uibenglish.aslan.mvpmindorkssample.ui.listening;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.ui.FragmentsListener;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Listening;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Questionanswer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListeningTasksFragment extends Fragment implements ListeningTaskAdapter.ListeningTaskListener{

    private static final String TAG = "ListeningTasksFragment";
    private static final String ARTICLE_KEY = "key";

    @BindView(R.id.rvListeningTasks)
    RecyclerView mTaskRView;

    @BindView(R.id.btnSentResult)
    Button mSendResult;

    private Unbinder mUnbinder;

    private List<Questionanswer> editModelArrayList;
    private List<Listening> listeningList;
    private int correct = 0;
    private FragmentsListener listener;
    private ListeningTaskAdapter customAdapter;


    public static ListeningTasksFragment newInstance(List<Listening> list, int position) {
        Bundle args = new Bundle();
        ListeningTasksFragment fragment = new ListeningTasksFragment();
        args.putParcelableArrayList(ARTICLE_KEY, new ArrayList<>(list));
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listening_tasks, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        listener = (FragmentsListener)getActivity();
        if (getArguments()!=null) {
             listeningList = getArguments().getParcelableArrayList(ARTICLE_KEY);
        }
        int position = getArguments().getInt("position");
        editModelArrayList = listeningList.get(position-1).getQuestionanswer();
        customAdapter = new ListeningTaskAdapter( this);
        mTaskRView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mTaskRView.setNestedScrollingEnabled(false);
        mTaskRView.setAdapter(customAdapter);
        customAdapter.setEditModelArrayList(editModelArrayList);
        return view;

    }

    @OnClick(R.id.btnSentResult)
    public void onSendClicked(){
        mTaskRView.clearFocus();
        if (editModelArrayList.size()>0) {
            int result = correct * 100 / editModelArrayList.size();
            listener.sendData(result, "");
        }
        else getActivity().finish();
    }

    @Override
    public void onSendingResult() {
        correct++;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        if (customAdapter!=null){
            customAdapter = null;
        }
        if (listener!=null){
            listener = null;
        }
        if (mUnbinder!=null){
            mUnbinder = null;
        }
        super.onDestroyView();
    }
}