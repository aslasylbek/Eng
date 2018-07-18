package com.example.aslan.mvpmindorkssample.ui.vocabulary;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.aslan.mvpmindorkssample.MvpApp;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.example.aslan.mvpmindorkssample.ui.base.BaseActivity;
import com.example.aslan.mvpmindorkssample.ui.tinderCard.VocabularyMvpView;
import com.example.aslan.mvpmindorkssample.ui.tinderCard.VocabularyTrainPresenter;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.correctchoice.BuildWordFragment;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.finish.FinishFragment;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.remember.RememberFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class VocabularyActivity extends BaseActivity implements VocabularyMvpView, FragmentsListener {

    private static final String TAG = "VocabularyActivity";

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<Fragment> fragmentList;
    private int item = 0;
    private VocabularyTrainPresenter presenter;
    private VocabularyAdapter adapter;
    private String[] arr = new String[]{"asds", "Sond", "Hola"};

    @Override
    protected void init(@Nullable Bundle state) {
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

        DataManager manager = ((MvpApp)getApplicationContext()).getDataManager();
        presenter = new VocabularyTrainPresenter(manager);
        presenter.attachView(this);
        presenter.requestForWord("last");
    }



    @Override
    public void sendData(int response) {
        item++;
        if (item>arr.length*3)
            finish();
        viewPager.setCurrentItem(item);

    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_vocabulary;
    }

    @Override
    public void setData(TranslationResponse response) {

        ArrayList<String> fakeArr = new ArrayList<>();
        fakeArr.add("hola");
        fakeArr.add("Laliga");
        fakeArr.add("Cabrone");
        fakeArr.add("last");

        ArrayList<String> fakeTranslateArr = new ArrayList<>();
        fakeTranslateArr.add("саламалейкум");
        fakeTranslateArr.add("уалейкум");
        fakeTranslateArr.add("как дела");
        fakeTranslateArr.add("привет");
        fakeTranslateArr.add("пшел нах");
        fakeTranslateArr.add("последний");

        //Arr must be TranslationResponse list length
        for (int i = 0; i<arr.length; i++){

            fakeArr.remove(response.getWordForms().get(0).getWord());
            Collections.shuffle(fakeArr);
            fakeArr.add(0, response.getWordForms().get(0).getWord());

            fakeTranslateArr.remove(response.getTranslate().get(0).getValue());
            Collections.shuffle(fakeTranslateArr);
            fakeTranslateArr.add(0, response.getTranslate().get(0).getValue());

            Bundle bundle = new Bundle();
            bundle.putParcelable("ed", response);
            bundle.putStringArrayList("fk", new ArrayList<String>(fakeArr.subList(0,4)));

            RememberFragment fragment = new RememberFragment();
            BuildWordFragment fragmentEnglish = new BuildWordFragment();

            fragmentEnglish.setArguments(bundle);
            fragment.setArguments(bundle);
            fragmentList.add(0, fragment);
            fragmentList.add(fragmentEnglish);
        }
        fragmentList.add(new FinishFragment());
        adapter.notifyDataSetChanged();


    }


    @Override
    protected void onDestroy() {
        if (adapter!=null)
            adapter = null;
        if (viewPager!=null)
            viewPager.setAdapter(null);
        presenter.detachView();
        super.onDestroy();
    }
}
