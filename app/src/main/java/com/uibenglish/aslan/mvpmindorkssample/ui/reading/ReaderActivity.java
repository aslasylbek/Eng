package com.uibenglish.aslan.mvpmindorkssample.ui.reading;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.uibenglish.aslan.mvpmindorkssample.MvpApp;
import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.ui.AddWordListener;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BaseActivity;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Reading;
import com.uibenglish.aslan.mvpmindorkssample.ui.FragmentsListener;
import com.uibenglish.aslan.mvpmindorkssample.ui.vocabulary.VocabularyAdapter;
import com.uibenglish.aslan.mvpmindorkssample.widget.VocabularyViewPager;
import com.uibenglish.aslan.mvpmindorkssample.ui.FinishFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ReaderActivity extends BaseActivity implements ReaderMvpContract.ReaderMvpView, FragmentsListener, AddWordListener, TabLayout.OnTabSelectedListener {

    private static final String TAG = "ReaderActivity";

    @BindView(R.id.viewPagerReader)
    VocabularyViewPager mArticleViewPager;

    @BindView(R.id.toolbarReading)
    Toolbar mReadingToolbar;

    @BindView(R.id.tabsReader)
    TabLayout mReadingTabLayout;

    private ReaderPresenter presenter;
    private VocabularyAdapter mArticleAdapter;
    private String topicId;
    private List<Fragment> fragmentList;
    private int item = 1;

    private int result_ans = 0;
    private int total_ans = 0;
    private int result_tf = 0;
    private int total_tf = 0;
    private long startTime = 0;


    public static Intent getReaderIntent(Context context){
        return new Intent(context, ReaderActivity.class);
    }

    @Override
    protected void init(@Nullable Bundle state) {
        mReadingToolbar.setTitle(getString(R.string.item_reading));
        setSupportActionBar(mReadingToolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mReadingTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mReadingTabLayout.addOnTabSelectedListener(this);
        topicId = getIntent().getStringExtra("topicId");
        mArticleViewPager.disableScroll(true);
        mArticleViewPager.setCurrentItem(item);
        fragmentList = new ArrayList<>();
        mArticleAdapter = new VocabularyAdapter(getSupportFragmentManager(), fragmentList);
        mArticleViewPager.setAdapter(mArticleAdapter);
        DataManager dataManager = ((MvpApp)getApplicationContext()).getDataManager();
        presenter = new ReaderPresenter(dataManager);
        presenter.attachView(this);
        presenter.requestForLocalReadingText(topicId);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if(tab.getPosition()==0){
            mArticleViewPager.setCurrentItem(0);
            setScrollFlags(true);
        }
        else if (tab.getPosition()==1){
            setScrollFlags(false);
            mArticleViewPager.setCurrentItem(item);
        }
    }

    private void setScrollFlags(boolean isActive){
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams)mReadingToolbar.getLayoutParams();
        if(isActive) {
            Log.e(TAG, "setScrollFlags: " );
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        }
        else params.setScrollFlags(0);
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
        return R.layout.activity_reader;
    }

    @Override
    public void spreadTextToFragments(List<Reading> readingList) {
        startTime = System.currentTimeMillis()/1000;
        /**
         * get Also position
         */


        Reading reading = readingList.get(0);

        if(!reading.getReading().isEmpty())
            mReadingTabLayout.addTab(mReadingTabLayout.newTab().setText("Text"));
        if(reading.getQuestionanswer().size()>0||reading.getTruefalse().size()>0)
            mReadingTabLayout.addTab(mReadingTabLayout.newTab().setText("Exercise"));
        fragmentList.add(ReaderFragment.newInstance(reading.getReading()));
        for (int i=0; i<reading.getQuestionanswer().size(); i++) {
            fragmentList.add(TrueFalseFragment.newInstance(reading.getQuestionanswer().get(i).getQuestion(),
                    reading.getQuestionanswer().get(i).getAnswer(), true));
            total_ans++;
        }

        for (int i=0; i<reading.getTruefalse().size(); i++){
            fragmentList.add(TrueFalseFragment.newInstance(reading.getTruefalse().get(i).getQuestion(),
                    reading.getTruefalse().get(i).getTruefalse(), false));
            total_tf++;
        }
        mArticleAdapter.notifyDataSetChanged();
    }

    @Override
    public void sendData(int isCorrect, String wordId) {
        item++;
        if (wordId.equals("tf")){
            result_tf +=isCorrect;
        }
        else if (wordId.equals("qa")){
            result_ans+=isCorrect;
        }

        if (item==fragmentList.size()&&fragmentList.size()>1){
            presenter.postResultToServer(topicId, result_tf, result_ans, total_tf, total_ans, startTime);
        }
        else if (fragmentList.size()==1)
            finish();

        mArticleViewPager.setCurrentItem(item);

    }

    @Override
    public void addFinishFragment(int totalResult) {
        fragmentList.add(FinishFragment.newInstance(totalResult));
        mArticleAdapter.notifyDataSetChanged();
        mArticleViewPager.setCurrentItem(item);
    }

    @Override
    public void sendToWordBook(String mWord) {
        presenter.addToDictionary(mWord);
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
    public void setTranslate(String translates) {
        /**
         * Not using
         */
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}
