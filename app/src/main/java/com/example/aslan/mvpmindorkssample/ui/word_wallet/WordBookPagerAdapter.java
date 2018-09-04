package com.example.aslan.mvpmindorkssample.ui.word_wallet;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Paranoid on 17/7/31.
 */

public class WordBookPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public WordBookPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragmentList = fragments;
    }

    public void setArticleSections(List<Fragment> fragments) {
        this.fragmentList = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}
