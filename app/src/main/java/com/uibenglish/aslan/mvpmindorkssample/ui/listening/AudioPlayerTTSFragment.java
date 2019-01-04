package com.uibenglish.aslan.mvpmindorkssample.ui.listening;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.audio.AudioPlayService;
import com.uibenglish.aslan.mvpmindorkssample.audio.AudioSyntethis;
import com.uibenglish.aslan.mvpmindorkssample.audio.OnAudioTTSCompleteListener;
import com.uibenglish.aslan.mvpmindorkssample.utils.DateTimeUtils;

import static android.content.Context.BIND_AUTO_CREATE;

public class AudioPlayerTTSFragment extends Fragment implements
        View.OnClickListener, OnAudioTTSCompleteListener {


    private final static int REFRESH_TIME_INTERVAL = 500;
    private static final String SERVICE_STATE_KEY = "service_state";
    private static final String AUDIO_HREF = "audioFileMp3";




    private AudioSyntethis audioSyntethis;
    private ImageView mPlayButton;
    private SeekBar mAudioSeekBar;
    private ProgressBar mAudioLoading;
    private TextView mCurrentTimeText;
    private TextView mDurationTimeText;
    private ImageView mForwardButton;
    private ImageView mReplayButton;
    private ImageView mLoopButton;
    private Handler mPlayerHandler = new Handler();

    public AudioPlayerTTSFragment() {

    }

    private void viewBind(View view) {
        mPlayButton = (ImageView) view.findViewById(R.id.iv_play_pause);
        mPlayButton.setOnClickListener(this);
        mAudioSeekBar = (SeekBar) view.findViewById(R.id.sb_play_bar);
        //mAudioSeekBar.setOnSeekBarChangeListener(this);
        mAudioSeekBar.setEnabled(false);
        mAudioLoading = (ProgressBar) view.findViewById(R.id.pb_audio_load);
        mForwardButton = (ImageView) view.findViewById(R.id.iv_forward);
        mForwardButton.setOnClickListener(this);
        mReplayButton = (ImageView) view.findViewById(R.id.iv_replay);
        mReplayButton.setOnClickListener(this);
        mCurrentTimeText = (TextView) view.findViewById(R.id.tv_current);
        mDurationTimeText = (TextView) view.findViewById(R.id.tv_duration);
        mLoopButton = view.findViewById(R.id.iv_loop);
        mLoopButton.setOnClickListener(this);
        mReplayButton.setVisibility(View.INVISIBLE);
        mForwardButton.setVisibility(View.INVISIBLE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_audio_player, container, false);
        viewBind(view);
        /***
         * Call from other part of project in loader
         */
        return view;
    }

    public void prepareAudioService(String audioString, Uri uriWithTimeStamp) {
        //Check is service is active
            audioSyntethis = new AudioSyntethis(getActivity(), this);
            audioSyntethis.setText(audioString);

            mAudioLoading.setVisibility(View.INVISIBLE);
            mPlayButton.setVisibility(View.VISIBLE);

    }

    @Override
    public void onResume() {
        super.onResume();

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
            mPlayButton.setImageResource(R.drawable.ic_play_arrow);
        }
    };

    @Override
    public void onAudioError() {

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        int newPosition;
        switch (id) {
            case R.id.iv_play_pause:
                mPlayButton.setImageResource(!audioSyntethis.isPlaying()? R.drawable.ic_replay_5 : R.drawable.ic_play_arrow);
                if (audioSyntethis.isPlaying()){
                    audioSyntethis.stop();
                }
                else audioSyntethis.playSyntethMedia();


                break;
            case R.id.iv_forward:
                // 5 seconds
//                newPosition = mAudioService.getCurrentPosition() + 5000;
//                mAudioService.controlSeekPosition(newPosition);
                break;
            case R.id.iv_replay:
                // 5 seconds
//                newPosition = mAudioService.getCurrentPosition() - 5000;
//                mAudioService.controlSeekPosition(newPosition);
                break;
            case R.id.iv_loop:
                audioSyntethis.setIsLoop(!mLoopButton.isActivated());
                mLoopButton.setActivated(audioSyntethis.getIsLoop());
                break;
        }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (audioSyntethis !=null)
            audioSyntethis.destroyAudioPlayer();

    }
}
