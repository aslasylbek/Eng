package com.uibenglish.aslan.mvpmindorkssample.ui.bbcenglish;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.uibenglish.aslan.mvpmindorkssample.MvpApp;
import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCCategories;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BaseFragment;
import com.uibenglish.aslan.mvpmindorkssample.ui.word_wallet.WordBookPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BBCFragment extends BaseFragment implements BBCContract.BBCMvpView, TabLayout.OnTabSelectedListener {


    private static final String TAG = "BBCFragment";
    @BindView(R.id.tabbarCategories)
    TabLayout tabCategories;

    @BindView(R.id.lessonsFrame)
    FrameLayout lessonsFrame;

    private BBCPresenter presenter;
    private BBCListFragment bbcListFragment;

    @Override
    protected void init(@Nullable Bundle bundle) {
        tabCategories.addOnTabSelectedListener(this);
        DataManager manager = ((MvpApp)getActivity().getApplicationContext()).getDataManager();
        presenter = new BBCPresenter(manager);
        presenter.attachView(this);

        bbcListFragment = new BBCListFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.lessonsFrame, bbcListFragment);
        transaction.commit();

        presenter.requestForBCCCategories();
    }

    @Override
    protected int getContentResource() {
        return R.layout.fragment_bbc;
    }

    @Override
    public void setTabbarTitles(List<BBCCategories> categoriesList) {
        for(int i=0; i<categoriesList.size(); i++){
            TabLayout.Tab tab = tabCategories.newTab();
            tab.setText(categoriesList.get(i).getTitle());
            tab.setContentDescription(categoriesList.get(i).getDescription());
            tab.setTag(categoriesList.get(i).getId());
            tabCategories.addTab(tab);
        }


    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //Reload frame fragment
        bbcListFragment.setCategoryId(Integer.parseInt(String.valueOf(tab.getTag())));

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
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }
}
