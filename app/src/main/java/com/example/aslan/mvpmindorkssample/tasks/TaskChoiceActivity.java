package com.example.aslan.mvpmindorkssample.tasks;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.general.LoadingDialog;
import com.example.aslan.mvpmindorkssample.general.LoadingView;
import com.example.aslan.mvpmindorkssample.main.expandable.LessonTopicItem;
import com.example.aslan.mvpmindorkssample.tinderCard.DashActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskChoiceActivity extends AppCompatActivity implements ViewPagerAdapter.ChoiceClickListener {

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
    private LoadingView loadingView;
    private List<ChoiceItemTest> choiceItemTests;


    public static void navigate(@NonNull AppCompatActivity activity, @NonNull View transitionImage, @NonNull LessonTopicItem topicItem) {
        Intent intent = new Intent(activity, TaskChoiceActivity.class);
        intent.putExtra(EXTRA, topicItem);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    public void onViewPageClicked(ChoiceItemTest choiceItemTest) {
        Intent intent = new Intent(this, DashActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareWindowForAnimation();
        setContentView(R.layout.activity_task_choice);
        ButterKnife.bind(this);
        loadingView = LoadingDialog.view(getSupportFragmentManager());
        loadingView.showLoading();
        setSupportActionBar(mToolbar);
        ViewCompat.setTransitionName(findViewById(R.id.mChoiceViewPager), IMAGE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        LessonTopicItem mTopicItem = getIntent().getParcelableExtra(EXTRA);
        showTopicDetails(mTopicItem);


        //you have to refactor
        choiceItemTests = new ArrayList<>();
        for (int i =0; i<12; i++){
            choiceItemTests.add(new ChoiceItemTest(
                    i,
                    "ChocoLoco"+i,
                    R.drawable.ic_menu_gallery,
                    "In this section you can listen music or ..."));
        }

        mViewPager.setOffscreenPageLimit(choiceItemTests.size());

        //mViewPager.setPageTransformer(false, new FadePageTransformer());
        adapter.setNewData(choiceItemTests);
        loadingView.hideLoading();

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

    public void showTopicDetails(LessonTopicItem topicItem) {

        String title = topicItem.getTopicName();
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle("This is America");
        adapter = new ViewPagerAdapter( this);

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

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter!=null||mViewPager!=null) {
            mViewPager.setAdapter(null);
            adapter = null;
        }
        Log.d(TAG, "onDestroy: ");
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
