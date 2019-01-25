package com.uibenglish.aslan.mvpmindorkssample.ui.reading;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
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

import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.definition.DefinitionFragment;
import com.uibenglish.aslan.mvpmindorkssample.ui.FragmentsListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrueFalseFragment extends Fragment implements TextView.OnEditorActionListener {


    private static final String TAG = "TrueFalseFragment";
    private static final String ARTICLE = "article";
    private static final String ANSWER = "answer";
    private static final String UI = "boolean";

    private FragmentsListener listener;
    private Unbinder mUnbinder;

    @BindView(R.id.tvQuestion)
    TextView mQuestionText;

    @BindView(R.id.llTrueFalse)
    LinearLayout mLinearTrueFalse;

    @BindView(R.id.btnTrue)
    Button mBtnTrue;

    @BindView(R.id.btnFalse)
    Button mBtnFalse;

    @BindView(R.id.btnNot)
    Button mBtnNot;

    @BindView(R.id.btnNext)
    Button mBtnNext;

    private EditText mEditAnswer;
    private Button[] mButtonsAns;
    private int isCorrect = 0;
    private String questionType = "tf";

    public static TrueFalseFragment newInstance(String str, String ans, boolean rebuildUI) {
        Bundle args = new Bundle();
        TrueFalseFragment fragment = new TrueFalseFragment();
        args.putString(ARTICLE, str);
        args.putString(ANSWER, ans);
        args.putBoolean(UI, rebuildUI);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_true_false, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        listener = (FragmentsListener) getActivity();
        mBtnNext.setVisibility(View.INVISIBLE);
        mBtnNot.setTag(0);
        mBtnFalse.setTag(0);
        mBtnTrue.setTag(0);
        String text = "";
        String answer = "";
        boolean changeUI = false;
        if (getArguments()!=null) {
             text = getArguments().getString(ARTICLE);
             answer = getArguments().getString(ANSWER);
             changeUI = getArguments().getBoolean(UI);
        }
        mButtonsAns = new Button[]{mBtnTrue, mBtnFalse, mBtnNot};
        setView(changeUI, answer);
        setTextFromDb(text);
        return view;
    }

    public void setView(boolean changeUI, String answer){
        if (changeUI){
            questionType = "qa";
            mBtnFalse.setVisibility(View.GONE);
            mBtnTrue.setVisibility(View.GONE);
            mBtnNot.setVisibility(View.GONE);
            mEditAnswer = new EditText(getActivity());
            mEditAnswer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mEditAnswer.setHint("Answer");
            mEditAnswer.setTag(answer);
            mEditAnswer.setMaxLines(1);
            mEditAnswer.setSingleLine(true);
            mEditAnswer.setGravity(1);
            mEditAnswer.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            mEditAnswer.setImeActionLabel("Check", KeyEvent.KEYCODE_ENTER);
            mEditAnswer.setImeActionLabel("Check", EditorInfo.IME_ACTION_DONE);
            mEditAnswer.setOnEditorActionListener(this);
            mLinearTrueFalse.addView(mEditAnswer);
        }
        else if(answer.equals("0")){
            mBtnFalse.setTag(1);
        }
        else if(answer.equals("1")){
            mBtnTrue.setTag(1);
        }
        else if(answer.equals("2")){
            mBtnNot.setTag(1);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        closeKeyboard();
        String convertToLower = mEditAnswer.getText().toString().toLowerCase();
        String trimText = convertToLower.trim();
        if (mEditAnswer.getTag().toString().equals(trimText)) {
            mBtnNext.setVisibility(View.VISIBLE);
            mEditAnswer.setFocusable(false);
            mEditAnswer.setBackgroundColor(getResources().getColor(R.color.colorCorrect));
            isCorrect = 1;
        }
        else if(trimText.length()!=0){
            mEditAnswer.setBackgroundColor(getResources().getColor(R.color.colorIncorrect));
            mBtnNext.setVisibility(View.VISIBLE);
            mEditAnswer.setFocusable(false);
        }
        return false;
    }

    private void closeKeyboard(){
        if (getActivity()!=null) {
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @OnClick({R.id.btnNot, R.id.btnFalse, R.id.btnTrue})
    public void onChooseAnswer(Button button){
        if (Integer.parseInt(button.getTag().toString())!=0){
            button.setBackgroundColor(Color.GREEN);
            isCorrect = 1;
        }
        else{
            button.setBackgroundColor(Color.RED);
            mLinearTrueFalse.findViewWithTag(1).setBackgroundColor(Color.GREEN);
        }
        mBtnNext.setVisibility(View.VISIBLE);
        mBtnTrue.setClickable(false);
        mBtnFalse.setClickable(false);
        mBtnNot.setClickable(false);

    }

    @OnClick(R.id.btnNext)
    public void onNextClicked(){
        listener.sendData(isCorrect, questionType);
    }

    public void setTextFromDb(String textFromDb) {
        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/GoogleSans-Medium.ttf");
        //mQuestionText.setTypeface(typeface);
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
                        DefinitionFragment.newInstance(word).show(getChildFragmentManager(), "Definition Fragment");
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

    @Override
    public void onDestroyView() {
        if (mUnbinder!=null){
            mUnbinder = null;
        }
        if (listener!=null)
            listener = null;
        super.onDestroyView();
    }
}
