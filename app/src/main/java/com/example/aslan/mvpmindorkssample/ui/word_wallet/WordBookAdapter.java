package com.example.aslan.mvpmindorkssample.ui.word_wallet;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.transition.AutoTransition;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aslan.mvpmindorkssample.BuildConfig;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.models.WordCollection;

import java.util.ArrayList;
import java.util.List;

import static com.example.aslan.mvpmindorkssample.utils.AnimatorUtility.getFastOutSlowInInterpolator;


/**
 * Created by Paranoid on 17/9/10.
 */

public class WordBookAdapter extends RecyclerView.Adapter<WordBookAdapter.VocabularyViewHolder> {



    private static final int EXPAND = 0x1;
    private static final int COLLAPSE = 0x2;

    private List<WordCollection> list = new ArrayList<>();
    private Context mContext;
    private RecyclerView mWordList;
    private int mExpandedPosition = RecyclerView.NO_POSITION;
    /***
     * Without Animation
     */
    private final Transition mExpandCollapse;


    private OnItemClickListener mListener;

    public WordBookAdapter(Context context,
                           RecyclerView wordList,
                           OnItemClickListener listener) {
        mContext = context;
        mListener = listener;
        mWordList = wordList;

        mExpandCollapse = new AutoTransition();
        mExpandCollapse.setDuration(120);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
            mExpandCollapse.setInterpolator(getFastOutSlowInInterpolator(mContext));
    }

    public void setWalletWords(List<WordCollection> wordDeleteList){
        list.addAll(wordDeleteList);
        notifyDataSetChanged();
    }



    interface OnItemClickListener {
        void OnPronunciationClick(String audioHref);
        void OnDetailClick(int position, String word);
    }

    @Override
    public VocabularyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VocabularyViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.vocabulary_list_item, parent, false));
    }

    @Override
    public void onViewDetachedFromWindow(VocabularyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onBindViewHolder(final VocabularyViewHolder holder, int position) {

        boolean isExpanded = position == mExpandedPosition;

        final String id = list.get(position).getId();
        holder.itemView.setTag(id);
        holder.mVocabularyTextView.setText(list.get(position).getWord());
        holder.mSymbolTextView.setText(list.get(position).getTranscription());
        holder.mDefinitionTextView.setText(list.get(position).getTranslateWord());
        holder.mAudioHref = list.get(position).getSoundUrl();

        // set visibility
        holder.itemView.setActivated(isExpanded);
        holder.mProgressBar.setVisibility(
                TextUtils.isEmpty(list.get(position).getTranslateWord()) && isExpanded ? View.VISIBLE : View.GONE);
        holder.mSymbolTextView.setVisibility(
                !TextUtils.isEmpty(list.get(position).getTranscription()) && isExpanded? View.VISIBLE : View.GONE);
        holder.mDefinitionTextView.setVisibility(
                !TextUtils.isEmpty(list.get(position).getTranslateWord()) && isExpanded? View.VISIBLE : View.GONE);

        holder.mVocabularyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                if (position == RecyclerView.NO_POSITION) return;

                /***
                 *
                 */
                TransitionManager.beginDelayedTransition(mWordList, mExpandCollapse);

                // collapse currently expanded items
                if (RecyclerView.NO_POSITION != mExpandedPosition) {
                    notifyItemChanged(mExpandedPosition, COLLAPSE);
                }

                // expand this item
                if (mExpandedPosition != position) {
                    mExpandedPosition = position;
                    notifyItemChanged(position, EXPAND);
                } else {
                    mExpandedPosition = RecyclerView.NO_POSITION;
                }

                mListener.OnDetailClick(position, list.get(position).getWord());
            }
        });
    }

    @Override
    public void onBindViewHolder(VocabularyViewHolder holder, int position, List<Object> payloads) {
        if (payloads.contains(COLLAPSE) || payloads.contains(EXPAND)) {
            boolean isExpanded = position == mExpandedPosition;

            // set visibility
            holder.itemView.setActivated(isExpanded);
            holder.mProgressBar.setVisibility(
                    TextUtils.isEmpty(list.get(position).getTranslateWord()) && isExpanded ? View.VISIBLE : View.GONE);
            holder.mSymbolTextView.setVisibility(
                    !TextUtils.isEmpty(list.get(position).getTranscription()) && isExpanded? View.VISIBLE : View.GONE);
            holder.mDefinitionTextView.setVisibility(
                    !TextUtils.isEmpty(list.get(position).getTranslateWord()) && isExpanded? View.VISIBLE : View.GONE);
        } else {
            onBindViewHolder(holder, position);
        }
    }

    public void clearDeletedExpandedPosition(int deletedPosition) {
        if (mExpandedPosition == deletedPosition) {
            mExpandedPosition = RecyclerView.NO_POSITION;
        }
        list.remove(deletedPosition);
        notifyItemRemoved(deletedPosition);
    }

    public void undoLastDelete(int deletedPosition, WordCollection vocabulary){
        list.add(deletedPosition, vocabulary);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void swapCursor(Cursor cursor) {
        //mCursor = cursor;
        notifyDataSetChanged();
    }

    class VocabularyViewHolder extends RecyclerView.ViewHolder {

        TextView mVocabularyTextView;
        TextView mSymbolTextView;
        TextView mDefinitionTextView;
        ViewGroup mVocabularyContainer;
        ProgressBar mProgressBar;
        String mAudioHref;

        VocabularyViewHolder(View itemView) {
            super(itemView);
            mVocabularyTextView = itemView.findViewById(R.id.tv_vocabulary);
            mSymbolTextView = itemView.findViewById(R.id.tv_detail_symbol);
            mDefinitionTextView = itemView.findViewById(R.id.tv_detail_definition);
            mProgressBar = itemView.findViewById(R.id.pb_detail);
            mVocabularyContainer = itemView.findViewById(R.id.vocabulary_container);

            mSymbolTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.OnPronunciationClick(mAudioHref);
                }
            });
        }
    }
}
