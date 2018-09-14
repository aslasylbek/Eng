package com.example.aslan.mvpmindorkssample.ui.grammar;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.aslan.mvpmindorkssample.MvpApp;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.ui.base.BaseActivity;
import com.example.aslan.mvpmindorkssample.ui.main.content.Grammar;
import com.example.aslan.mvpmindorkssample.ui.FragmentsListener;
import com.example.aslan.mvpmindorkssample.ui.reading.TrueFalseFragment;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.VocabularyAdapter;
import com.example.aslan.mvpmindorkssample.widget.VocabularyViewPager;
import com.example.aslan.mvpmindorkssample.ui.FinishFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GrammarActivity extends BaseActivity implements FragmentsListener, GrammarContract.GrammarMvpView{

    private static final String TAG = "GrammarActivity";
   private int item = 0;
   private int correctAns = 0;
   private int sizeOfData;
   private List<Fragment> fragmentList;
   private VocabularyAdapter adapter;
   private GrammarPresenter presenter;
   private String topicId;

   private long startTime;
   private int res_ans = 0;
   private int res_cons = 0;

    @BindView(R.id.mGrammarViewPager)
    VocabularyViewPager mGrammarViewPager;

    public static Intent getGrammarActivity(Context context){
        return new Intent(context, GrammarActivity.class);
    }

    @Override
    protected void init(@Nullable Bundle state) {
        setTitle(getString(R.string.item_grammar));
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        fragmentList = new ArrayList<>();
        adapter = new VocabularyAdapter(getSupportFragmentManager(), fragmentList);
        mGrammarViewPager.disableScroll(true);
        mGrammarViewPager.setAdapter(adapter);
        topicId = getIntent().getStringExtra("topicId");
        DataManager manager = ((MvpApp)getApplication()).getDataManager();
        presenter = new GrammarPresenter(manager);
        presenter.attachView(this);
        presenter.requestGrammarCollection(topicId);
    }

    @Override
    public void setNewDataFromRoom(List<Grammar> grammarList){
        startTime = System.currentTimeMillis()/1000;
        sizeOfData = grammarList.size();
        for (int i=0; i<grammarList.size(); i++) {
            Grammar grammar = grammarList.get(i);
            fragmentList.add(GrammarFragment.newInstance(grammar.getSentence(), grammar.getTranslate()));
            fragmentList.add(0, TrueFalseFragment.newInstance(grammar.getQuestion(), grammar.getAnswer()));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_grammar;
    }

    @Override
    public void sendData(int isCorrect, String wordId) {
        item++;
        if (wordId.equals("cons")){
            res_cons+=isCorrect;
        }
        else if (wordId.equals("qa")){
            res_ans+=isCorrect;
        }
        if (item==sizeOfData*2){
            res_ans = res_ans*100/fragmentList.size();
            res_cons = res_cons*100/fragmentList.size();
            presenter.sendGrammarResult( topicId, res_ans, res_cons, startTime);
        }

        mGrammarViewPager.setCurrentItem(item);
    }

    @Override
    public void addFinishFragment(int result) {
        fragmentList.add(FinishFragment.newInstance(result));
        adapter.notifyDataSetChanged();
        mGrammarViewPager.setCurrentItem(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}
