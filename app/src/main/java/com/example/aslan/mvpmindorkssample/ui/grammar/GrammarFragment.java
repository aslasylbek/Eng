package com.example.aslan.mvpmindorkssample.ui.grammar;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.ui.FragmentsListener;
import com.example.aslan.mvpmindorkssample.ui.reading.TrueFalseFragment;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.ResultContent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class GrammarFragment extends Fragment {

    private static final String TAG = "GrammarFragment";
    private static final String TEXT = "text";
    private static final String TRANSLATE = "translate";

    @BindView(R.id.textView3)
    TextView mBuilder;

    @BindView(R.id.mGrammarTranslate)
    TextView mGrammarTranslate;

    @BindView(R.id.llParts)
    LinearLayout linearLayout;

    private FragmentsListener listener;

    private int checker = 0;

    private int correct = 0;

    private Unbinder mUnbinder;


    public static GrammarFragment newInstance(String text, String translate) {
        Bundle args = new Bundle();
        GrammarFragment fragment = new GrammarFragment();
        args.putString(TEXT, text);
        args.putString(TRANSLATE, translate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grammar, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        listener = (FragmentsListener)getActivity();

        String translate = "";
        String text = "";
        if (getArguments()!=null) {
            translate = getArguments().getString(TRANSLATE);
            text = getArguments().getString(TEXT);
        }
        mGrammarTranslate.setText(translate);
        String[] arr = text.split(" ");
        final List<ResultContent> testList = new ArrayList<>();

        for (int i=0; i<arr.length; i++){
            testList.add(new ResultContent(arr[i], i));
        }
        Collections.shuffle(testList);

        for (int i=0; i<testList.size(); i++){
            final Button button = new Button(getActivity());
            button.setId(testList.get(i).getResult());
            button.setText(testList.get(i).getChapter());
            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (button.getId()==checker)
                        checker++;
                    linearLayout.removeView(button);
                    mBuilder.append(" "+button.getText());

                    if (linearLayout.getChildCount()==0){
                        if (checker==testList.size()){
                            mBuilder.setTextColor(Color.GREEN);
                            correct = 1;
                        }
                        else mBuilder.setTextColor(Color.RED);
                        Button button1 = new Button(getActivity());
                        button1.setText("Next");
                        button1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                        linearLayout.addView(button1);
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                listener.sendData(correct, "cons");
                            }
                        });
                    }
                }
            });
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        if (mUnbinder!=null)
            mUnbinder = null;
        if (listener!=null){
            listener = null;
        }
        super.onDestroyView();
    }
}
