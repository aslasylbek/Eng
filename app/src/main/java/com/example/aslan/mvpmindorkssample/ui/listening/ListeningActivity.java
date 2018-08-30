package com.example.aslan.mvpmindorkssample.ui.listening;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.aslan.mvpmindorkssample.MvpApp;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.ui.FinishFragment;
import com.example.aslan.mvpmindorkssample.ui.FragmentsListener;
import com.example.aslan.mvpmindorkssample.ui.base.BaseActivity;
import com.example.aslan.mvpmindorkssample.ui.main.content.Listening;
import com.example.aslan.mvpmindorkssample.ui.reading.ReaderActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListeningActivity extends BaseActivity implements ListeningContract.ListeningMvpView, FragmentsListener {

    private static final String AUDIO_PLAYER_FRAGMENT_TAG = "audioPlayerFragment";
    private static final String AUDIO_FILE_SOURCE = "http://de.uib.kz/post/audio.php?topic_id=";



    @BindView(R.id.audio_player_container)
    FrameLayout mAudioContainer;

    private ListeningPresenter listeningPresenter;
    private String topicId;
    private int position;
    private long startTime;

    public static Intent getListeningIntent(Context context){
        return new Intent(context, ListeningActivity.class);
    }
    @Override
    protected void init(@Nullable Bundle state) {
        setTitle(getString(R.string.item_listening));
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        topicId = getIntent().getStringExtra("topicId");
        position = getIntent().getIntExtra("position", 0);

        if (getSupportFragmentManager().findFragmentByTag(AUDIO_PLAYER_FRAGMENT_TAG) == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.audio_player_container,
                    new AudioPlayerFragment(), AUDIO_PLAYER_FRAGMENT_TAG).commit();
        }
        DataManager manager = ((MvpApp)getApplicationContext()).getDataManager();
        listeningPresenter = new ListeningPresenter(manager);
        listeningPresenter.attachView(this);
        listeningPresenter.requestForListeningCollection(topicId);
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_listening;
    }

    @Override
    public void spreadListeningCollection(List<Listening> collection) {
        startTime = System.currentTimeMillis()/1000;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.listeningTaskContainer, ListeningTasksFragment.newInstance(collection, position));
        ft.commit();

        /***
         * Refactor Uri
         */
        Uri mUriWithTimeStamp = Uri.parse("content://com.example.aslan.mvpmindorkssample/bbc/1534356000000/category/6Minute");

        AudioPlayerFragment audioPlayerFragment = (AudioPlayerFragment)getSupportFragmentManager().findFragmentByTag(AUDIO_PLAYER_FRAGMENT_TAG);
        audioPlayerFragment.prepareAudioService(AUDIO_FILE_SOURCE+topicId, mUriWithTimeStamp);
    }

    @Override
    public void sendData(int isCorrect, String wordId) {
        listeningPresenter.setListeningResult(topicId, isCorrect, startTime);
    }

    @Override
    public void addFinishFragment(int result) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.listeningTaskContainer, FinishFragment.newInstance(result));
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        listeningPresenter.detachView();
        super.onDestroy();
    }
}
