package com.example.aslan.mvpmindorkssample.ui.vocabulary;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.example.aslan.mvpmindorkssample.MvpApp;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.example.aslan.mvpmindorkssample.ui.base.BaseActivity;
import com.example.aslan.mvpmindorkssample.ui.login.LoginActivity;
import com.example.aslan.mvpmindorkssample.ui.main.content.Word;
import com.example.aslan.mvpmindorkssample.ui.main.expandable.LessonChildItem;
import com.example.aslan.mvpmindorkssample.ui.tasks.TaskChoiceActivity;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.correctchoice.BuildWordFragment;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.finish.FinishFragment;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.remember.RememberFragment;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

import static android.support.constraint.Constraints.TAG;

public class VocabularyActivity extends BaseActivity implements VocabularyMvpView, FragmentsListener {

    private static final String TAG = "VocabularyActivity";

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private List<Fragment> fragmentList;
    private int item = 0;
    private VocabularyTrainPresenter presenter;
    private VocabularyAdapter adapter;
    private int sizeOfData;
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
        final View view = findViewById(R.id.viewPager);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
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
        sizeOfData = response.size();
        mProgressBar.setMax(sizeOfData*2);
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
            Bundle bundle = new Bundle();
            Collections.shuffle(fakeArr);
            if (index==1) {
                fakeArr.remove(response.get(i).getWord());
                fakeArr.add(0, response.get(i).getWord());
            }
            else {
                fakeArr.remove(response.get(i).getTranslateWord());
                fakeArr.add(0, response.get(i).getTranslateWord());
            }
            bundle.putInt("trigger", index);
            bundle.putParcelable("ed", response.get(i));
            bundle.putStringArrayList("fk", new ArrayList<>(fakeArr.subList(0, 4)));

            RememberFragment fragment = new RememberFragment();
            BuildWordFragment fragmentEnglish = new BuildWordFragment();

            fragmentEnglish.setArguments(bundle);
            fragment.setArguments(bundle);

            fragmentList.add(0, fragment);
            fragmentList.add(fragmentEnglish);
        }
    }

    @Override
    public void sendData(int responses, String wordId) {

        if (responses!=2) {
            try {
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
        if (item == sizeOfData * 2) {
            JsonObject res = new JsonObject();
            res.addProperty("topicId", topicId);
            res.addProperty("chapter", "ch"+index);
            res.addProperty("result", correctAns*100/sizeOfData);
            res.addProperty("datas", new Gson().toJson(datas));
            presenter.requestSendResult(res);
        }
        viewPager.setCurrentItem(item);
    }

    @Override
    public ResultContent sendExerciseData() {
        int result = correctAns*100/sizeOfData;
        return new ResultContent("ch"+index, topicId, result);
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