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
import com.example.aslan.mvpmindorkssample.ui.vocabulary.FragmentsListener;

import java.io.IOException;

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
    Button mBtnReplay;

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

    private TranslationResponse response;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_remember, container, false);
        if (getActivity()!=null)
            listener = (FragmentsListener)getActivity();
        mediaPlayer = new MediaPlayer();
        ButterKnife.bind(this, view);
        response = getArguments().getParcelable("ed");


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.release();
                mediaPlayer = null;
                listener.sendData(1);
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
        mTextWord.setText(response.getWordForms().get(0).getWord());
        mTextTranscribe.setText(response.getTranscription());
        mTextTranslate.setText(response.getTranslate().get(0).getValue());
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
