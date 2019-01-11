package com.uibenglish.aslan.mvpmindorkssample.ui.bbcenglish;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCLessonsList;

import java.util.ArrayList;
import java.util.List;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.BBCContentAdapterViewHolder> {

    private List<BBCLessonsList.Lesson> lessonList = new ArrayList<>();
    private Context mContext;
    private OnListItemClickListener mOnClickListener;


    public LessonsAdapter(Context context, OnListItemClickListener listener) {
        this.mContext = context;
        this.mOnClickListener = listener;
    }

    public interface OnListItemClickListener {
        void onClickItem(String lesson_id, String title);
    }

    @NonNull
    @Override
    public BBCContentAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bbc, parent, false);
        return new BBCContentAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BBCContentAdapterViewHolder holder, int position) {
        // Set cursor to position

        // Title set

        BBCLessonsList.Lesson lesson = lessonList.get(position);
        holder.mTitleTextView.setText(lesson.getTitle());

        // Time set
        holder.mTimeTextView.setText(lesson.getId());

        // Description set
        holder.mDescriptionTextView.setText(lesson.getDescription());

        // Use picasso to load image

        Picasso.with(mContext)
                .load(lesson.getImg())
                .resizeDimen(R.dimen.list_item_img_width, R.dimen.list_item_img_height)
                .centerCrop()
                .placeholder(R.drawable.image_place_holder)
                .into(holder.mThumbnailImageView);

        holder.itemView.setTag(lesson.getId());
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    public void reloadData(List<BBCLessonsList.Lesson> lessons){
        lessonList.clear();
        lessonList.addAll(lessons);
        notifyDataSetChanged();
    }

    public void clear(){
        lessonList.clear();
        notifyDataSetChanged();
    }

    public class BBCContentAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private ImageView mThumbnailImageView;
        private TextView mTitleTextView;
        private TextView mTimeTextView;
        private TextView mDescriptionTextView;

        public BBCContentAdapterViewHolder(View view) {
            super(view);
            mThumbnailImageView = view.findViewById(R.id.iv_thumbnail);
            mTitleTextView = view.findViewById(R.id.tv_title);
            mTimeTextView = view.findViewById(R.id.tv_time);
            mDescriptionTextView = view.findViewById(R.id.tv_description);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onClickItem(lessonList.get(getAdapterPosition()).getId(), lessonList.get(getAdapterPosition()).getTitle());
        }
    }


}
