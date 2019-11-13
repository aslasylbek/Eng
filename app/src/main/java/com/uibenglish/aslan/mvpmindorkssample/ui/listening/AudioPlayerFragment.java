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
import com.uibenglish.aslan.mvpmindorkssample.utils.DateTimeUtils;

import static android.content.Context.BIND_AUTO_CREATE;

public class AudioPlayerFragment extends Fragment implements
        SeekBar.OnSeekBarChangeListener,
        View.OnClickListener{

    private static final String TAG = "AudioPlayerFragment";

    private final static int REFRESH_TIME_INTERVAL = 500;
    private static final String SERVICE_STATE_KEY = "service_state";
    private static final String AUDIO_HREF = "audioFileMp3";


    private AudioPlayService mAudioService;

    private ImageView mPlayButton;
    private SeekBar mAudioSeekBar;
    private ProgressBar mAudioLoading;
    private TextView mCurrentTimeText;
    private TextView mDurationTimeText;
    private ImageView mForwardButton;
    private ImageView mReplayButton;
    private ImageView mLoopButton;
    private boolean mBond = false;
    private Handler mPlayerHandler = new Handler();
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            updateAudioLoadingUI();
            updateSeekBarUI();
            updatePlayerControlButtonUI();
            mPlayerHandler.postDelayed(this, REFRESH_TIME_INTERVAL);
        }
    };

    public AudioPlayerFragment() {
        // Required empty public constructor
    }

    private void viewBind(View view) {
        mPlayButton = (ImageView) view.findViewById(R.id.iv_play_pause);
        mPlayButton.setOnClickListener(this);
        mAudioSeekBar = (SeekBar) view.findViewById(R.id.sb_play_bar);
        mAudioSeekBar.setOnSeekBarChangeListener(this);
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

    public void prepareAudioService(String audioHref, Uri uriWithTimeStamp) {
        //Check is service is active
        if (!mBond) {
            Intent playerIntent = new Intent(getContext(), AudioPlayService.class)
                    .setData(uriWithTimeStamp)
                    .setAction(AudioPlayService.ACTION_INITIALIZE)
                    .putExtra(AUDIO_HREF, audioHref);
            getActivity().startService(playerIntent);
            getActivity().bindService(playerIntent, mConnection, BIND_AUTO_CREATE);
        }
    }



    private void updateSeekBarUI() {
        if (mBond && mAudioService.isPrepared()) {
            mAudioSeekBar.setEnabled(true);
            mAudioSeekBar.setMax(mAudioService.getDuration());
            mAudioSeekBar.setSecondaryProgress(mAudioService.getCachedProgress());
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                mAudioSeekBar.setProgress(mAudioService.getCurrentPosition(), false);
            } else {
                mAudioSeekBar.setProgress(mAudioService.getCurrentPosition());
            }
        } else {
            mAudioSeekBar.setEnabled(false);
        }
    }

    private void updatePlayerControlButtonUI() {
        if (mBond && mAudioService.isPrepared()) {
            mPlayButton.setEnabled(true);
            mReplayButton.setEnabled(true);
            mForwardButton.setEnabled(true);
            mLoopButton.setEnabled(true);
            mPlayButton.setImageResource(mAudioService.isPlaying()? R.drawable.ic_pause : R.drawable.ic_play_arrow);
            mLoopButton.setActivated(mAudioService.getIsLoop());
        } else {
            mPlayButton.setEnabled(false);
            mReplayButton.setEnabled(false);
            mForwardButton.setEnabled(false);
            mLoopButton.setEnabled(false);
        }
    }

    private void updateAudioLoadingUI() {
        if (mBond && mAudioService.isPrepared()) {
            if (mAudioService.getCurrentPosition() - mAudioService.getCachedProgress() < 1000) {
                mAudioLoading.setVisibility(View.INVISIBLE);
                mPlayButton.setVisibility(View.VISIBLE);
            } else {
                mAudioLoading.setVisibility(View.VISIBLE);
                mPlayButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    //Binding this Client to the AudioPlayer Service
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            AudioPlayService.LocalBinder binder = (AudioPlayService.LocalBinder) service;
            mAudioService = binder.getService();
            mBond = true;
            Toast.makeText(getActivity(), "Service Bound", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBond = false;
        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int newPosition;
        switch (id) {
            case R.id.iv_play_pause:
                mAudioService.controlPlayStatus();
                break;
            case R.id.iv_forward:
                // 5 seconds
                newPosition = mAudioService.getCurrentPosition() + 5000;
                mAudioService.controlSeekPosition(newPosition);
                break;
            case R.id.iv_replay:
                // 5 seconds
                newPosition = mAudioService.getCurrentPosition() - 5000;
                mAudioService.controlSeekPosition(newPosition);
                break;
            case R.id.iv_loop:
                mAudioService.setIsLoop(!mLoopButton.isActivated());
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // Here to update the time of audio
        if (mBond) {
            mCurrentTimeText.setText(DateTimeUtils.getDisplayTime(progress));
            mDurationTimeText.setText(DateTimeUtils.getDisplayTime(mAudioService.getDuration()));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mPlayerHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mBond) {
            mAudioService.controlSeekPosition(seekBar.getProgress());
        }
        mPlayerHandler.postDelayed(mRunnable, REFRESH_TIME_INTERVAL);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPlayerHandler.postDelayed(mRunnable, REFRESH_TIME_INTERVAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPlayerHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBond) {
            getActivity().unbindService(mConnection);
            if (getActivity().isFinishing()) {
                mAudioService.stopSelf();
            }
        }
    }
}
