package com.example.aslan.mvpmindorkssample.ui.grammar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.ui.main.content.Grammar;
import com.example.aslan.mvpmindorkssample.ui.main.content.Reading;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GrammarPagerAdapter extends PagerAdapter {

    @BindView(R.id.textView3)
    TextView mTBuilder;

    @BindView(R.id.mGrammarTranslate)
    TextView mGrammarTranslate;

    @BindView(R.id.llParts)
    LinearLayout mPartsLayout;

    private List<Grammar> grammarList = new ArrayList<>();

    private ViewPager mViewPager;


    public GrammarPagerAdapter() {

    }

    @Override
    public int getCount() {
        return grammarList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater)container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.item_grammar, null);

        ButterKnife.bind(this, view);
        mTBuilder.setTag(position);

        Grammar grammar = grammarList.get(position);
        mGrammarTranslate.setText(grammar.getTranslate());
        String[] arr = grammar.getSentence().split(" ");
        for (int i=0; i<arr.length; i++) {
            final Button button = new Button(container.getContext());
            //button.setId(i);
            button.setText(arr[i]);
            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mPartsLayout.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("AAA", "onClick: "+button.getText());
                    TextView viewById = mTBuilder.findViewWithTag(0);
                    mPartsLayout.removeView(button);
                    viewById.append(" " + button.getText());
                }
            });

        }

        mViewPager = (ViewPager)container;
        mViewPager.addView(view);

        return view;
    }

    public void updateData(List<Grammar> newGrammarList){
        grammarList.clear();
        grammarList.addAll(newGrammarList);
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        grammarList.clear();
        super.destroyItem(container, position, object);
    }
}
