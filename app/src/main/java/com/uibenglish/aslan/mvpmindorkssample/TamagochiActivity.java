package com.uibenglish.aslan.mvpmindorkssample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

public class TamagochiActivity extends AppCompatActivity {


    @BindView(R.id.imageView)
    ImageView imageTree;

    @BindView(R.id.btnFeed)
    Button btnFeed;

    @BindView(R.id.tvLabel)
    TextView tvLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamagochi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        imageTree.setImageResource(R.drawable.tree_one);


    }

    @OnClick(R.id.btnFeed)
    public void onFeedClick(){
        animateView(imageTree);
    }

    public void animateView(View view){
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_tree);
        view.startAnimation(shake);
    }

}
