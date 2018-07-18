package com.example.aslan.mvpmindorkssample.ui.tinderCard;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.aslan.mvpmindorkssample.MvpApp;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.example.aslan.mvpmindorkssample.ui.base.BaseActivity;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.listeners.ItemRemovedListener;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class VocabularyTrainActivity extends BaseActivity implements VocabularyMvpView {

    @BindView(R.id.swipeView)
    SwipePlaceHolderView mSwipeView;

    private MediaPlayer mediaPlayer;
    private VocabularyTrainPresenter mPresenter;
    private WordsCard wordsCard;
    private int countt = 0;

    @Override
    protected void init(@Nullable Bundle state) {
        DataManager manager = ((MvpApp) getApplicationContext()).getDataManager();
        mPresenter = new VocabularyTrainPresenter(manager);
        mPresenter.attachView(this);

        mediaPlayer = new MediaPlayer();
        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.words_swipe_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.words_swipe_out_msg_view));

        findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        findViewById(R.id.knownBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });



        final String [] a = new String[]{"hello", "family", "honestly"};
        countt = a.length-1;
        mPresenter.requestForWord("hello");

        mSwipeView.addItemRemoveListener(new ItemRemovedListener() {
            @Override
            public void onItemRemoved(int count) {
                if (count >= 0){
                    mPresenter.requestForWord(a[countt]);
                countt--;
                }
            }
        });
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_dash;
    }

    @Override
    public void setData(final TranslationResponse response) {
        Profile profile = new Profile();
        profile.setName(response.getWordForms().get(0).getWord());
        profile.setLocation(response.getTranslate().get(0).getValue());
        profile.setAge(response.getTranscription());
        profile.setImageUrl(response.getPicUrl());
        mSwipeView.addView(new WordsCard(this, profile, mSwipeView));

        Button mPlaySound = mSwipeView.findViewById(R.id.mPlayAudios);
        mPlaySound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.reset();
                try {
                    mediaPlayer.setDataSource(response.getSoundUrl());
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                        }
                    });
                } catch (IOException e) {
                    Log.e("AA", "prepare() failed");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        mediaPlayer.release();
        mediaPlayer = null;
        super.onDestroy();
    }
}
