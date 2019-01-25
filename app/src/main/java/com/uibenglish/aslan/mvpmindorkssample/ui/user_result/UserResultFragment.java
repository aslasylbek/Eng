package com.uibenglish.aslan.mvpmindorkssample.ui.user_result;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.uibenglish.aslan.mvpmindorkssample.MvpApp;
import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.models.ResultExercise;
import com.uibenglish.aslan.mvpmindorkssample.data.models.ResultTopic;
import com.uibenglish.aslan.mvpmindorkssample.ui.FinishFragment;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserResultFragment extends BaseFragment implements UserResultContract.UserResultMvpView,
        ExpandableListView.OnGroupExpandListener,
        ExpandableListView.OnGroupCollapseListener,
        ExpandableListView.OnChildClickListener,
        TabLayout.OnTabSelectedListener {

    private static final String TAG = "UserResultFragment";
    private UserResultPresenter resultPresenter;

    @BindView(R.id.expandableListView)
    ExpandableListView mExpandableListView;

    @BindView(R.id.tabUserResult)
    TabLayout mTabUserResult;

    private CustomExpandableListAdapter adapter;
    private List<String> expandableListTitle;
    private List<ResultTopic> allData;
    private HashMap<String, List<String>> expandableListDetail;

    public static UserResultFragment newInstance() {
        return new UserResultFragment();
    }

    @Override
    protected void init(@Nullable Bundle bundle) {
        expandableListDetail = new HashMap<>();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        adapter = new CustomExpandableListAdapter(getBaseActivity(), expandableListTitle, expandableListDetail);
        mExpandableListView.setAdapter(adapter);
        mExpandableListView.setOnGroupExpandListener(this);
        mExpandableListView.setOnGroupCollapseListener(this);
        mExpandableListView.setOnChildClickListener(this);

        mTabUserResult.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabUserResult.setTabGravity(TabLayout.GRAVITY_CENTER);
        mTabUserResult.addOnTabSelectedListener(this);

        DataManager manager = ((MvpApp)getBaseActivity().getApplicationContext()).getDataManager();
        resultPresenter = new UserResultPresenter(manager);
        resultPresenter.attachView(this);
        resultPresenter.requestToUserResult();
    }

    @Override
    public void onGroupExpand(int groupPosition) {

    }

    @Override
    public void onGroupCollapse(int groupPosition) {


    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {

        return false;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        // change list

        expandableListDetail.clear();
        expandableListTitle.clear();
        ResultTopic topic = allData.get(tab.getPosition());
        for (int i = 0; i<topic.getExercises().size(); i++){
            List<String> results = new ArrayList<>();
            ResultExercise resultExercise = topic.getExercises().get(i);
            if (resultExercise.getResults().size()!=0){
                for (int k =0; k<resultExercise.getResults().size(); k++){
                    results.add(resultExercise.getResults().get(k).getDate()+"\nResult: "+resultExercise.getResults().get(k).getOverall());
                }
                expandableListDetail.put(topic.getExercises().get(i).getDescription(),results);
            }
        }
        adapter.setNewData(expandableListDetail);
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
    protected int getContentResource() {
        return R.layout.fragment_user_result;
    }

    @Override
    public void spreadUserResults(List<ResultTopic> resultTopicList) {
        allData = resultTopicList;
        for (int i=0; i<resultTopicList.size(); i++){
            ResultTopic resultTopic = resultTopicList.get(i);
            mTabUserResult.addTab(mTabUserResult.newTab().setText(resultTopic.getTitle()));
        }
        /*for (int i = 0; i<resultTopicList.get(0).getExercises().size(); i++){
            List<String> results = new ArrayList<>();
            ResultExercise resultExercise = resultTopicList.get(0).getExercises().get(i);
            if (resultExercise.getResults().size()!=0){
                for (int k =0; k<resultExercise.getResults().size(); k++){
                    results.add(resultExercise.getResults().get(k).getDate()+"\nResult: "+resultExercise.getResults().get(k).getOverall());
                }
                expandableListDetail.put(resultTopicList.get(0).getExercises().get(i).getDescription(),results);
            }

        }
        adapter.setNewData(expandableListDetail);*/
    }

    @Override
    public void onDestroyView() {
        if (adapter!=null){
            adapter = null;
        }
        resultPresenter.detachView();
        super.onDestroyView();
    }
}
