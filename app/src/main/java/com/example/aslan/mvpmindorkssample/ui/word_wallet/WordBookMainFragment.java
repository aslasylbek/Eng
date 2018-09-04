package com.example.aslan.mvpmindorkssample.ui.word_wallet;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aslan.mvpmindorkssample.MvpApp;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.models.WordCollection;
import com.example.aslan.mvpmindorkssample.ui.base.BaseFragment;
import com.example.aslan.mvpmindorkssample.ui.tasks.CustomViewPager;
import com.example.aslan.mvpmindorkssample.ui.tasks.ViewPagerAdapter;
import com.example.aslan.mvpmindorkssample.widget.VocabularyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordBookMainFragment extends Fragment implements TabLayout.OnTabSelectedListener {


    private static final String TAG = "WordBookMainFragment";

    @BindView(R.id.tabbar)
    TabLayout mTabLayout;

    @BindView(R.id.vp_word_book)
    VocabularyViewPager mViewPager;

    private WordBookPresenter presenter;

    private WordBookPagerAdapter pagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_word_book_main, container, false);
        ButterKnife.bind(this, rootView);

        getActivity().setTitle(R.string.custom_word_book);
        mTabLayout.addOnTabSelectedListener(this);
        for (int i=0; i<3; i++)
            fragmentList.add(WordBookFragment.newInstance(i));
        mViewPager.disableScroll(true);
        pagerAdapter = new WordBookPagerAdapter(getFragmentManager(), fragmentList);
        mViewPager.setAdapter(pagerAdapter);
        return rootView;
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



}
