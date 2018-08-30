package com.example.aslan.mvpmindorkssample.ui.vocabulary;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.example.aslan.mvpmindorkssample.MvpApp;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.ui.FragmentsListener;
import com.example.aslan.mvpmindorkssample.ui.base.BaseActivity;
import com.example.aslan.mvpmindorkssample.ui.main.content.Word;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.correctchoice.BuildWordFragment;
import com.example.aslan.mvpmindorkssample.ui.FinishFragment;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.remember.RememberFragment;
import com.example.aslan.mvpmindorkssample.widget.VocabularyViewPager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class VocabularyActivity extends BaseActivity implements VocabularyMvpView, FragmentsListener {

    private static final String TAG = "VocabularyActivity";

    @BindView(R.id.viewPager)
    VocabularyViewPager viewPager;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private List<Fragment> fragmentList;
    private int item = 0;
    private VocabularyTrainPresenter presenter;
    private VocabularyAdapter adapter;
    private int sizeOfData = 0;
    private int sizeOfResponse = 0;
    private int index;
    private int correctAns;
    private String topicId;
    private JsonArray datas;
    private JsonObject object;


    public static Intent getVocabularyIntent(Context context){
        return new Intent(context, VocabularyActivity.class);
    }

    @Override
    protected void init(@Nullable Bundle state) {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        fragmentList = new ArrayList<>();
        viewPager.setCurrentItem(item);
        adapter = new VocabularyAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);

        viewPager.disableScroll(true);
        index = getIntent().getIntExtra("position", 0);
        topicId = getIntent().getStringExtra("topicId");

        datas = new JsonArray();

        DataManager manager = ((MvpApp) getApplicationContext()).getDataManager();
        presenter = new VocabularyTrainPresenter(manager);
        presenter.attachView(this);
        presenter.requestForWord(topicId);
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_vocabulary;
    }

    @Override
    public void setData(List<Word> response) {
        sizeOfResponse = sizeOfData = response.size();
        ArrayList<String> fakeArr = new ArrayList<>();
        ArrayList<String> fakeTranslateArr = new ArrayList<>();
        for (int i=0; i<response.size(); i++) {
            fakeArr.add(response.get(i).getWord());
            fakeTranslateArr.add(response.get(i).getTranslateWord());
        }
        switch (index) {
            case 0:
                finish();
                break;
            case 1:
                prepareFragmentList(fakeArr, response, 1);
                break;
            case 2:
                prepareFragmentList(fakeTranslateArr, response, 2);
                break;
            case 3:
                prepareFragmentList(fakeTranslateArr, response, 3);
                break;
            case 4:
                prepareFragmentList(fakeTranslateArr, response, 4);
                break;

        }
        //fragmentList.add(new FinishFragment());
        adapter.notifyDataSetChanged();
    }

    public void prepareFragmentList(ArrayList<String> fakeArr, List<Word> response, int index){

        for (int i = 0; i < response.size(); i++) {
            Collections.shuffle(fakeArr);
            Word word = response.get(i);
            if (index==1) {
                fakeArr.remove(word.getWord());
                fakeArr.add(0, word.getWord());
                fragmentList.add(0, RememberFragment.newInstance(word));
                sizeOfData++;
            }
            else {
                fakeArr.remove(word.getTranslateWord());
                fakeArr.add(0, word.getTranslateWord());
            }
            /*bundle.putInt("trigger", index);
            bundle.putParcelable("ed", response.get(i));
            bundle.putStringArrayList("fk", new ArrayList<>(fakeArr.subList(0, 4)));

            RememberFragment fragment = new RememberFragment();
            BuildWordFragment fragmentEnglish = new BuildWordFragment();

            fragmentEnglish.setArguments(bundle);
            fragment.setArguments(bundle);*/

            fragmentList.add(BuildWordFragment.newInstance(index, word, fakeArr.subList(0,4)));
        }
        mProgressBar.setMax(sizeOfData);
    }

    @Override
    public void sendData(int responses, String wordId) {

        if (responses!=2){
            presenter.addToJson(wordId, responses);
            correctAns = correctAns+responses;
        }

        item++;
        mProgressBar.setProgress(item);
        if (item == sizeOfData) {
            JsonObject res = new JsonObject();
            int result;
            if (sizeOfData==sizeOfResponse) {
                 result = correctAns * 100 / sizeOfData;
            }
            else result = correctAns *100 / (sizeOfData/2);
            res.addProperty("topicId", topicId);
            res.addProperty("chapter", "ch"+index);
            presenter.requestSendResult(res, result);
        }
        viewPager.setCurrentItem(item);

        /*if (responses==2&&item==0) {
            sizeOfData = sizeOfData/2;
            Log.d(TAG, "sendData: "+sizeOfData);
        }
        else if (responses!=2){
            try {
                Log.d(TAG, "sendData: "+responses);
                object = new JsonObject();
                object.addProperty("wordId", wordId);
                object.addProperty("isCorrect", responses);
                datas.add(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
            correctAns = correctAns+responses;
        }
        item++;
        mProgressBar.setProgress(item);
        if (item == sizeOfData) {
            JsonObject res = new JsonObject();
            int result = correctAns*100/sizeOfData;
            res.addProperty("topicId", topicId);
            res.addProperty("chapter", "ch"+index);
            res.addProperty("result", result);
            res.addProperty("datas", new Gson().toJson(datas));
            presenter.requestSendResult(res, result);
        }
        viewPager.setCurrentItem(item);*/


    }


    @Override
    public ResultContent sendExerciseData() {
        int result = correctAns*100/sizeOfData;
        return new ResultContent("ch"+index, topicId, result);
    }

    @Override
    public void addFinishFragment(int result) {
        fragmentList.add(FinishFragment.newInstance(result));
        adapter.notifyDataSetChanged();
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
        if (adapter != null)
            adapter = null;
        if (viewPager != null)
            viewPager.setAdapter(null);
        presenter.detachView();
        super.onDestroy();
    }
}