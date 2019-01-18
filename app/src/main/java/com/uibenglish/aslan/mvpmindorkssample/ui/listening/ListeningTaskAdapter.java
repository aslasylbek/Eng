package com.uibenglish.aslan.mvpmindorkssample.ui.listening;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Questionanswer;

import java.util.ArrayList;
import java.util.List;

public class ListeningTaskAdapter  extends RecyclerView.Adapter<ListeningTaskAdapter.MyViewHolder> {

    private List<Questionanswer> editModelArrayList = new ArrayList<>();

    public ListeningTaskAdapter(){
    }

    public void setEditModelArrayList(List<Questionanswer> newList){
        editModelArrayList.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public ListeningTaskAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listening_task, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListeningTaskAdapter.MyViewHolder holder, final int position) {
        holder.bind(editModelArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return editModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private EditText editText;
        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvQuestion);
            editText = itemView.findViewById(R.id.editid);
        }

        public void bind(final Questionanswer item){
            if (item.getEditTextColor()!=null){
                editText.setBackgroundColor(Color.parseColor(item.getEditTextColor()));
                editText.setEnabled(false);
            }
            textView.setText(getAdapterPosition()+1+". "+item.getQuestion());
            editText.setTag(item.getAnswer());
            editText.setText(item.getUserAnswer());
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus){
                        item.setUserAnswer(editText.getText().toString());
                    }
                }
            });
        }


    }

}
