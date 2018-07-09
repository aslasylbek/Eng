package com.example.aslan.mvpmindorkssample.ui.tasks;

import android.content.Intent;
import android.graphics.Bitmap;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.aslan.mvpmindorkssample.MvpApp;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.general.LoadingDialog;
import com.example.aslan.mvpmindorkssample.general.LoadingView;
import com.example.aslan.mvpmindorkssample.ui.main.expandable.LessonTopicItem;
import com.example.aslan.mvpmindorkssample.tinderCard.DashActivity;
import com.example.aslan.mvpmindorkssample.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskChoiceActivity extends BaseActivity implements ViewPagerAdapter.ChoiceClickListener,
        ViewPager.OnPageChangeListener, TaskChoiceMvpView {

    private static final String TAG = "TaskChoiceActivity";
    private static final String EXTRA = "topicId";
    public static final String IMAGE = "mTopicPhoto";
    private static final int MAX_PAGES = 10;
    private int cnt = 1;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.mChoiceLayout)
    ConstraintLayout mRootLayout;

    @BindView(R.id.mChoiceViewPager)
    DynamicViewPager mViewPager;

    private ViewPagerAdapter adapter;
    private List<ChoiceItemTest> choiceItemTests;
    private LoadingView loadingView;
    private TaskChoicePresenter presenter;


    public static void navigate(@NonNull AppCompatActivity activity, @NonNull View transitionImage, @NonNull LessonTopicItem topicItem) {
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

        loadingView = LoadingDialog.view(getSupportFragmentManager());
        DataManager manager = ((MvpApp)getApplication()).getDataManager();
        presenter = new TaskChoicePresenter(manager);
        presenter.attachView(this);
        presenter.initPresenter();
        presenter.requestForTasks();
        //mViewPager.setPageTransformer(false, new FadePageTransformer());
    }

    protected int getContentResource() {
        return R.layout.activity_task_choice;
    }

    @Override
    public LessonTopicItem getTopicsGeneral(){
        return getIntent().getParcelableExtra(EXTRA);
    }

    private void prepareWindowForAnimation() {
        Log.d(TAG, "prepareWindowForAnimation: ");
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
    public void onViewPageClicked(ChoiceItemTest choiceItemTest) {
        Intent intent = new Intent(this, DashActivity.class);
        startActivity(intent);
    }

    @Override
    public void setViewPagerData() {
        choiceItemTests = new ArrayList<>();
        for (int i =0; i<12; i++){
            choiceItemTests.add(new ChoiceItemTest(
                    i,
                    "ChocoLoco"+i,
                    R.drawable.ic_menu_gallery,
                    "In this section you can listen music or ..."));
        }

        mViewPager.setOffscreenPageLimit(choiceItemTests.size());
        adapter.setNewData(choiceItemTests);
    }

    @Override
    public void showTopicDetails(LessonTopicItem topicItem) {
        String title = topicItem.getTopicName();
        getSupportActionBar().setTitle("AAA");
        //getSupportActionBar().setSubtitle("This");
        mToolbar.post(new Runnable() {
            @Override
            public void run() {
                getSupportActionBar().setSubtitle("klasldk");
            }
        });
        Log.d(TAG, "showTopicDetails: "+getSupportActionBar().getSubtitle());
        Glide.with(this)
                .asBitmap()
                .load(topicItem.getTopicPhoto())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        mViewPager.setMaxPages(MAX_PAGES);
                        mViewPager.setParallaxEnabled(true);
                        mViewPager.setBackgroundAsset(resource);
                        mViewPager.setAdapter(adapter);
                    }
                });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (cnt==1){
            onPageSelected(position);
            cnt++;
        }
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
        if (adapter!=null||mViewPager!=null) {
            mViewPager.setAdapter(null);
            adapter = null;
        }
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

    @Override
    public void showLoading() {
        loadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        loadingView.hideLoading();
    }
}
