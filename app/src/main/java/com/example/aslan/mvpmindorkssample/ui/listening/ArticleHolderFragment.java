package com.example.aslan.mvpmindorkssample.ui.listening;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.ui.main.definition.DefinitionFragment;
import com.example.aslan.mvpmindorkssample.widget.CustomTextView;
import com.example.aslan.mvpmindorkssample.widget.CustomTextViewListener;

import java.util.ArrayList;

/**
 * Created by Paranoid on 17/7/31.
 */

public class ArticleHolderFragment extends Fragment {

    private static final String ARTICLE_KEY = "article_text";

    private static final String TAG = "ArticleHolderFragment";

    private CustomTextView mArticleText;

    public static ArticleHolderFragment newInstance(ArrayList<String> str) {
        Bundle args = new Bundle();
        ArticleHolderFragment fragment = new ArticleHolderFragment();
        args.putStringArrayList(ARTICLE_KEY, str);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article, container, false);
        mArticleText = rootView.findViewById(R.id.tv_article);
        ArrayList<String> str = getArguments().getStringArrayList(ARTICLE_KEY);

        ArrayList<String> newArray = prepareSentence(str);
        String text = "";
        for (int i=0; i<newArray.size(); i = i+2){
            text = text.concat(newArray.get(i)+"\n\n");
        }
        //mArticleText.setText(text);


        /*SpannableString ss = new SpannableString("Your string value");
        ClickableSpan clickableTerms = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                // show toast here
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);

            }
        };
        ss.setSpan(clickableTerms, 4, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mArticleText.setText(ss, TextView.BufferType.EDITABLE);
        mArticleText.setMovementMethod(LinkMovementMethod.getInstance());
        mArticleText.setHighlightColor(Color.TRANSPARENT);*/

        String s = "Hello ___ my name is";

        String sample = "_________";


        mArticleText.setText(Html.fromHtml(s));
        mArticleText.setEventListener(new CustomTextViewListener() {
            @Override
            public void addToWordBook(String word) {
                Log.d(TAG, "addToWordBook: ");
            }

            @Override
            public void showDefinitionFragment(String word) {
                Log.d(TAG, "showDefinitionFragment: ");
                DefinitionFragment.newInstance(word).show(getFragmentManager(), "Definition Fragment");
            }
        });

        /*mArticleText.setMovementMethod(LinkMovementMethod.getInstance());
        mArticleText.setLinkTextColor(Color.BLACK);
        mArticleText.setText(definition, TextView.BufferType.EDITABLE);
        Spannable spans = (Spannable) mArticleText.getText();
        BreakIterator iterator = BreakIterator.getWordInstance(Locale.US);
        iterator.setText(definition);
        int start = iterator.first();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator
                .next()) {
            String possibleWord = definition.substring(start, end);
            if (possibleWord.charAt(0)=='_') {
                ClickableSpan clickSpan = getClickableSpan(possibleWord);
                spans.setSpan(clickSpan, start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }*/

        return rootView;
    }

    private ClickableSpan getClickableSpan(final String word) {
        return new ClickableSpan() {
            final String mWord;
            {
                mWord = word;
            }

            @Override
            public void onClick(View widget) {
                Log.d("", "onClick: " + mWord);
            }

            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);

            }
        };
    }

    private ArrayList<String> prepareSentence(ArrayList<String> stringArrayList){


        for (int i=0; i<stringArrayList.size(); i=i+2){
            String[] parts = stringArrayList.get(i).split(" ");
            String modifiedString = "";
            for (int j=0; j<parts.length; j++){
                if (parts[j].matches(stringArrayList.get(i+1))){
                    parts[j] = "___________";
                }
                modifiedString = modifiedString.concat(parts[j]+" ");
            }
            stringArrayList.set(i, modifiedString);
        }
        return stringArrayList;


    }
}
