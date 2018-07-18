package com.example.aslan.mvpmindorkssample.ui.vocabulary.finish;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    FragmentsListener listener ;

    public FinishFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finish, container, false);
        ButterKnife.bind(this, view);
        listener = (FragmentsListener)getActivity();
        mFinishTasks.setText("Finish");
        return view;
    }

    @OnClick(R.id.btnFinish)
    public void finishTasks(){
        listener.sendData(1);
    }


}
