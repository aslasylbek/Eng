package com.example.aslan.mvpmindorkssample.ui.reading;


import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.ui.AddWordListener;
import com.example.aslan.mvpmindorkssample.ui.main.definition.DefinitionFragment;
import com.example.aslan.mvpmindorkssample.ui.FragmentsListener;
import com.example.aslan.mvpmindorkssample.widget.CustomTextView;
import com.example.aslan.mvpmindorkssample.widget.CustomTextViewListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReaderFragment extends Fragment{

    private static final String ARTICLE = "article";

    private FragmentsListener listener;
    private AddWordListener addWordListener;
    @BindView(R.id.textView)
    CustomTextView textView;

    @BindView(R.id.btnFinishReading)
    Button mFinishReading;
    private String text;
    /***
     *
     * @param str
     * @return
     */
    public static ReaderFragment newInstance(String str) {
        Bundle args = new Bundle();
        ReaderFragment fragment = new ReaderFragment();
        args.putString(ARTICLE, str);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reader, container, false);
        ButterKnife.bind(this, view);
        listener = (FragmentsListener) getActivity();
        addWordListener = (AddWordListener)getActivity();
        mFinishReading.setText(getString(R.string.button_understand));
        if (getArguments()!=null)
            text = getArguments().getString(ARTICLE);
        setTextFromDb(text);
        return view;
    }


    public void setTextFromDb(String textFromDb) {
        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/TheanoOldStyle-Regular.ttf");
        textView.setTypeface(typeface);

        if (Build.VERSION.SDK_INT >= 24) {
            textView.setText(Html.fromHtml(textFromDb, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(textFromDb));
        }
        textView.setEventListener(new CustomTextViewListener() {
            @Override
            public void addToWordBook(String word) {
                addWordListener.sendToWordBook(word);
            }

            @Override
            public void showDefinitionFragment(String word) {
                DefinitionFragment.newInstance(word).show(getFragmentManager(), "Definition Fragment");
            }
        });

    }

    @OnClick(R.id.btnFinishReading)
    public void finishReading(){
        listener.sendData(0, "");
    }


    @Override
    public void onDestroyView() {
        listener = null;
        addWordListener = null;
        super.onDestroyView();
    }
}
