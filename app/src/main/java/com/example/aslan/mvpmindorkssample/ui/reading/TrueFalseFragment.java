package com.example.aslan.mvpmindorkssample.ui.reading;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.ui.main.definition.DefinitionFragment;
import com.example.aslan.mvpmindorkssample.ui.FragmentsListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrueFalseFragment extends Fragment implements View.OnClickListener, View.OnKeyListener {


    private static final String TAG = "TrueFalseFragment";
    private static final String ARTICLE = "article";
    private static final String ANSWER = "answer";

    private FragmentsListener listener;

    @BindView(R.id.tvQuestion)
    TextView mQuestionText;

    @BindView(R.id.llTrueFalse)
    LinearLayout mLinearTrueFalse;

    @BindView(R.id.btnTrue)
    Button mBtnTrue;

    @BindView(R.id.btnFalse)
    Button mBtnFalse;

    @BindView(R.id.btnNext)
    Button mBtnNext;

    private EditText mEditAnswer;

    String answer = "";

    private int isCorrect = 0;
    private String questionType = "tf";

    public static TrueFalseFragment newInstance(String str, String ans) {
        Bundle args = new Bundle();
        TrueFalseFragment fragment = new TrueFalseFragment();
        args.putString(ARTICLE, str);
        args.putString(ANSWER, ans);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_true_false, container, false);
        ButterKnife.bind(this, view);

        listener = (FragmentsListener) getActivity();
        mBtnFalse.setOnClickListener(this);
        mBtnTrue.setOnClickListener(this);
        mBtnNext.setVisibility(View.INVISIBLE);
        String text = "";
        if (getArguments()!=null) {
             text = getArguments().getString(ARTICLE);
             answer = getArguments().getString(ANSWER);
        }
        setView(answer);
        setTextFromDb(text);

        return view;
    }


    public void setView(String answer){
        if (answer.equals("0")){
            mBtnFalse.setTag(answer);
        }
        else if(answer.equals("1")){
            mBtnFalse.setTag(answer);
        }
        else {
            questionType = "qa";
            mBtnFalse.setVisibility(View.GONE);
            mBtnTrue.setVisibility(View.GONE);
            mEditAnswer = new EditText(getActivity());
            mEditAnswer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mEditAnswer.setHint("Answer");
            mEditAnswer.setSingleLine();
            mEditAnswer.setGravity(1);
            mEditAnswer.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            mEditAnswer.setImeActionLabel("Check", KeyEvent.KEYCODE_ENTER);
            mEditAnswer.setOnKeyListener(this);
            mLinearTrueFalse.addView(mEditAnswer);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == EditorInfo.IME_ACTION_DONE||event.getKeyCode()==KeyEvent.KEYCODE_ENTER||event.getKeyCode()==KeyEvent.ACTION_DOWN){
            closeKeyboard();
            String convertToLower = mEditAnswer.getText().toString().toLowerCase();
            String trimText = convertToLower.trim();

            if (answer.equals(trimText)) {
                mBtnNext.setVisibility(View.VISIBLE);
                mEditAnswer.setFocusable(false);
                isCorrect = 1;
            }
            else if(trimText.length()!=0){
                mEditAnswer.setError("Incorrect");
                mBtnNext.setVisibility(View.VISIBLE);
                mEditAnswer.setFocusable(false);
            }
            return true;
        }
        return false;
    }

    private void closeKeyboard(){
        View view = getActivity().getCurrentFocus();
        if (view!=null){
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFalse:
                String tagFalse = (String)mBtnFalse.getTag();
                if (tagFalse!=null) {
                    mBtnFalse.setBackgroundColor(Color.GREEN);
                    isCorrect = 1;
                }
                else{
                    mBtnFalse.setBackgroundColor(Color.RED);
                    mBtnTrue.setBackgroundColor(Color.GREEN);
                }
                break;
            case R.id.btnTrue:
                String tagTrue = (String)mBtnTrue.getTag();
                if (tagTrue!=null) {
                    mBtnTrue.setBackgroundColor(Color.GREEN);
                    isCorrect = 1;
                }
                else{
                    mBtnFalse.setBackgroundColor(Color.GREEN);
                    mBtnTrue.setBackgroundColor(Color.RED);
                }
                break;
        }
        mBtnNext.setVisibility(View.VISIBLE);
        mBtnTrue.setClickable(false);
        mBtnFalse.setClickable(false);
    }

    @OnClick(R.id.btnNext)
    public void onNextClicked(){
        listener.sendData(isCorrect, questionType);
    }

    public void setTextFromDb(String textFromDb) {
        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/GoogleSans-Medium.ttf");
        mQuestionText.setTypeface(typeface);
        mQuestionText.setText(textFromDb);
        mQuestionText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                actionMode.getMenuInflater().inflate(R.menu.select_text_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                menu.removeItem(android.R.id.selectAll);
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                String word;
                actionMode.invalidate();
                switch (menuItem.getItemId()) {
                    case R.id.menu_word_book:
                        word = getSelectedText();
                        if (word != null && word.length() > 0 && word.length() < 20) {
                            //presenter.addToDictionary(word);
                        }
                        actionMode.finish();
                        break;
                    case R.id.menu_definition:
                        word = getSelectedText();
                        DefinitionFragment.newInstance(word).show(getActivity().getSupportFragmentManager(), "Definition Fragment");
                        actionMode.finish();
                        break;
                    default:

                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });
    }

    private String getSelectedText() {
        String selectedText = "";
        if (mQuestionText.isFocused()) {
            final int textStartIndex = mQuestionText.getSelectionStart();
            final int textEndIndex = mQuestionText.getSelectionEnd();

            int min = Math.max(0, Math.min(textStartIndex, textEndIndex));
            int max = Math.max(0, Math.max(textStartIndex, textEndIndex));
            selectedText = mQuestionText.getText().subSequence(min, max).toString().trim();
        }
        return selectedText;
    }

}
