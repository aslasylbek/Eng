package com.example.aslan.mvpmindorkssample.ui.word_wallet;

import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aslan.mvpmindorkssample.MvpApp;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.example.aslan.mvpmindorkssample.data.models.WordCollection;
import com.example.aslan.mvpmindorkssample.ui.base.BaseFragment;
import com.example.aslan.mvpmindorkssample.ui.main.content.Word;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Paranoid on 17/9/10.
 */

public class WordBookFragment extends BaseFragment implements
        WordBookAdapter.OnItemClickListener, WordBookContract.WordBookMvpView {

    private static final int WORD_BOOK_LOADER_ID = 64236;

    @BindView(R.id.rv_word_book_list)
    RecyclerView recyclerView;

    private WordBookAdapter mAdapter;
    private ItemTouchHelper mSwipeToDeleteHelper;
    private MediaPlayer mMediaPlayer;
    private String mCurrentAudioHref = "";
    private WordBookPresenter bookPresenter;
    private List<WordCollection> trashList;

    public WordBookFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ItemTouchHelper.SimpleCallback swipeToDelete = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                final String vocabulary = ((TextView) viewHolder.itemView.findViewById(R.id.tv_vocabulary)).getText().toString();
                final String id = (String) viewHolder.itemView.getTag();
                mAdapter.clearDeletedExpandedPosition(position);
                viewHolder.itemView.setAlpha(1.0f);
                bookPresenter.addWordAsKnown(id);
                Snackbar.make(viewHolder.itemView, R.string.vocabulary_refactored, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                /*Snackbar.make(viewHolder.itemView, R.string.vocabulary_refactored, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.undo, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for (int i=0; i<trashList.size(); i++){
                                    if (id.equals(trashList.get(i).getId())){
                                        mAdapter.undoLastDelete(position, trashList.get(i));
                                        *//***
                                         * Make request
                                         *//*
                                    }
                                }

                            }
                        }).show();*/
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    // Get RecyclerView item from the ViewHolder
                    View itemView = viewHolder.itemView;
                    itemView.setAlpha(1.0f - Math.abs(dX) / itemView.getWidth());

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }
        };
        mSwipeToDeleteHelper = new ItemTouchHelper(swipeToDelete);
    }



    @Override
    protected void init(@Nullable Bundle bundle) {
        getActivity().setTitle(R.string.custom_word_book);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        WordItemAnimator animator = new WordItemAnimator();
        recyclerView.setItemAnimator(animator);
        mAdapter = new WordBookAdapter(getContext(), recyclerView, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(manager);
        mSwipeToDeleteHelper.attachToRecyclerView(recyclerView);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                manager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));

        recyclerView.addItemDecoration(dividerItemDecoration);
        DataManager dataManager = ((MvpApp)getActivity().getApplicationContext()).getDataManager();
        bookPresenter = new WordBookPresenter(dataManager);
        bookPresenter.attachView(this);
        bookPresenter.requestWordsCollection();
    }



    @Override
    protected int getContentResource() {
        return R.layout.fragent_word_book_list;
    }

    @Override
    public void setWordsCollection(List<WordCollection> collectionList) {
        trashList = collectionList;
        mAdapter.setWalletWords(collectionList);
    }

    @Override
    public void showSnackbar() {
        Snackbar.make(getView(),  R.string.vocabulary_refactored, Snackbar.LENGTH_LONG)
                        .setAction("Action",null).show();
    }

    @Override
    public void OnPronunciationClick(String audioHref) {
        if (TextUtils.isEmpty(audioHref)) {
            Toast.makeText(getContext(), R.string.no_audio, Toast.LENGTH_SHORT).show();
        } else if (mCurrentAudioHref.equals(audioHref) && mMediaPlayer != null) {
            mMediaPlayer.start();
        } else {
            prepareMedia(audioHref);
            mCurrentAudioHref = audioHref;
        }
    }

    @Override
    public void OnDetailClick(final int id, String word) {
        /***
         * Reauest about word
         */

        /*DataManager manager = ((MvpApp)getActivity().getApplicationContext()).getDataManager();
        manager.requestForWordTranslation(word, new DataManager.GetWordTranslation() {
            @Override
            public void onSuccess(TranslationResponse response) {
                String translates = "";
                WordDelete wordDelete = list.get(id);
                wordDelete.setAudio(response.getSoundUrl());
                for (int i=0; i<response.getTranslate().size(); i++){
                    translates = translates.concat((i+1)+". "+response.getTranslate().get(i).getValue()+"\n");
                }
                wordDelete.setDef(translates);
                wordDelete.setTrancript(response.getTranscription());
                list.set(id, wordDelete);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError() {

            }
        });*/
    }

    private void prepareMedia(String audioHref) {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(audioHref);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        } catch (IOException e) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    static class WordItemAnimator extends DefaultItemAnimator {

        @Override
        public boolean animateMove(
                RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
            return false;
        }
    }
}
