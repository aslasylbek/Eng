package com.uibenglish.aslan.mvpmindorkssample.ui.vocabulary.remember;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.playbutton.PlayPauseView;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Word;
import com.uibenglish.aslan.mvpmindorkssample.ui.FragmentsListener;
import com.uibenglish.aslan.mvpmindorkssample.ui.reading.ReaderFragment;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RememberFragment extends Fragment implements MediaPlayer.OnCompletionListener{


    private static final String TAG = "RememberFragment";
    private static final String WORD_DATA = "wordData";

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

    public static RememberFragment newInstance(Word word) {
        Bundle args = new Bundle();
        RememberFragment fragment = new RememberFragment();
        args.putParcelable(WORD_DATA, word);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_remember, container, false);
        if (getActivity()!=null)
            listener = (FragmentsListener)getActivity();
        mediaPlayer = new MediaPlayer();
        ButterKnife.bind(this, view);
        if (getArguments()!=null)
        response = getArguments().getParcelable(WORD_DATA);

        btnNext.setEnabled(false);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNext.setEnabled(false);
                mediaPlayer.release();
                mediaPlayer = null;
                listener.sendData(2, response.getId());
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
        Glide.with(this).load(response.getPicUrl()).into(mWordPhoto);
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
            mediaPlayer.setOnCompletionListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onCompletion(MediaPlayer mp) {
        mBtnReplay.toggle();
        mBtnReplay.setClickable(true);
        btnNext.setEnabled(true);
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: ");
        if (mediaPlayer!=null){
            mediaPlayer.release();
        }

        listener = null;
        super.onDetach();
    }
}
