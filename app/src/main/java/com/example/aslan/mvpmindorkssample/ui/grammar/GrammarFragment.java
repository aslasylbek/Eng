package com.example.aslan.mvpmindorkssample.ui.grammar;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.FragmentsListener;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.ResultContent;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GrammarFragment extends Fragment {

    @BindView(R.id.textView3)
    TextView mBuilder;

    @BindView(R.id.mGrammarTranslate)
    TextView mGrammarTranslate;

    @BindView(R.id.llParts)
    LinearLayout linearLayout;

    private FragmentsListener listener;

    private int checker = 0;

    private int correct = 0;

    public GrammarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grammar, container, false);
        ButterKnife.bind(this, view);

        listener = (FragmentsListener)getActivity();

        String translate = getArguments().getString("keyTrans");
        mGrammarTranslate.setText(translate);

        String text = getArguments().getString("keyText");
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
                                listener.sendData(correct, "");
                            }
                        });
                    }
                }
            });
        }

        return view;
    }

}
