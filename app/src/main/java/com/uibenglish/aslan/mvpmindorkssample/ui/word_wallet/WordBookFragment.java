package com.uibenglish.aslan.mvpmindorkssample.ui.word_wallet;

import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.audio.AudioSyntethis;
import com.uibenglish.aslan.mvpmindorkssample.audio.OnAudioTTSCompleteListener;
import com.uibenglish.aslan.mvpmindorkssample.data.models.WordCollection;
import com.uibenglish.aslan.mvpmindorkssample.ui.AddWordListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Paranoid on 17/9/10.
 */

public class WordBookFragment extends Fragment implements
        WordBookAdapter.OnItemClickListener, OnAudioTTSCompleteListener {

    private static final int WORD_BOOK_LOADER_ID = 64236;
    private static final String SECTION = "section";
    private static final String WORD_COLLECTIONS = "wordCollection";


    @BindView(R.id.rv_word_book_list)
    RecyclerView recyclerView;

    private WordBookAdapter mAdapter;
    private ItemTouchHelper mSwipeToDeleteHelper;
    private AudioSyntethis audioSyntethis;
    private String mCurrentAudioHref = "";
    private AddWordListener listener;


    public static WordBookFragment newInstance(int section, List<WordCollection> wordCollection) {
        Bundle args = new Bundle();
        WordBookFragment fragment = new WordBookFragment();
        args.putParcelableArrayList(WORD_COLLECTIONS, new ArrayList<>(wordCollection));
        args.putInt(SECTION, section);
        fragment.setArguments(args);
        return fragment;
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
                listener.sendToWordBook(id);

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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragent_word_book_list, container, false);
        ButterKnife.bind(this, view);
        listener = (AddWordListener)getParentFragment();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        WordItemAnimator animator = new WordItemAnimator();
        recyclerView.setItemAnimator(animator);
        mAdapter = new WordBookAdapter(getContext(), recyclerView, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(manager);

        audioSyntethis = new AudioSyntethis(getActivity(), this);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                manager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));

        recyclerView.addItemDecoration(dividerItemDecoration);

        int section=0;
        List<WordCollection> collection = new ArrayList<>();
        if (getArguments()!=null) {
            collection = getArguments().getParcelableArrayList(WORD_COLLECTIONS);
            section = getArguments().getInt(SECTION);
        }
        mAdapter.setWalletWords(collection);

        if (section==0)
            mSwipeToDeleteHelper.attachToRecyclerView(recyclerView);


        return view;
    }


    @Override
    public void OnPronunciationClick(String audioHref) {
        audioSyntethis.setText(audioHref);
        audioSyntethis.playSyntethMedia();
        /*if (TextUtils.isEmpty(audioHref)) {
            Toast.makeText(getContext(), R.string.no_audio, Toast.LENGTH_SHORT).show();
        } else if (mCurrentAudioHref.equals(audioHref) && mMediaPlayer != null) {
            mMediaPlayer.start();
        } else {
            prepareMedia(audioHref);
            mCurrentAudioHref = audioHref;
        }*/
    }

    @Override
    public void onAudioStart() {

    }

    @Override
    public void onAudioDone() {

    }

    @Override
    public void onAudioError() {

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

    /*private void prepareMedia(String audioHref) {
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
*/
    @Override
    public void onPause() {
        super.onPause();
        /*if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }*/
    }

    @Override
    public void onDestroyView() {
        if (audioSyntethis!=null){
            audioSyntethis.destroyAudioPlayer();
            audioSyntethis = null;
        }
        super.onDestroyView();
    }

    static class WordItemAnimator extends DefaultItemAnimator {

        @Override
        public boolean animateMove(
                RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
            return false;
        }
    }
}
