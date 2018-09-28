package com.uibenglish.aslan.mvpmindorkssample.ui.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.uibenglish.aslan.mvpmindorkssample.MvpApp;
import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Info;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BaseActivity;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Topic;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.syllabus.SyllabusFragment;
import com.uibenglish.aslan.mvpmindorkssample.ui.splash.SplashActivity;
import com.uibenglish.aslan.mvpmindorkssample.ui.user_result.UserResultFragment;
import com.uibenglish.aslan.mvpmindorkssample.ui.word_wallet.WordBookFragment;
import com.uibenglish.aslan.mvpmindorkssample.ui.word_wallet.WordBookMainFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class Main2Activity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainMvpView {


    private static final String TAG = "Main2Activity";
    public final static String BROADCAST_ACTION = "ru.startandroid.develop.p0961servicebackbroadcast";

    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.frameMainFragment)
    FrameLayout mFrameFragment;

    private Main2Presenter presenter;
    private ActionBarDrawerToggle mToggle;
    private FragmentManager mFragmentManager;
    private List<Topic> topicList;
    private AlarmManager am;


    public static Intent getStartIntent(Context context) {
        return new Intent(context, Main2Activity.class);
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_main2;
    }

    @Override
    protected void init(@Nullable Bundle state) {
        setSupportActionBar(mToolbar);
        mFragmentManager = getSupportFragmentManager();
        mToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        topicList = new ArrayList<>();
        mNavView.setNavigationItemSelectedListener(this);
        DataManager manager = ((MvpApp) getApplication()).getDataManager();
        presenter = new Main2Presenter(manager);
        presenter.attachView(this);
        presenter.requestForStudentDiscipline();

        presenter.sendDeviceToken();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public void setHeaderView(Info information) {
        Log.d(TAG, "setHeaderView: ");
        //Set Information from local db
        View mHeaderLayout = mNavView.getHeaderView(0);
        TextView mHeaderName = mHeaderLayout.findViewById(R.id.tv_header_fio);
        TextView mHeaderGroup = mHeaderLayout.findViewById(R.id.tv_group);
        TextView mHeaderLevel = mHeaderLayout.findViewById(R.id.tv_sub_header);
        //SetProfile Information
        mHeaderName.setText(information.getFio());
        mHeaderLevel.setText(information.getProgram());
        mHeaderGroup.setText(information.getGroup());

        mNavView.setCheckedItem(R.id.category_dashboard);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameMainFragment, new SyllabusFragment()).commit();
        setTitle(R.string.category_dashboard);

    }

    @Override
    public void setNavigationView() {
        /*//Have receive some object from internet
        Menu menum = mNavView.getMenu();
        menum
                .add(0, 1, 0, "Dashboard")
                .setIcon(R.drawable.ic_dashboard_black_24dp)
                .setCheckable(true)
                .setChecked(true);
        menum
                .add(0, 2, 0, "My Dictionary")
                .setIcon(R.drawable.ic_word_book)
                .setCheckable(true);


        SubMenu subMenu = menum.addSubMenu("Settings");
        subMenu.add(2, 10, 0, "Sign out").setIcon(R.drawable.ic_error_outline);

        mNavView.setCheckedItem(1);*/
    }

    @Override
    public List<Topic> setHolderData(List<Topic> topics) {
        topicList = topics;
        return topicList;
    }

    @OnClick(R.id.fab)
    public void onFabClicked(View view) {
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
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.my_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameMainFragment, UserResultFragment.newInstance()).commit();
                setTitle(R.string.my_profile);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        int groupId = item.getGroupId();

        Intent intent;
        FragmentManager fm;
        FragmentTransaction ft;
        Fragment newFragment;
        switch (id) {
            case R.id.category_dashboard:
                fm = getSupportFragmentManager();
                newFragment = new SyllabusFragment();
                fm.beginTransaction().replace(R.id.frameMainFragment,
                            newFragment)
                            .commit();
                break;
            case R.id.custom_word_book:
                ft = getSupportFragmentManager().beginTransaction();
                newFragment = new WordBookMainFragment();
                ft.replace(R.id.frameMainFragment, newFragment).commit();
                break;
            case R.id.sign_out:
                presenter.setUserLogOut();
                break;
            default:
                return false;
        }

        setTitle(item.getTitle());
        if (mDrawerLayout!=null)
            mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void openSlashActivity() {
        Intent intent = SplashActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

}
