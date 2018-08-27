package com.example.aslan.mvpmindorkssample.ui.vocabulary.remember;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.example.aslan.mvpmindorkssample.playbutton.PlayPauseView;
import com.example.aslan.mvpmindorkssample.ui.main.content.Word;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.FragmentsListener;

import java.io.IOException;

import be.rijckaert.tim.animatedvector.FloatingMusicActionButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RememberFragment extends Fragment{

    private static final String TAG = "RememberFragment";

    @BindView(R.id.tvWord)
    TextView mTextWord;

    @BindView(R.id.btnReplay)
    PlayPauseView mBtnReplay;

    @BindView(R.id.btnNext)
    Button btnNext;

    @BindView(R.id.tvTranslate)
    TextView mTextTranslate;

    @BindView(R.id.tvTranscribe)
    TextView mTextTranscribe;

    @BindView(R.id.ivWordPhoto)
    ImageView mWordPhoto;

    private boolean isViewShown = false;
    private boolean isViewToUser = false;

    private FragmentsListener listener;

    private MediaPlayer mediaPlayer;

    private Word response;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_remember, container, false);
        if (getActivity()!=null)
            listener = (FragmentsListener)getActivity();
        mediaPlayer = new MediaPlayer();
        ButterKnife.bind(this, view);
        response = getArguments().getParcelable("ed");
        for (int i=0; i<getArguments().getStringArrayList("fk").size(); i++){
            Log.d(TAG, "onCreateView: "+response.getWord()+"   "+getArguments().getStringArrayList("fk").get(i));
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.release();
                mediaPlayer = null;
                listener.sendData(2, response.getId());
                btnNext.setClickable(false);
            }
        });
        isViewShown = true;

        if (isViewToUser){
            playAudio();
        }

        setWordData();

        return view;
    }


    public void setWordData(){
        mTextWord.setText(response.getWord());
        mTextTranscribe.setText(response.getTranscription());
        mTextTranslate.setText(response.getTranslateWord());
        Glide.with(getActivity()).load(response.getPicUrl()).into(mWordPhoto);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        isViewToUser = menuVisible;
        if (isViewToUser && isViewShown){
            playAudio();
        }
    }

    @OnClick(R.id.btnReplay)
    public void onReplay(){
        playAudio();
    }

    public void playAudio(){
        mBtnReplay.toggle();
        mBtnReplay.setClickable(false);
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(response.getSoundUrl());
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
                    mBtnReplay.toggle();
                    mBtnReplay.setClickable(true);
                }
            });
        } catch (IOException e) {
            Log.e("AA", "prepare() failed");
        }
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: ");
        super.onDetach();
    }
}
