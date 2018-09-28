package com.uibenglish.aslan.mvpmindorkssample.ui;


import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.uibenglish.aslan.mvpmindorkssample.R;
import com.timqi.sectorprogressview.ColorfulRingProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinishFragment extends Fragment {

    private static final String RESULT = "res";
    @BindView(R.id.btnFinish)
    Button mFinishTasks;


    private int pStatus = 0;
    private Handler handler = new Handler();


    @BindView(R.id.tvTest)
    TextView textView;

    @BindView(R.id.spv)
    ColorfulRingProgressView mProgressResultBar;

    @BindView(R.id.tvProgressResult)
    TextView mTvProgressResult;

    private Unbinder mUnbinder;


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
        mUnbinder = ButterKnife.bind(this, view);

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

        mTvProgressResult.setText(result+getString(R.string.percentage));

        final int finalResult = result;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (finalResult>0) {
                    while (pStatus <= finalResult) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mProgressResultBar.setPercent(pStatus);
                                mTvProgressResult.setText(pStatus + " %");
                            }
                        });
                        try {
                            Thread.sleep(15);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        pStatus++;
                    }
                }
                else {
                    mProgressResultBar.setPercent(pStatus);
                    mTvProgressResult.setText(pStatus + " %");
                }
            }
        }).start();
        return view;
    }

    @OnClick(R.id.btnFinish)
    public void finishTasks(){
        if (getActivity()!=null)
            getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        mProgressResultBar.clearAnimation();
        if (mUnbinder!=null)
            mUnbinder = null;
        super.onDestroyView();
    }
}
