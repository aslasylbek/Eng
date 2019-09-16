package com.uibenglish.aslan.mvpmindorkssample.ui.listening;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.uibenglish.aslan.mvpmindorkssample.MvpApp;
import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.ui.FinishFragment;
import com.uibenglish.aslan.mvpmindorkssample.ui.FragmentsListener;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BaseActivity;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Listening;
import com.uibenglish.aslan.mvpmindorkssample.ui.reading.ReaderActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListeningActivity extends BaseActivity implements ListeningContract.ListeningMvpView, FragmentsListener {

    private static final String TAG = "ListeningActivity";
    private static final String AUDIO_PLAYER_FRAGMENT_TAG = "audioPlayerFragment";
    private static final String AUDIO_FILE_SOURCE = "http://de.uib.kz/post/audio.php?topic_id=";

    @BindView(R.id.audio_player_container)
    FrameLayout mAudioContainer;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private ListeningPresenter listeningPresenter;
    private String topicId;
    private int position;

    public static Intent getListeningIntent(Context context){
        return new Intent(context, ListeningActivity.class);
    }
    @Override
    protected void init(@Nullable Bundle state) {
        mToolbar.setTitle(getString(R.string.item_listening));
        setSupportActionBar(mToolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        topicId = getIntent().getStringExtra("topicId");
        position = getIntent().getIntExtra("position", 0);

        if (getSupportFragmentManager().findFragmentByTag(AUDIO_PLAYER_FRAGMENT_TAG) == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.audio_player_container,
                    new AudioPlayerTTSFragment(), AUDIO_PLAYER_FRAGMENT_TAG).commit();
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
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.listeningTaskContainer, ListeningTasksFragment.newInstance(collection, topicId));
        ft.commit();
        /***
         * Refactor Uri
         */
        Uri mUriWithTimeStamp = Uri.parse("content://com.uibenglish.aslan.mvpmindorkssample/bbc/1534356000000/category/6Minute"+topicId);
        AudioPlayerTTSFragment audioPlayerFragment = (AudioPlayerTTSFragment) getSupportFragmentManager().findFragmentByTag(AUDIO_PLAYER_FRAGMENT_TAG);
        audioPlayerFragment.prepareAudioService(collection.get(0).getListening(), mUriWithTimeStamp);
    }

    @Override
    public void sendData(int isCorrect, String wordId) {
        scrollFlags();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.listeningTaskContainer, FinishFragment.newInstance(isCorrect));
        ft.commit();
        //listeningPresenter.setListeningResult(topicId, isCorrect, startTime);
    }

    private void scrollFlags(){
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams)mToolbar.getLayoutParams();
        params.setScrollFlags(0);
    }

    @Override
    public void addFinishFragment(int result) {

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
        if (listeningPresenter.isAttached())
            listeningPresenter.detachView();
        super.onDestroy();
    }
}
