package com.uibenglish.aslan.mvpmindorkssample.ui.bbcenglish;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.uibenglish.aslan.mvpmindorkssample.MvpApp;
import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCLessonsList;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BaseFragment;
import com.uibenglish.aslan.mvpmindorkssample.ui.bbcenglish.lesson.BBCLessonActivity;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BBCListFragment extends BaseFragment implements BBCListContract.BBCListMvpView, LessonsAdapter.OnListItemClickListener {

    @BindView(R.id.rvBBC)
    RecyclerView rvBBC;

    private BBCListPresenter presenter;
    private LessonsAdapter adapter;
    private int categoryId;

    @Override
    protected void init(@Nullable Bundle bundle) {
        DataManager manager = ((MvpApp)getBaseActivity().getApplicationContext()).getDataManager();
        presenter = new BBCListPresenter(manager);
        presenter.attachView(this);
        adapter = new LessonsAdapter(getActivity(), this);
        rvBBC.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBBC.setAdapter(adapter);
    }

    @Override
    protected int getContentResource() {
        return R.layout.fragment_bbclist;
    }

    @Override
    public void reloadListData(List<BBCLessonsList.Lesson> lessonList) {
        adapter.reloadData(lessonList);
    }

    public void setCategoryId(int categoryId){
        this.categoryId = categoryId;
        adapter.clear();
        presenter.requestLessonsById(categoryId);

    }
    @Override
    public void onClickItem(String lesson_id, String title) {
        BBCLessonActivity.navigate(getBaseActivity(), title, lesson_id);
    }

    @Override
    public void onDestroyView() {
        if (adapter!=null){
            rvBBC.setAdapter(null);
            adapter = null;
        }
        presenter.detachView();
        super.onDestroyView();
    }


}
