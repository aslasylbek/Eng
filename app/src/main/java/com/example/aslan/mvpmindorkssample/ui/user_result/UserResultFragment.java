package com.example.aslan.mvpmindorkssample.ui.user_result;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.aslan.mvpmindorkssample.MvpApp;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.models.ResultExercise;
import com.example.aslan.mvpmindorkssample.data.models.ResultTopic;
import com.example.aslan.mvpmindorkssample.ui.FinishFragment;
import com.example.aslan.mvpmindorkssample.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserResultFragment extends BaseFragment implements UserResultContract.UserResultMvpView, ExpandableListView.OnGroupExpandListener, ExpandableListView.OnGroupCollapseListener, ExpandableListView.OnChildClickListener {

    private static final String TAG = "UserResultFragment";
    private UserResultPresenter resultPresenter;

    @BindView(R.id.expandableListView)
    ExpandableListView mExpandableListView;

    private CustomExpandableListAdapter adapter;

    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    public static UserResultFragment newInstance() {
        UserResultFragment fragment = new UserResultFragment();
        return fragment;
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

        DataManager manager = ((MvpApp)getActivity().getApplicationContext()).getDataManager();
        resultPresenter = new UserResultPresenter(manager);
        resultPresenter.attachView(this);
        resultPresenter.requestToUserResult();
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        Toast.makeText(getBaseActivity(),
                expandableListTitle.get(groupPosition) + " List Expanded.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGroupCollapse(int groupPosition) {
        Toast.makeText(getBaseActivity(),
                expandableListTitle.get(groupPosition) + " List Collapsed.",
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {
        Toast.makeText(
                getBaseActivity(),
                expandableListTitle.get(groupPosition)
                        + " -> "
                        + expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition), Toast.LENGTH_SHORT
        ).show();
        return false;
    }

    @Override
    protected int getContentResource() {
        return R.layout.fragment_user_result;
    }

    @Override
    public void spreadUserResults(List<ResultTopic> resultTopicList) {
        for (int i=0; i<resultTopicList.size(); i++){
            ResultTopic resultTopic = resultTopicList.get(i);

            for (int j=0; j<resultTopic.getExercises().size(); j++){
                List<String> results = new ArrayList<>();
                ResultExercise resultExercise = resultTopic.getExercises().get(j);
                if (resultExercise.getResults().size()!=0){
                    for (int k =0; k<resultExercise.getResults().size(); k++){
                        results.add(resultExercise.getResults().get(k).getDate()+"\nResult: "+resultExercise.getResults().get(k).getOverall());
                    }
                    expandableListDetail.put(resultTopic.getTitle()+"\n\n"+resultTopic.getExercises().get(j).getDescription(),results);
                }
            }
        }
        adapter.setNewData(expandableListDetail);
    }

    @Override
    public void onDestroyView() {
        resultPresenter.detachView();
        super.onDestroyView();
    }
}
