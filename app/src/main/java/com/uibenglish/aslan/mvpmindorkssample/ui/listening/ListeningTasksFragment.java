package com.uibenglish.aslan.mvpmindorkssample.ui.listening;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.uibenglish.aslan.mvpmindorkssample.MvpApp;
import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCTaskArray;
import com.uibenglish.aslan.mvpmindorkssample.ui.FragmentsListener;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BaseFragment;
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
public class ListeningTasksFragment extends BaseFragment implements ListeningTaskContract.ListeningTaskMvpView, DialogInterface.OnClickListener {

    private static final String TAG = "ListeningTasksFragment";
    private static final String ARTICLE_KEY = "key";
    private static final String TOPIC_ID = "topic_id";

    @BindView(R.id.rvListeningTasks)
    RecyclerView mTaskRView;

    @BindView(R.id.btnSentResult)
    Button mSendResult;

    private List<Questionanswer> editModelArrayList;
    private List<Listening> listeningList;
    private int correct = 0;
    private FragmentsListener listener;
    private ListeningTaskAdapter customAdapter;
    private ListeningTaskPresenter presenter;
    private long startTime;
    private String topicId;
    private int result;


    public static ListeningTasksFragment newInstance(List<Listening> list, String topicId) {
        Bundle args = new Bundle();
        ListeningTasksFragment fragment = new ListeningTasksFragment();
        args.putParcelableArrayList(ARTICLE_KEY, new ArrayList<>(list));
        args.putString(TOPIC_ID, topicId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init(@Nullable Bundle bundle) {
        listener = (FragmentsListener)getBaseActivity();
        if (getArguments()!=null) {
            listeningList = getArguments().getParcelableArrayList(ARTICLE_KEY);
            topicId = getArguments().getString(TOPIC_ID);
        }
        editModelArrayList = listeningList.get(0).getQuestionanswer();
        customAdapter = new ListeningTaskAdapter();
        mTaskRView.setLayoutManager(new LinearLayoutManager(getBaseActivity(), LinearLayoutManager.VERTICAL, false));
        mTaskRView.setNestedScrollingEnabled(false);
        mTaskRView.setAdapter(customAdapter);
        customAdapter.setEditModelArrayList(editModelArrayList);
        DataManager manager = ((MvpApp)getBaseActivity().getApplicationContext()).getDataManager();
        presenter = new ListeningTaskPresenter(manager);
        presenter.attachView(this);

    }

    @Override
    protected int getContentResource() {
        return R.layout.fragment_listening_tasks;
    }

    @Override
    public void onStart() {
        super.onStart();
        startTime = System.currentTimeMillis()/1000;
    }

    @OnClick(R.id.btnSentResult)
    public void onSendClicked(){
        mTaskRView.clearFocus();

        if (editModelArrayList.size()>0) {
            result = correct * 100 / editModelArrayList.size();
            if (mSendResult.getText().toString().equals("Next")){

                AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity());
                builder.setTitle("Send result and check answer?")
                        .setCancelable(true)
                        .setNegativeButton("Cancel", this)
                        .setPositiveButton("OK", this);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else{

                listener.sendData(result, "");
            }
        }
        else getBaseActivity().finish();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which==-2){
            dialog.cancel();
            return;
        }

        for (int i = 0; i < editModelArrayList.size(); i++) {
            Questionanswer questionObject =  editModelArrayList.get(i);
            int mEditTextColor = R.color.colorIncorrect;
            if (questionObject.getUserAnswer() != null) {
                String convertToLower = questionObject.getUserAnswer().toLowerCase();
                String trimText = convertToLower.trim();
                if (questionObject.getAnswer().equals(trimText)) {
                    mEditTextColor = R.color.colorCorrect;
                    correct++;
                }
            }
            editModelArrayList.get(i).setUserAnswer(questionObject.getAnswer());
            questionObject.setEditTextColor("#" + Integer.toHexString(ContextCompat.getColor(getBaseActivity(), mEditTextColor)));
        }
        presenter.postListeningResult(topicId, result, startTime);

    }


    @Override
    public void updateUI() {
        mSendResult.setText("Finish");
        customAdapter.notifyItemRangeChanged(0, editModelArrayList.size());
    }

    @Override
    public void onDestroyView() {
        if (presenter.isAttached())
            presenter.detachView();
        if (customAdapter!=null){
            customAdapter = null;
        }
        if (listener!=null){
            listener = null;
        }
        super.onDestroyView();
    }
}
