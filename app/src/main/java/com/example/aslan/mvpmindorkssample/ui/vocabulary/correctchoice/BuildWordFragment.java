package com.example.aslan.mvpmindorkssample.ui.vocabulary.correctchoice;


import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.playbutton.PlayPauseView;
import com.example.aslan.mvpmindorkssample.ui.main.content.Word;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.FragmentsListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuildWordFragment extends Fragment implements View.OnKeyListener{

    private static final String TAG = "BuildWordFragment";


    private boolean isViewShown = false;
    private boolean isViewToUser = false;
    private MediaPlayer mediaPlayer;

    @BindView(R.id.btnNext)
    Button mButtonNext;

    @BindView(R.id.btnPlayAudio)
    PlayPauseView mPlayAudio;

    @BindView(R.id.llChoice)
    LinearLayout linearLayout;

    @BindView(R.id.frameAudioButton)
    FrameLayout mAudioFrame;

    Button buttons[] = new Button[4];
    private EditText editText;

    private FragmentsListener listener;
    private Word st;
    private ArrayList<String> fakeList;

    private int indexCorrect;

    private int isCorrect = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_build_word, container, false);

        listener = (FragmentsListener)getActivity();
        ButterKnife.bind(this, view);

        mediaPlayer = new MediaPlayer();
        buttons[0] = view.findViewById(R.id.btn1);
        buttons[1] = view.findViewById(R.id.btn2);
        buttons[2] = view.findViewById(R.id.btn3);
        buttons[3] = view.findViewById(R.id.btn4);

        fakeList = new ArrayList<>();
        if (getArguments()!=null){
            st = getArguments().getParcelable("ed");
            int trigger = getArguments().getInt("trigger");
            if (trigger==3){
                rebuildView();
            }
            else if (trigger==4){
                rebuildViewForMultipleChoice();
                fakeList.addAll(getArguments().getStringArrayList("fk"));
                setButtonsData();
            }
            else {
                fakeList.addAll(getArguments().getStringArrayList("fk"));
                setButtonsData();
            }
        }
        mButtonNext.setVisibility(View.INVISIBLE);
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.release();
                mediaPlayer = null;
                listener.sendData(isCorrect, st.getId());
            }
        });

        isViewShown = true;

        if (isViewToUser){
            playAudio();
        }
        return view;
    }

    public void rebuildView(){
        for (int i=0; i<4; i++){
            buttons[i].setVisibility(View.GONE);
        }
        editText = new EditText(getActivity());
        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setHint("Word");
        editText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editText.setImeActionLabel("Check", KeyEvent.KEYCODE_ENTER);
        editText.setOnKeyListener(this);
        linearLayout.addView(editText);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.d(TAG, "onKey: "+keyCode+ "   "+event.getKeyCode()+"  "+editText.getImeOptions()+" "+editText.getImeActionId());
        if (keyCode == EditorInfo.IME_ACTION_DONE||event.getKeyCode()==KeyEvent.KEYCODE_ENTER||event.getKeyCode()==KeyEvent.ACTION_DOWN){
            closeKeyboard();
            if (editText.getText().toString().matches(""))
                return true;
            else if (st.getWord().equals(editText.getText().toString())) {
                mButtonNext.setVisibility(View.VISIBLE);
                editText.setFocusable(false);
                isCorrect = 1;
            }
            else {
                editText.setError("Incorrect");
                mButtonNext.setVisibility(View.VISIBLE);
                editText.setFocusable(false);
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

    public void rebuildViewForMultipleChoice(){
        mPlayAudio.setVisibility(View.GONE);
        TextView mTextViewWord = new TextView(getActivity());
        mTextViewWord.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mTextViewWord.setText(st.getWord());
        mAudioFrame.addView(mTextViewWord);
    }


    @OnClick(R.id.btnPlayAudio)
    public void onButtonPlayClick(){
        playAudio();
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4})
    public void onChoiceClicked(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                checkIsCorrectAnswer(0);
                break;
            case R.id.btn2:
                checkIsCorrectAnswer(1);
                break;
            case R.id.btn3:
                checkIsCorrectAnswer(2);
                break;
            case R.id.btn4:
                checkIsCorrectAnswer(3);
                break;
        }
        buttons[0].setClickable(false);
        buttons[1].setClickable(false);
        buttons[2].setClickable(false);
        buttons[3].setClickable(false);
        mButtonNext.setVisibility(View.VISIBLE);
    }

    public void checkIsCorrectAnswer(int choice){
        if (buttons[choice].getText().equals(st.getWord())||
                buttons[choice].getText().equals(st.getTranslateWord())) {
            buttons[choice].setBackgroundColor(Color.GREEN);
            isCorrect = 1;
        }
        else {
            buttons[choice].setBackgroundColor(Color.RED);
            buttons[indexCorrect].setBackgroundColor(Color.GREEN);
        }
    }


    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        isViewToUser = menuVisible;
        if (isViewToUser && isViewShown){
            playAudio();
        }
    }

    public void playAudio(){
        mPlayAudio.toggle();
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(st.getSoundUrl());
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mPlayAudio.toggle();
                }
            });
        } catch (IOException e) {
            Log.e("AA", "prepare() failed");
        }
    }

    public void setButtonsData(){
        Collections.shuffle(fakeList);

        if (fakeList.contains(st.getWord())){
            indexCorrect = fakeList.indexOf(st.getWord());
        }
        else if (fakeList.contains(st.getTranslateWord()))
            indexCorrect = fakeList.indexOf(st.getTranslateWord());
        //fakeList.add(st.getWordForms().get(0).getWord());
        for (int i=0; i<fakeList.size(); i++){
            buttons[i].setText(fakeList.get(i));
        }
    }
}
