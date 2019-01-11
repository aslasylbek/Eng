package com.uibenglish.aslan.mvpmindorkssample.ui.bbcenglish.lesson;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCLesson;
import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCTaskArray;
import com.uibenglish.aslan.mvpmindorkssample.widget.BaseAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static io.fabric.sdk.android.Fabric.TAG;

public class BBCTaskAdapter extends BaseAdapter<BBCTaskAdapter.TaskViewHolder, BBCTaskArray> {

    public BBCTaskAdapter(@NonNull List items) {
        super(items);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listening_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        BBCTaskArray transcript = getItem(position);
        holder.bind(transcript);
    }

    @Override
    public void onClick(View v) {

    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvQuestion)
        TextView mQuestion;
        @BindView(R.id.editid)
        EditText mAnswer;

        public TaskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bind(@NonNull final BBCTaskArray repository) {
            if (repository.getSentence()==null){
                mQuestion.setText(repository.getOrDefinition());
                if (repository.getEditTextColor()!=null) {
                    mAnswer.setBackgroundColor(Color.parseColor(repository.getEditTextColor()));
                    mAnswer.setEnabled(false);
                }
            }
            else{
                mQuestion.setText(repository.getSentence());
                if (repository.getEditTextColor()!=null) {
                    mAnswer.setBackgroundColor(Color.parseColor(repository.getEditTextColor()));
                    mAnswer.setEnabled(false);
                }
            }
            mAnswer.setTag(repository.getWord());
            mAnswer.setText(repository.getUserAnswer());
            mAnswer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus){
                        repository.setUserAnswer(mAnswer.getText().toString());
                    }
                }
            });
        }


    }
}
