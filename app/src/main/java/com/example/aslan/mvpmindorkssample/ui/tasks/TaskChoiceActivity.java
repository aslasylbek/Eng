package com.example.aslan.mvpmindorkssample.ui.tasks;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.aslan.mvpmindorkssample.MvpApp;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.ui.grammar.GrammarActivity;
import com.example.aslan.mvpmindorkssample.ui.main.expandable.LessonChildItem;
import com.example.aslan.mvpmindorkssample.ui.base.BaseActivity;
import com.example.aslan.mvpmindorkssample.ui.reading.ReaderActivity;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.VocabularyActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TaskChoiceActivity extends BaseActivity implements ViewPagerAdapter.ChoiceClickListener,
        ViewPager.OnPageChangeListener, TaskChoiceMvpView {

    private static final String TAG = "TaskChoiceActivity";
    private static final String EXTRA = "topicId";
    public static final String IMAGE = "mTopicPhoto";
    private static final int MAX_PAGES = 10;
    private int cnt = 1;
    private String topicId;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.mChoiceLayout)
    ConstraintLayout mRootLayout;

    @BindView(R.id.mChoiceViewPager)
    CustomViewPager mViewPager;

    private ViewPagerAdapter adapter;
    private TaskChoicePresenter presenter;
    private float mLastPositionOffset = 0f;


    public static void navigate(@NonNull AppCompatActivity activity, @NonNull View transitionImage, @NonNull LessonChildItem topicItem) {
        Intent intent = new Intent(activity, TaskChoiceActivity.class);
        intent.putExtra(EXTRA, topicItem);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    protected void init(@Nullable Bundle state) {
        prepareWindowForAnimation();
        setSupportActionBar(mToolbar);
        ViewCompat.setTransitionName(findViewById(R.id.mChoiceViewPager), IMAGE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        adapter = new ViewPagerAdapter( this);
        mViewPager.addOnPageChangeListener(this);
        LessonChildItem lessonChildItem = getIntent().getParcelableExtra(EXTRA);
        topicId = lessonChildItem.getTopicId();
        mViewPager.setMaxPages(MAX_PAGES);
        mViewPager.setBackgroundAsset(lessonChildItem.getTopicPhoto());
        mViewPager.setAdapter(adapter);
        String title = lessonChildItem.getTopicName();
        getSupportActionBar().setTitle(title);
        mToolbar.post(new Runnable() {
            @Override
            public void run() {
                getSupportActionBar().setSubtitle("My family");
            }
        });

        DataManager manager = ((MvpApp)getApplication()).getDataManager();
        presenter = new TaskChoicePresenter(manager);
        presenter.attachView(this);
        setViewPagerData(lessonChildItem);
        //mViewPager.setPageTransformer(false, new FadePageTransformer());
    }

    protected int getContentResource() {
        return R.layout.activity_task_choice;
    }

    private void prepareWindowForAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBlackAlpha));
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    @Override
    public void setViewPagerData(LessonChildItem lessonChildItem) {
        List<ChoiceItemTest> choiceItemTests = new ArrayList<>();
        if (lessonChildItem.getTopicName().equals("Vocabulary")){
            for (int i = 1; i<=4; i++){
                choiceItemTests.add(new ChoiceItemTest(i, "Exercise"+i, R.drawable.ic_menu_gallery, "Train your vocabulary using brainstorm", 1));
            }
        }
        else if (lessonChildItem.getTopicName().equals("Reading")){
            choiceItemTests.add(new ChoiceItemTest(1, "Reading "+1, R.drawable.ic_menu_gallery, "Read the text and try to understand", 2));
        }
        else if (lessonChildItem.getTopicName().equals("Listening")){
            choiceItemTests.add(new ChoiceItemTest(1, "Exercise"+1, R.drawable.ic_menu_gallery, "Listen man", 3));

        }
        else if (lessonChildItem.getTopicName().equals("Grammar")){
            choiceItemTests.add(new ChoiceItemTest(1, "Exercise"+1, R.drawable.ic_menu_gallery, "Train your grammar to become an advanced", 4));

        }
        mViewPager.setOffscreenPageLimit(choiceItemTests.size());
        adapter.setNewData(choiceItemTests);
    }

    @Override
    public void onViewPageClicked(ChoiceItemTest choiceItemTest) {
        Intent intent;
        switch (choiceItemTest.getSection()){
            case 1:
                intent = VocabularyActivity.getVocabularyIntent(this);
                intent.putExtra("position", choiceItemTest.getId());
                intent.putExtra("topicId", topicId);
                startActivity(intent);
                break;
            case 2:
                intent = ReaderActivity.getReaderIntent(this);
                intent.putExtra("position", choiceItemTest.getId());
                intent.putExtra("topicId", topicId);
                startActivity(intent);
                break;
            case 3:
                Log.d(TAG, "onViewPageClicked: "+3);
                Log.d(TAG, "onViewPageClicked: "+2);
                intent = VocabularyActivity.getVocabularyIntent(this);
                intent.putExtra("position", choiceItemTest.getId());
                startActivity(intent);
                break;
            case 4:
                intent = GrammarActivity.getGrammarActivity(this);
                intent.putExtra("position", choiceItemTest.getId());
                intent.putExtra("topicId", topicId);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (cnt==1){
            onPageSelected(position);
            cnt++;
        }
        if(positionOffset < mLastPositionOffset && positionOffset < 0.9) {
            mViewPager.setCurrentItem(position);
        } else if(positionOffset > mLastPositionOffset && positionOffset > 0.1) {
            mViewPager.setCurrentItem(position+1);
        }
        mLastPositionOffset = positionOffset;
    }

    @Override
    public void onPageSelected(int position) {
        if (mViewPager.getChildAt(position + 1) != null) {
            Group mGroupNext = (mViewPager.getChildAt(position+1)).findViewById(R.id.group);
            mGroupNext.setVisibility(View.GONE);
        }
        Group mGroupCurrent = (mViewPager.getChildAt(position)).findViewById(R.id.group);
        mGroupCurrent.setVisibility(View.VISIBLE);

        if (mViewPager.getChildAt(position - 1) != null) {
            Group mGroupPrev = (mViewPager.getChildAt(position-1)).findViewById(R.id.group);
            mGroupPrev.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //Do nothing
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter!=null){
            adapter = null;
        }
        if (mViewPager!=null)
            mViewPager.setAdapter(null);
        presenter.detachView();
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
}
