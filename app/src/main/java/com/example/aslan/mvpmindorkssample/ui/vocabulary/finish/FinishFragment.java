package com.example.aslan.mvpmindorkssample.ui.vocabulary.finish;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.FragmentsListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinishFragment extends Fragment {

    @BindView(R.id.btnFinish)
    Button mFinishTasks;

    @BindView(R.id.tvTest)
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finish, container, false);
        ButterKnife.bind(this, view);
        int result = getArguments().getInt("res");
        textView.setTextSize(30);
        textView.setText("100/"+String.valueOf(result));
        mFinishTasks.setText("Complete");
        return view;
    }

    @OnClick(R.id.btnFinish)
    public void finishTasks(){
        getActivity().finish();
    }
}
