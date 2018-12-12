package com.uibenglish.aslan.mvpmindorkssample.ui.grammar;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.ui.FragmentsListener;
import com.uibenglish.aslan.mvpmindorkssample.ui.vocabulary.ResultContent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class GrammarFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "GrammarFragment";
    private static final String TEXT = "text";
    private static final String TRANSLATE = "translate";

    @BindView(R.id.textView3)
    TextView mBuilder;
    @BindView(R.id.mGrammarTranslate)
    TextView mGrammarTranslate;

    @BindView(R.id.llParts)
    LinearLayout linearLayout;

    @BindView(R.id.btnNext)
    Button mNextButton;

    private ArrayList buildedData = new ArrayList();

    private FragmentsListener listener;

    private int checker = 0;

    private int correct = 0;
    private final List<ResultContent> shuffledList = new ArrayList<>();

    private String translate;
    private String text;

    private Unbinder mUnbinder;
    private Button[] buttons;
    private int buttonCount = 0;


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
        View view = inflater.inflate(R.layout.fragment_grammar, container, false);

        mUnbinder = ButterKnife.bind(this, view);
        listener = (FragmentsListener)getActivity();

        translate = "";
        text = "";
        if (getArguments()!=null) {
            translate = getArguments().getString(TRANSLATE);
            text = getArguments().getString(TEXT);
        }

        updateUI();

        return view;
    }

    public void updateUI(){
        mNextButton.setVisibility(View.GONE);
        mGrammarTranslate.setText(translate);
        String[] correctArray = text.split(" ");
        for (int i=0; i<correctArray.length; i++){
            shuffledList.add(new ResultContent(correctArray[i], i));
        }

        Collections.shuffle(shuffledList);
        buttons = new Button[shuffledList.size()];

        for (int i=0; i<shuffledList.size(); i++){
            buttons[i] = new Button(getActivity());
            buttons[i].setId(shuffledList.get(i).getResult());
            buttons[i].setText(shuffledList.get(i).getChapter());
            buttons[i].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(buttons[i]);
            buttons[i].setOnClickListener(this);
        }
    }

    @OnClick(R.id.textView3)
    public void undoLastWord(){
        mBuilder.setText("");
        buildedData.clear();
        checker = 0;
        buttonCount = 0;
        for (int i = 0; i<shuffledList.size(); i++){
            buttons[i].setVisibility(View.VISIBLE);
        }


    }

    @OnClick(R.id.btnNext)
    public void gotoNextQuestion(){
        listener.sendData(correct, "cons");
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

    @Override
    public void onClick(View v) {
        Button b = linearLayout.findViewById(v.getId());
        if (v.getId()==checker)
            checker++;
        b.setVisibility(View.GONE);
        buttonCount++;
        mBuilder.setText("");
        buildedData.add(b.getText());
        for (int i=0; i< buildedData.size(); i++){
            mBuilder.append(" "+buildedData.get(i));
        }

        if (linearLayout.getChildCount()==buttonCount+1){
            if (checker==shuffledList.size()){
                mBuilder.setTextColor(Color.GREEN);
                correct = 1;
            }
            else {
                mBuilder.setTextColor(Color.RED);
                mGrammarTranslate.setTextColor(Color.GREEN);
                mGrammarTranslate.setText(text);
            }
            mNextButton.setVisibility(View.VISIBLE);
        }
    }
}
