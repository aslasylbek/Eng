package com.example.aslan.mvpmindorkssample.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aslan.mvpmindorkssample.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainActivityView {


    @BindView(R.id.fab) FloatingActionButton mFab;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.nav_view) NavigationView mNavView;
    @BindView(R.id.frameMainFragment)
    FrameLayout mFrameFragment;
    View mHeaderLayout;

    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        setSupportActionBar(mToolbar);
        mToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(mToggle);
        setNavigationView();
        mNavView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public void setHeaderView() {
        //Set Information from local db
        mHeaderLayout = mNavView.getHeaderView(0);
        ImageView mHeaderPhoto = mHeaderLayout.findViewById(R.id.iv_header);
        TextView mHeaderName = mHeaderPhoto.findViewById(R.id.tv_header);
        TextView mHeaderLevel = mHeaderPhoto.findViewById(R.id.tv_sub_header);
        //SetProfile Information
        mHeaderPhoto.setImageResource(R.drawable.leak_canary_icon);
    }

    @Override
    public void setNavigationView() {
        //Have receive some object from internet
        Menu menum = mNavView.getMenu();
        menum
                .add(0, 1, 0, "My English" )
                .setIcon(R.drawable.leak_canary_icon)
                .setCheckable(true)
                .setChecked(true);
        menum
                .add(0, 2, 0, "My Dictionary" )
                .setIcon(R.drawable.leak_canary_icon)
                .setCheckable(true);

        SubMenu subMenu = menum.addSubMenu("Viva");
        subMenu.add(2, 1, 0, "Java").setIcon(R.drawable.leak_canary_icon);


        //Coverage this lines
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frameMainFragment, new SyllabusFragment()).commit();
        }

    @OnClick(R.id.fab)
    public void onFabClicked(View view){
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_settings:
                break;
            case R.id.someExample:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        if (id == 1) {
            fragment = new SyllabusFragment();
        }
        else if(id == 2)
            fragment = new MyDictionaryFragment();


        if (fragment!=null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frameMainFragment, fragment).commit();
        }

        setTitle(item.getTitle());
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
