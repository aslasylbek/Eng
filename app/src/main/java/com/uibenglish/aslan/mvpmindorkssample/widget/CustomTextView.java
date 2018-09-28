package com.uibenglish.aslan.mvpmindorkssample.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.uibenglish.aslan.mvpmindorkssample.R;

public class CustomTextView extends AppCompatTextView {

    private static final String TAG = "CustomTextView";

    private CustomTextViewListener listener;

    public CustomTextView(Context context) {
        super(context);
        Log.d("AAA", "CustomTextView: ");
        init();
    }

    public void setEventListener(CustomTextViewListener mCustomListener){
        this.listener = mCustomListener;
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("AAA", "CustomTextView: 1");
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Log.d("AAA", "CustomTextView: 2");
        init();
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }

    public void init(){
        setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                actionMode.getMenuInflater().inflate(R.menu.select_text_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                menu.removeItem(android.R.id.selectAll);
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                String word;
                actionMode.invalidate();
                switch (menuItem.getItemId()) {
                    case R.id.menu_word_book:
                        word = getSelectedText();
                        if (word != null && word.length() > 0 && word.length() < 20) {
                            if (listener!=null){
                                listener.addToWordBook(word);
                            }
                        }
                        actionMode.finish();
                        break;
                    case R.id.menu_definition:
                        word = getSelectedText();
                        if (listener!=null) {
                            listener.showDefinitionFragment(word);
                        }
                        actionMode.finish();
                        break;
                    default:

                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });
    }


    private String getSelectedText() {
        String selectedText = "";
        if (isFocused()) {
            final int textStartIndex = getSelectionStart();
            final int textEndIndex = getSelectionEnd();

            int min = Math.max(0, Math.min(textStartIndex, textEndIndex));
            int max = Math.max(0, Math.max(textStartIndex, textEndIndex));
            selectedText = getText().subSequence(min, max).toString().trim();
        }
        return selectedText;
    }
}
