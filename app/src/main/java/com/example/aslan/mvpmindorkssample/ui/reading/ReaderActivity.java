package com.example.aslan.mvpmindorkssample.ui.reading;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aslan.mvpmindorkssample.MvpApp;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.ui.base.BaseActivity;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.finish.FinishFragment;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReaderActivity extends BaseActivity implements ReaderMvpContract.ReaderMvpView {

    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.llButtons)
    LinearLayout mLlButtons;

    @BindView(R.id.btnFinishReading)
    Button mFinishReading;

    @BindView(R.id.scrollView2)
    ScrollView scrollViewText;

    private ReaderPresenter presenter;

    private String topicId;


    public static Intent getReaderIntent(Context context){
        return new Intent(context, ReaderActivity.class);
    }

    @Override
    protected void init(@Nullable Bundle state) {
        mFinishReading.setText("I understand all text");

        topicId = getIntent().getStringExtra("topicId");

        DataManager dataManager = ((MvpApp)getApplicationContext()).getDataManager();
        presenter = new ReaderPresenter(dataManager);
        presenter.attachView(this);
        presenter.requestForLocalReadingText(topicId);
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_reader;
    }

    @Override
    public void setTextFromDb(String textFromDb) {
        String definition = textFromDb.trim();
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/GoogleSans-Medium.ttf");
        textView.setTypeface(typeface);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setLinkTextColor(Color.BLACK);
        textView.setText(definition, TextView.BufferType.EDITABLE);
        Spannable spans = (Spannable) textView.getText();
        BreakIterator iterator = BreakIterator.getWordInstance(Locale.US);
        iterator.setText(definition);
        int start = iterator.first();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator
                .next()) {
            String possibleWord = definition.substring(start, end);
            if (Character.isLetterOrDigit(possibleWord.charAt(0))) {
                ClickableSpan clickSpan = getClickableSpan(possibleWord);
                spans.setSpan(clickSpan, start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private ClickableSpan getClickableSpan(final String word) {
        return new ClickableSpan() {
            final String mWord;
            {
                mWord = word;
            }

            @Override
            public void onClick(View widget) {
                mLlButtons.removeAllViews();
                presenter.getWordTranslate(mWord);
            }

            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);

            }
        };
    }

    @Override
    public void setTranslate(String[] translates) {
        for (int i=0; i<translates.length; i++){
            final Button button = new Button(this);
            button.setText(translates[i]);
            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            mLlButtons.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, button.getText()+" - was added to your dictionary", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

}

    @OnClick(R.id.btnFinishReading)
    public void finishReading(){
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}
