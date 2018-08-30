package com.example.aslan.mvpmindorkssample.ui;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aslan.mvpmindorkssample.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinishFragment extends Fragment {

    private static final String RESULT = "res";
    @BindView(R.id.btnFinish)
    Button mFinishTasks;

    @BindView(R.id.tvTest)
    TextView textView;

    @BindView(R.id.progressBar)
    ProgressBar mProgressResultBar;

    @BindView(R.id.tvProgressResult)
    TextView mTvProgressResult;


    public static FinishFragment newInstance(int str) {
        Bundle args = new Bundle();
        FinishFragment fragment = new FinishFragment();
        args.putInt(RESULT, str);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finish, container, false);
        ButterKnife.bind(this, view);

        int result = 101;
        if (getArguments()!=null)
             result = getArguments().getInt("res");
        if (result==100){
            textView.setText("You are genius!");
        }
        else if (result>100){
            textView.setText(getResources().getString(R.string.get_wrong));
        }
        else if (result>50){
            textView.setText("Excellent!\n\nYou can improve result");
        }
        else if (result==0){
            textView.setText("Oops!\n\nTry again");
        }
        else  textView.setText("Good!\n\nYou can improve result");

        mFinishTasks.setText("Complete");

        ObjectAnimator animation = ObjectAnimator.ofInt(mProgressResultBar, "progress", 0, result);
        animation.setDuration(3000); // in milliseconds
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
        mProgressResultBar.setProgress(result);
        mTvProgressResult.setText(String.valueOf(result)+getString(R.string.percentage));
        return view;
    }

    @OnClick(R.id.btnFinish)
    public void finishTasks(){
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        mProgressResultBar.clearAnimation();
        super.onDestroyView();
    }
}
