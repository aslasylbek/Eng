package com.uibenglish.aslan.mvpmindorkssample.ui.vocabulary.correctchoice;


import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
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
import android.widget.Toast;

import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.audio.AudioSyntethis;
import com.uibenglish.aslan.mvpmindorkssample.audio.OnAudioTTSCompleteListener;
import com.uibenglish.aslan.mvpmindorkssample.playbutton.PlayPauseView;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Listening;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Word;
import com.uibenglish.aslan.mvpmindorkssample.ui.FragmentsListener;
import com.uibenglish.aslan.mvpmindorkssample.ui.reading.ReaderFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuildWordFragment extends Fragment implements  OnAudioTTSCompleteListener {

    private static final String TAG = "BuildWordFragment";
    private static final String TRIGGED = "trigged";
    private static final String FAKE_LIST = "fakeList";
    private static final String WORD_DATA = "wordData";

    private boolean isViewShown = false;
    private boolean isViewToUser = false;

    private AudioSyntethis audioSyntethis;

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

    public static BuildWordFragment newInstance(int index, Word word, List<String> fakeList) {
        Bundle args = new Bundle();
        BuildWordFragment fragment = new BuildWordFragment();
        args.putInt(TRIGGED, index);
        args.putParcelable(WORD_DATA, word);
        args.putStringArrayList(FAKE_LIST, new ArrayList<String>(fakeList));
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_build_word, container, false);

        listener = (FragmentsListener)getActivity();
        ButterKnife.bind(this, view);



        buttons[0] = view.findViewById(R.id.btn1);
        buttons[1] = view.findViewById(R.id.btn2);
        buttons[2] = view.findViewById(R.id.btn3);
        buttons[3] = view.findViewById(R.id.btn4);

        fakeList = new ArrayList<>();
        if (getArguments()!=null){
            st = getArguments().getParcelable(WORD_DATA);
            int trigger = getArguments().getInt(TRIGGED);
            if (trigger==4){
                rebuildView();
            }
            else if (trigger==3){
                rebuildViewForMultipleChoice();
                fakeList.addAll(getArguments().getStringArrayList(FAKE_LIST));
                setButtonsData();
            }
            else {
                fakeList.addAll(getArguments().getStringArrayList(FAKE_LIST));
                setButtonsData();
            }
        }

        audioSyntethis = new AudioSyntethis(getActivity(), this);
        audioSyntethis.setText(st.getWord());

        mButtonNext.setVisibility(View.INVISIBLE);
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioSyntethis.destroyAudioPlayer();
                listener.sendData(isCorrect, st.getId());
            }
        });


        return view;
    }

 /*   @Override
    public void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        isViewShown = true;

                        if (isViewToUser){
                            playAudio();
                        }
                    }
                });
            }
        }).start();
    }*/

    public void rebuildView(){
        for (int i=0; i<4; i++){
            buttons[i].setVisibility(View.GONE);
        }
        editText = new EditText(getActivity());
        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setHint("Word");
        editText.setMaxLines(1);
        editText.setGravity(Gravity.CENTER_HORIZONTAL);
        editText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editText.setImeActionLabel("Check", KeyEvent.KEYCODE_ENTER);
        editText.setImeActionLabel("Check", EditorInfo.IME_ACTION_DONE);
        //editText.setOnKeyListener(this);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /***
                 * Make changes
                 */
                    String convertToLower = editText.getText().toString().toLowerCase();
                    String trimText = convertToLower.trim();

                    if (st.getWord().equals(trimText)) {
                        mButtonNext.setVisibility(View.VISIBLE);
                        editText.setFocusable(false);
                        editText.setBackgroundColor(getResources().getColor(R.color.colorCorrect));
                        isCorrect = 1;
                    }
                    else if (trimText.length()!=0){
                        editText.setError("Incorrect");
                        mButtonNext.setVisibility(View.VISIBLE);
                        editText.setBackgroundColor(getResources().getColor(R.color.colorIncorrect));
                        editText.setFocusable(false);
                    }

                return false;
            }
        });

        linearLayout.addView(editText);
    }

    public void rebuildViewForMultipleChoice(){
        mPlayAudio.setVisibility(View.GONE);
        TextView mTextViewWord = new TextView(getActivity());
        mTextViewWord.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mTextViewWord.setText(st.getWord());
        mTextViewWord.setTextSize(20);
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

/*
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        isViewToUser = menuVisible;
        if (isViewToUser && isViewShown){
            playAudio();
        }
    }*/

    public void playAudio(){
        mPlayAudio.toggle();
        audioSyntethis.playSyntethMedia();


    }

    @Override
    public void onAudioStart() {

    }

    @Override
    public void onAudioDone() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getActivity().runOnUiThread(runn1);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

            }
        }).start();
    }

    Runnable runn1 = new Runnable() {
        @Override
        public void run() {
            mPlayAudio.toggle();
        }
    };


    @Override
    public void onAudioError() {

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

    @Override
    public void onDestroyView() {
        if (listener!=null){
            listener = null;
        }
        if (audioSyntethis!=null){
            audioSyntethis.destroyAudioPlayer();
        }
        super.onDestroyView();
    }
}
