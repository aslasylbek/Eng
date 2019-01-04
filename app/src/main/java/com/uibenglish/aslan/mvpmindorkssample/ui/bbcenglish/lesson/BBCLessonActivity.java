package com.uibenglish.aslan.mvpmindorkssample.ui.bbcenglish.lesson;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.Slide;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.uibenglish.aslan.mvpmindorkssample.MvpApp;
import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCLesson;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BaseActivity;
import com.uibenglish.aslan.mvpmindorkssample.ui.bbcenglish.BBCPresenter;
import com.uibenglish.aslan.mvpmindorkssample.ui.listening.AudioPlayerFragment;
import com.uibenglish.aslan.mvpmindorkssample.widget.BaseAdapter;
import com.uibenglish.aslan.mvpmindorkssample.widget.DividerItemDecoration;
import com.uibenglish.aslan.mvpmindorkssample.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.uibenglish.aslan.mvpmindorkssample.ui.tasks.TaskChoiceActivity.IMAGE;

public class BBCLessonActivity extends BaseActivity implements BBCLessonContract.BBCLessonMvpView, BaseAdapter.OnItemClickListener, TabLayout.OnTabSelectedListener {


    @BindView(R.id.avPlayerFrame)
    FrameLayout mAudioFrame;

    @BindView(R.id.sampleRV)
    EmptyRecyclerView mRecyclerView;

    @BindView(R.id.tabLayoutBBCTask)
    TabLayout mTabBBCTasks;

    private BBCLesson bbcLessonData;

    private static final String TAG = "BBCLessonActivity";
    private static final String AUDIO_FRAGMENT_TAG = "audioFragment";
    private static final String EXTRA = "Category";
    private static final String TITLE = "TITLE";
    private BBCLessonPresenter presenter;
    private BBCTaskAdapter adapter;

    public static void navigate(@NonNull AppCompatActivity activity, @NonNull String title, @NonNull String category_id) {
        Intent intent = new Intent(activity, BBCLessonActivity.class);
        intent.putExtra(EXTRA, category_id);
        intent.putExtra(TITLE, title);
        //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, IMAGE);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void init(@Nullable Bundle state) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getIntent().getStringExtra(TITLE));
        }

        if (getSupportFragmentManager().findFragmentByTag(AUDIO_FRAGMENT_TAG)==null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.avPlayerFrame, new  AudioPlayerFragment(), AUDIO_FRAGMENT_TAG).commit();
        }

        mTabBBCTasks.addOnTabSelectedListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        //mRecyclerView.setEmptyView(mEmptyView);

        adapter = new BBCTaskAdapter(new ArrayList());
        adapter.attachToRecyclerView(mRecyclerView);
        adapter.setOnItemClickListener(this);

        DataManager manager = ((MvpApp)getApplicationContext()).getDataManager();
        presenter = new BBCLessonPresenter(manager);
        presenter.attachView(this);
        String lessonId = getIntent().getStringExtra(EXTRA);
        presenter.requestBBCLessonDataById(lessonId);
    }

    @Override
    public void onItemClick(@NonNull Object item) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 0 && bbcLessonData.getVocabulary().size()!=0){
            adapter.changeDataSet(bbcLessonData.getVocabulary());
        }
        else if (tab.getPosition() == 1 && bbcLessonData.getTranscript().size()!=0){
            adapter.changeDataSet(bbcLessonData.getTranscript());
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        //nothing
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        //nothing
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_bbclesson;
    }

    @Override
    public void updateUI(BBCLesson bbcLesson) {
        Uri mUriWithTimeStamp = Uri.parse("content://com.uibenglish.aslan.mvpmindorkssample/bbc/1534356000000/category/6Minute");
        AudioPlayerFragment fragment = (AudioPlayerFragment) getSupportFragmentManager().findFragmentByTag(AUDIO_FRAGMENT_TAG);
        fragment.prepareAudioService(bbcLesson.getAudioUrl(), mUriWithTimeStamp);
        bbcLessonData = bbcLesson;
        if (bbcLesson.getVocabulary().size()!=0){
            adapter.changeDataSet(bbcLesson.getVocabulary());
        }
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
        presenter.detachView();
        super.onDestroy();
    }
}
