package com.uibenglish.aslan.mvpmindorkssample.audio;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.uibenglish.aslan.mvpmindorkssample.MvpApp;
import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.utils.NotificationUtility;

import java.io.File;
import java.io.IOException;

/**
 * Create by mao 2017.7
 * <p>
 * Thanks for the guide
 * A Step by Step Guide to Building an Android Audio Player MyApp
 * By Valdio Veliu  August 19, 2016
 * https://www.sitepoint.com/a-step-by-step-guide-to-building-an-android-audio-player-app/
 */


public class AudioPlayService extends Service implements
        MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener,
        AudioManager.OnAudioFocusChangeListener, CacheListener {

    private static final String TAG = "AudioPlayService";
    private HttpProxyCacheServer mAudioProxy;

    public final IBinder mBinder = new LocalBinder();

    private static final String AUDIO_HREF = "audioFileMp3";

    private static final int AUDIO_SERVICE_NOTIFICATION_ID = R.string.audio_service_notification;

    public static final String ACTION_INITIALIZE = "com.example.aslan.mvpmindorkssample.action_initialize";
    public static final String ACTION_PLAY = "com.example.aslan.mvpmindorkssample.action_play";
    public static final String ACTION_PAUSE = "com.example.aslan.mvpmindorkssample.action_pause";
    public static final String ACTION_FORWARD = "com.example.aslan.mvpmindorkssample.action_forward";
    public static final String ACTION_REPLAY = "com.example.aslan.mvpmindorkssample.action_replay";

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private int mResumePosition = 0;
    private String mAudioHref;
    private boolean mIsPrepared;
    private Uri mUriWithTimeStamp;
    private int mCachedProgress;
    private boolean mIsInitialized = false;
    private boolean mIsLoop = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        handleIntent(intent);

        return super.onStartCommand(intent, flags, startId);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (action == null) return;
        switch (action) {
            case ACTION_INITIALIZE:
                initialize(intent);
                break;
            case ACTION_PLAY:
                resumeMedia();
                break;
            case ACTION_PAUSE:
                pauseMedia();
                break;
            case ACTION_FORWARD:
                controlSeekPosition(getCurrentPosition() + 5000);
                break;
            case ACTION_REPLAY:
                controlSeekPosition(getCurrentPosition() - 5000);
                break;
            default:
                break;
        }
    }

    private void initialize(Intent intent) {

        if (mIsInitialized) return;

        if (!requestAudioFocus()) {
            stopSelf();
        }

        mAudioHref = intent
                .getStringExtra(AUDIO_HREF);

        mUriWithTimeStamp = intent.getData();

        if (!TextUtils.isEmpty(mAudioHref)) {
            initMediaPlayer();
        }

        updateNotification(ACTION_PLAY);
        mIsInitialized = true;
    }

    private void updateNotification(String action) {
        /***
         * send notification Title and description
         */
        Notification notification =
                    NotificationUtility.buildAudioServiceNotification(this, mUriWithTimeStamp, action);
        startForeground(AUDIO_SERVICE_NOTIFICATION_ID, notification);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        mCachedProgress = percent;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        //Invoked when there has been an error during an asynchronous operation
        mIsPrepared = false;
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                break;
            case MediaPlayer.MEDIA_ERROR_IO:
                break;
            case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                break;
            case MediaPlayer.MEDIA_ERROR_MALFORMED:
                break;
            case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mIsLoop) {
            seekMedia(0);
            resumeMedia();
        } else {
            stopMedia();
            removeAudioFocus();
            stopSelf();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mIsPrepared = true;
        playMedia();
        mMediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        //Invoked when the audio focus of the system is updated.
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // resume playback
                if (mMediaPlayer == null) initMediaPlayer();
                else playMedia();
                mMediaPlayer.setVolume(1.0f, 1.0f);
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                // Lost focus for an unbounded amount of time: stop playback and release media player
                pauseMedia();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // Lost focus for a short time, but we have to stop
                // playback. We don't release the media player because playback
                // is likely to resume
                pauseMedia();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                if (mMediaPlayer.isPlaying()) mMediaPlayer.setVolume(0.1f, 0.1f);
                break;
            default:
                break;
        }
    }

    private boolean requestAudioFocus() {
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int result = mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    private boolean removeAudioFocus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
                mAudioManager.abandonAudioFocus(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerBecomingNoisyReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            stopMedia();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        // remove audio focus
        removeAudioFocus();
        unRegisterBecomingNoisyReceiver();
        mAudioProxy.unregisterCacheListener(this);
    }

    @Override
    public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {
        if (percentsAvailable == 100) {
            Toast.makeText(this, getString(R.string.cache_complete), Toast.LENGTH_SHORT).show();
        }
    }

    //Becoming noisy
    private BroadcastReceiver becomingNoisyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //pause audio on ACTION_AUDIO_BECOMING_NOISY
            pauseMedia();
            NotificationUtility.buildAudioServiceNotification(
                    AudioPlayService.this, mUriWithTimeStamp, ACTION_PAUSE);
        }
    };

    private void registerBecomingNoisyReceiver() {
        //register after getting audio focus
        IntentFilter intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(becomingNoisyReceiver, intentFilter);
    }

    private void unRegisterBecomingNoisyReceiver() {
        unregisterReceiver(becomingNoisyReceiver);
    }

    private void checkCachedState() {
        HttpProxyCacheServer proxy = MvpApp.getProxy(this);
        if (proxy.isCached(mAudioHref)) {
            mCachedProgress = 100;
        } else {
            Toast.makeText(this, getString(R.string.cache_start), Toast.LENGTH_SHORT).show();
        }
    }

    public class LocalBinder extends Binder {
        public AudioPlayService getService() {
            return AudioPlayService.this;
        }
    }


    /***
     * Required 21 Api
     */
    private void initMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnBufferingUpdateListener(this);
        // On complete not work if set at here
        //mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);
        mMediaPlayer.reset();

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            mMediaPlayer.setAudioAttributes(
                    new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
            );
        }
        else {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }

        mAudioProxy = MvpApp.getProxy(this);
        String proxyUrl = mAudioProxy.getProxyUrl(mAudioHref);
        checkCachedState();
        mAudioProxy.registerCacheListener(this, mAudioHref);
        try {
            /**
             * todo caching not work proxyUrl
             *
             */
            mMediaPlayer.setDataSource(proxyUrl);
        } catch (IOException e) {
            e.printStackTrace();
            stopSelf();
        }
        mMediaPlayer.prepareAsync();
    }

    private void playMedia() {
        if (!mMediaPlayer.isPlaying() && requestAudioFocus()) {
            mMediaPlayer.start();
            updateNotification(ACTION_PAUSE);
        }
    }

    private void stopMedia() {
        if (mMediaPlayer == null) return;
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            removeAudioFocus();
        }
        mResumePosition = 0;
        updateNotification(ACTION_PLAY);
    }

    private void pauseMedia() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mResumePosition = mMediaPlayer.getCurrentPosition();
            updateNotification(ACTION_PLAY);
        }
    }

    private void resumeMedia() {
        if (!mMediaPlayer.isPlaying() && requestAudioFocus()) {
            mMediaPlayer.seekTo(mResumePosition);
            mMediaPlayer.start();
            updateNotification(ACTION_PAUSE);
        }
    }

    private void seekMedia(int position) {
        if (!mMediaPlayer.isPlaying()) {
            mResumePosition = position;
        }
        mMediaPlayer.seekTo(position);
    }

    public void controlPlayStatus() {
        if (mMediaPlayer.isPlaying()) {
            pauseMedia();
        } else {
            resumeMedia();
        }
    }

    public void controlSeekPosition(int position) {
        if (position >= 0 && position <= mMediaPlayer.getDuration()) {
            seekMedia(position);
        } else if (position < 0) {
            seekMedia(0);
        } else {
            seekMedia(mMediaPlayer.getDuration());
        }
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public int getDuration() {
        if (mIsPrepared) {
            return mMediaPlayer.getDuration();
        } else {
            return 0;
        }
    }

    public int getCurrentPosition() {
        if (mIsPrepared) {
            return mMediaPlayer.getCurrentPosition();
        } else {
            return -1;
        }
    }

    public void setIsLoop(boolean isLoop) {
        mIsLoop = isLoop;
    }

    public boolean getIsLoop() {
        return mIsLoop;
    }

    public boolean isPrepared() {
        return mIsPrepared;
    }

    public int getCachedProgress() {
        return mCachedProgress * getDuration() / 100;
    }
}
