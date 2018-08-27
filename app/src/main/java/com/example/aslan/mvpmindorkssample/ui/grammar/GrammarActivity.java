package com.example.aslan.mvpmindorkssample.ui.grammar;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aslan.mvpmindorkssample.MvpApp;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.ui.base.BaseActivity;
import com.example.aslan.mvpmindorkssample.ui.main.content.Grammar;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.FragmentsListener;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.VocabularyActivity;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.VocabularyAdapter;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.finish.FinishFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GrammarActivity extends BaseActivity implements FragmentsListener, GrammarContract.GrammarMvpView{

   private int item = 0;
   private int correctAns = 0;
   private int sizeOfData;
   private List<Fragment> fragmentList;
   private VocabularyAdapter adapter;
   private GrammarPresenter presenter;
   private String topicId;

    @BindView(R.id.mGrammarViewPager)
    ViewPager mGrammarViewPager;

    public static Intent getGrammarActivity(Context context){
        return new Intent(context, GrammarActivity.class);
    }

    @Override
    protected void init(@Nullable Bundle state) {
        fragmentList = new ArrayList<>();
        adapter = new VocabularyAdapter(getSupportFragmentManager(), fragmentList);
        final View view = findViewById(R.id.mGrammarViewPager);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        mGrammarViewPager.setAdapter(adapter);
        topicId = getIntent().getStringExtra("topicId");


        DataManager manager = ((MvpApp)getApplication()).getDataManager();
        presenter = new GrammarPresenter(manager);
        presenter.attachView(this);
        presenter.getGrammarLocalData(topicId);
    }

    @Override
    public void setNewDataFromRoom(List<Grammar> grammarList){
        sizeOfData = grammarList.size();

        for (int i=0; i<grammarList.size(); i++) {
            GrammarFragment fragment = new GrammarFragment();
            Bundle bundle = new Bundle();
            bundle.putString("keyTrans", grammarList.get(i).getTranslate());
            bundle.putString("keyText", grammarList.get(i).getSentence());
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_grammar;
    }

    @Override
    public void sendData(int isCorrect, String wordId) {
        correctAns = correctAns+isCorrect;
        item++;
        if (item==sizeOfData){
            presenter.sendGrammarResult("ph"+3, topicId, correctAns*100/sizeOfData);
        }
        mGrammarViewPager.setCurrentItem(item);
    }

    @Override
    public void addFinishFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt("res", correctAns*100/sizeOfData);
        FinishFragment finishFragment = new FinishFragment();
        finishFragment.setArguments(bundle);
        fragmentList.add(finishFragment);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}
