package com.example.aslan.mvpmindorkssample.ui.main.definition;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aslan.mvpmindorkssample.MvpApp;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.example.aslan.mvpmindorkssample.general.LoadingDialog;
import com.example.aslan.mvpmindorkssample.general.LoadingView;
import com.example.aslan.mvpmindorkssample.utils.NetworkUtils;

import java.io.IOException;

/**
 * Created by Paranoid on 17/9/10.
 */

public class DefinitionFragment extends BottomSheetDialogFragment
        implements View.OnClickListener, DefinitionContract.DefinitionMvpView {

    private static final String TAG = DefinitionFragment.class.getSimpleName();
    private static final String WORD_KEY = "word";

    private TextView mDefinitionView;
    private TextView mWordView;
    private TextView mSymbolView;
    private ImageView mAddView;
    private ImageView mPronunciationView;
    private String mWord;
    private String mPronUrl;
    private LoadingView mLoadingView;
    private DefinitionPresenter definitionPresenter;

    private MediaPlayer mMediaPlayer;

    public static DefinitionFragment newInstance(String word) {
        Bundle args = new Bundle();
        args.putString(WORD_KEY, word);
        DefinitionFragment fagFragment = new DefinitionFragment();
        fagFragment.setArguments(args);
        return fagFragment;
    }

    @Override
    public void showToastMessage(int resId) {
        Toast.makeText(getContext(),
                getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isNetworkConnected() {
        if (getContext()!=null)
            return NetworkUtils.isNetworkConnected(getContext());
        else return false;
    }

    @Override
    public void showLoading() {
        hideLoading();
        if (mLoadingView==null&&getActivity()!=null){
            mLoadingView = LoadingDialog.view(getActivity().getSupportFragmentManager());
        }
        mLoadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        if (mLoadingView!=null)
            mLoadingView.hideLoading();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_definition, container);
        mWord = getArguments().getString(WORD_KEY);

        mDefinitionView = view.findViewById(R.id.tv_definition);
        mWordView = view.findViewById(R.id.tv_word);
        mSymbolView = view.findViewById(R.id.tv_symbol);
        mAddView = view.findViewById(R.id.iv_add);
        mAddView.setVisibility(View.GONE);
        mWordView.setText(mWord);
        mPronunciationView = view.findViewById(R.id.iv_pronunciation);
        DataManager manager = ((MvpApp) getActivity().getApplicationContext()).getDataManager();
        definitionPresenter = new DefinitionPresenter(manager);
        definitionPresenter.attachView(this);
        definitionPresenter.getWordDefinition(mWord);
        return view;
    }

    @Override
    public void setWordDefinition(TranslationResponse response) {
        mSymbolView.setText(response.getTranscription());
        String translates = "";
        for (int i = 0; i < response.getTranslate().size(); i++) {
            translates = translates.concat((i + 1) + ". " + response.getTranslate().get(i).getValue() + "\n");
        }
        mDefinitionView.setText(translates);
        mAddView.setVisibility(View.VISIBLE);
        mAddView.setOnClickListener(DefinitionFragment.this);
        mPronUrl = response.getSoundUrl();
        if (!TextUtils.isEmpty(mPronUrl)) {
            mPronunciationView.setVisibility(View.VISIBLE);
            mPronunciationView.setOnClickListener(DefinitionFragment.this);
            prepareMedia();
        } else {
            mPronunciationView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_add:
                if (mWord != null && mWord.length() > 0 && mWord.length() < 20) {
                    definitionPresenter.addToDictionaryAfterShow(mWord);
                }
                break;
            case R.id.iv_pronunciation:
                if (mMediaPlayer != null) {
                    mMediaPlayer.start();
                }
                break;
            default:
                break;
        }
    }

    private void prepareMedia() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            try {
                mMediaPlayer.setDataSource(mPronUrl);
                mMediaPlayer.prepareAsync();
            } catch (IOException e) {
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
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

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        definitionPresenter.detachView();
        super.onDestroyView();
    }
}
