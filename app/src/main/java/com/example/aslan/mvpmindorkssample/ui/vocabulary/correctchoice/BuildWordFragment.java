package com.example.aslan.mvpmindorkssample.ui.vocabulary.correctchoice;


import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.FakeContent;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.FragmentsListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuildWordFragment extends Fragment {

    private static final String TAG = "BuildWordFragment";


    private boolean isViewShown = false;
    private boolean isViewToUser = false;
    private MediaPlayer mediaPlayer;

    @BindView(R.id.btnNext)
    Button mButtonNext;

    @BindView(R.id.btnPlayAudio)
    Button mPlayAudio;

    @BindView(R.id.llChoice)
    LinearLayout linearLayout;

    Button buttons[] = new Button[4];

    private FragmentsListener listener;
    private TranslationResponse st;
    private ArrayList<String> fakeList;

    private int indexCorrect;


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


        st = getArguments().getParcelable("ed");
        fakeList = new ArrayList<>();
        fakeList.addAll(getArguments().getStringArrayList("fk"));

        setButtonsData();
        mButtonNext.setVisibility(View.INVISIBLE);
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.release();
                mediaPlayer = null;
                listener.sendData(1);
            }
        });

        isViewShown = true;

        if (isViewToUser){
            playAudio();
        }
        return view;
    }


    @OnClick(R.id.btnPlayAudio)
    public void onButtonPlayClick(){
        playAudio();
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4})
    public void onChoiceClicked(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                if (buttons[0].getText().equals(st.getWordForms().get(0).getWord())||
                        buttons[0].getText().equals(st.getTranslate().get(0).getValue()))
                    buttons[0].setBackgroundColor(Color.GREEN);
                else {
                    buttons[0].setBackgroundColor(Color.RED);
                    buttons[indexCorrect].setBackgroundColor(Color.GREEN);
                }
                break;


            case R.id.btn2:
                if (buttons[1].getText().equals(st.getWordForms().get(0).getWord()))
                    buttons[1].setBackgroundColor(Color.GREEN);
                else {
                    buttons[1].setBackgroundColor(Color.RED);
                    buttons[indexCorrect].setBackgroundColor(Color.GREEN);
                }
                break;
            case R.id.btn3:
                if (buttons[2].getText().equals(st.getWordForms().get(0).getWord()))
                    buttons[2].setBackgroundColor(Color.GREEN);
                else {
                    buttons[2].setBackgroundColor(Color.RED);
                    buttons[indexCorrect].setBackgroundColor(Color.GREEN);
                }
                break;
            case R.id.btn4:
                if (buttons[3].getText().equals(st.getWordForms().get(0).getWord()))
                    buttons[3].setBackgroundColor(Color.GREEN);
                else {
                    buttons[3].setBackgroundColor(Color.RED);
                    buttons[indexCorrect].setBackgroundColor(Color.GREEN);
                }
                break;
        }
        buttons[0].setClickable(false);
        buttons[1].setClickable(false);
        buttons[2].setClickable(false);
        buttons[3].setClickable(false);
        mButtonNext.setVisibility(View.VISIBLE);
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
        } catch (IOException e) {
            Log.e("AA", "prepare() failed");
        }
    }

    public void setButtonsData(){
        Collections.shuffle(fakeList);

        if (fakeList.contains(st.getWordForms().get(0).getWord())){
            indexCorrect = fakeList.indexOf(st.getWordForms().get(0).getWord());
        }
        else if (fakeList.contains(st.getTranslate().get(0).getValue()))
            indexCorrect = fakeList.indexOf(st.getTranslate().get(0).getValue());
        //fakeList.add(st.getWordForms().get(0).getWord());
        for (int i=0; i<fakeList.size(); i++){
            buttons[i].setText(fakeList.get(i));
        }
    }
}
