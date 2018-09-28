package com.uibenglish.aslan.mvpmindorkssample.ui.word_wallet;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uibenglish.aslan.mvpmindorkssample.MvpApp;
import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.models.WordCollection;
import com.uibenglish.aslan.mvpmindorkssample.ui.AddWordListener;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BaseFragment;
import com.uibenglish.aslan.mvpmindorkssample.ui.tasks.CustomViewPager;
import com.uibenglish.aslan.mvpmindorkssample.ui.tasks.ViewPagerAdapter;
import com.uibenglish.aslan.mvpmindorkssample.widget.VocabularyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordBookMainFragment extends BaseFragment implements WordBookContract.WordBookMvpView, TabLayout.OnTabSelectedListener, AddWordListener {


    private static final String TAG = "WordBookMainFragment";

    @BindView(R.id.tabbar)
    TabLayout mTabLayout;

    @BindView(R.id.vp_word_book)
    VocabularyViewPager mViewPager;

    private WordBookPresenter presenter;

    private WordBookPagerAdapter pagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void init(@Nullable Bundle bundle) {

        getActivity().setTitle(R.string.custom_word_book);
        mTabLayout.addOnTabSelectedListener(this);
        mViewPager.disableScroll(true);
        pagerAdapter = new WordBookPagerAdapter(getChildFragmentManager(), fragmentList);
        Log.d(TAG, "init: "+fragmentList.size());
        mViewPager.setAdapter(pagerAdapter);
        DataManager manager = ((MvpApp)getBaseActivity().getApplicationContext()).getDataManager();
        presenter = new WordBookPresenter(manager);
        presenter.attachView(this);
        presenter.requestWordsCollection();
    }

    @Override
    protected int getContentResource() {
        return R.layout.fragment_word_book_main;
    }

    @Override
    public void setWordsCollection(List<WordCollection> wordsCollections) {

        /**
         * @// TODO: 12.09.2018 refactor if there 3 category of wallet
         */
        for (int j = 0; j < 2; j++) {
            List<WordCollection> newWordCollection = new ArrayList<>();
            for (int i = 0; i < wordsCollections.size(); i++) {
                WordCollection wordCollection = wordsCollections.get(i);
                if (j == 0 && wordCollection.getRating().equals("0")) {
                    newWordCollection.add(wordCollection);
                }
                else if (j==1 && !wordCollection.getRating().equals("0")){
                    newWordCollection.add(wordCollection);
                }
            }
            fragmentList.add(WordBookFragment.newInstance(j, newWordCollection));
        }
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void sendToWordBook(String mWord) {
        //Send Word As Known equal rating = 1; Refactor if AddWordLis changes
        presenter.addWordAsKnown(mWord);

    }

    @Override
    public void showSnackbar() {
        Snackbar.make(getView(),  R.string.vocabulary_refactored, Snackbar.LENGTH_LONG)
                .setAction("Action",null).show();
    }


    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        //nothing
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        //nothing
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onDestroyView() {
        pagerAdapter = null;
        if (mViewPager!=null) {
            mViewPager = null;
        }
        presenter.detachView();
        super.onDestroyView();
    }
}
