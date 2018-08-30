package com.example.aslan.mvpmindorkssample.ui.listening;

import android.content.Context;
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

import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.ui.main.content.Questionanswer;

import java.util.ArrayList;
import java.util.List;

public class ListeningTaskAdapter  extends RecyclerView.Adapter<ListeningTaskAdapter.MyViewHolder> {

    public List<Questionanswer> editModelArrayList;
    private ListeningTaskListener listener;

    public ListeningTaskAdapter(List<Questionanswer> editModelArrayList, ListeningTaskListener taskListener){
        this.editModelArrayList = editModelArrayList;
        listener = taskListener;
    }

    @Override
    public ListeningTaskAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listening_task, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListeningTaskAdapter.MyViewHolder holder, final int position) {
        holder.bind(editModelArrayList.get(position), listener);
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

        public void bind(Questionanswer item, final ListeningTaskListener listener){
            textView.setText(getAdapterPosition()+1+". "+item.getQuestion());
            editText.setTag(item.getAnswer());
            Log.d("AAA", "bind: "+item.getAnswer());
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    String convertToLower = editText.getText().toString().toLowerCase();
                    String trimText = convertToLower.trim();
                    if (editText.getTag().equals(trimText)) {
                        editText.setFocusable(false);
                        listener.onSendingResult();
                    }
                    else if (trimText.length()!=0){
                        editText.setError("Incorrect");
                    }
                }
            });
        }
    }

    public interface ListeningTaskListener{
        void onSendingResult();
    }
}
