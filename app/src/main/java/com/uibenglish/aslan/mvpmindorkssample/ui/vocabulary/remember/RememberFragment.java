package com.uibenglish.aslan.mvpmindorkssample.ui.vocabulary.remember;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.audio.AudioSyntethis;
import com.uibenglish.aslan.mvpmindorkssample.audio.OnAudioTTSCompleteListener;
import com.uibenglish.aslan.mvpmindorkssample.playbutton.PlayPauseView;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Word;
import com.uibenglish.aslan.mvpmindorkssample.ui.FragmentsListener;
import com.uibenglish.aslan.mvpmindorkssample.ui.reading.ReaderFragment;

import java.io.IOException;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RememberFragment extends Fragment implements OnAudioTTSCompleteListener {


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


    private FragmentsListener listener;

    private AudioSyntethis audioSyntethis;
    private Word response;

    public static RememberFragment newInstance(Word word) {
        Bundle args = new Bundle();
        RememberFragment fragment = new RememberFragment();
        args.putParcelable(WORD_DATA, word);
        fragment.setArguments(args);
        return fragment;
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
            mBtnReplay.toggle();
            mBtnReplay.setClickable(true);
            btnNext.setEnabled(true);
        }
    };

    @Override
    public void onAudioError() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_remember, container, false);
        if (getActivity()!=null)
            listener = (FragmentsListener)getActivity();
        /*call = new Callable() {
            @Override
            public Object call() throws Exception {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "onAudioDone: KAKAKAKA" );
                        mBtnReplay.toggle();
                        mBtnReplay.setClickable(true);
                        btnNext.setEnabled(true);
                    }
                });
                return null;
            }
        };*/
        audioSyntethis = new AudioSyntethis(getActivity(),this);
        ButterKnife.bind(this, view);
        if (getArguments()!=null)
        response = getArguments().getParcelable(WORD_DATA);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioSyntethis.stopAudioPlayer();
                listener.sendData(2, response.getId());
            }
        });

        setWordData();
        return view;
    }


    public void setWordData(){
        mTextWord.setText(response.getWord());
        mTextTranscribe.setText(response.getTranscription());
        mTextTranslate.setText(response.getTranslateWord());
        Glide.with(this).load(response.getPicUrl()).into(mWordPhoto);
    }

    @OnClick(R.id.btnReplay)
    public void onReplay(){
        playAudio();
    }

    public void playAudio(){
        mBtnReplay.toggle();
        mBtnReplay.setClickable(false);
        audioSyntethis.setText(response.getWord());
        audioSyntethis.playSyntethMedia();
    }

    @Override
    public void onDetach() {
        if (audioSyntethis!=null) {
            Log.e(TAG, "onDetach: 1" );
            audioSyntethis.stopAudioPlayer();
        }
        listener = null;
        super.onDetach();
    }
}
