package com.levine.githubviewer.ui;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.levine.githubviewer.GitHubViewerApplication;
import com.levine.githubviewer.R;
import com.levine.githubviewer.constant.SharedPreferenceConstant;
import com.levine.githubviewer.injector.component.ActivityComponent;
import com.levine.githubviewer.ui.base.BaseAppCompatActivity;
import com.levine.githubviewer.ui.base.BaseFragment;
import com.levine.githubviewer.ui.home.HomeFragment;
import com.levine.githubviewer.ui.mine.MineFragment;
import com.levine.githubviewer.ui.trending.TrendingFragment;
import com.levine.githubviewer.util.FragmentSwitcher;

import butterknife.BindView;

public class MainActivity extends BaseAppCompatActivity {
    private final static int TAB_HOME = 0;
    private final static int TAB_TRENDING = 1;
    private final static int TAB_MINE = 2;

    private FragmentSwitcher mFragmentSwitcher;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mFragmentSwitcher.switchFragment(TAB_HOME);
                    return true;
                case R.id.navigation_trending:
                    mFragmentSwitcher.switchFragment(TAB_TRENDING);
                    return true;
                case R.id.navigation_mine:
                    mFragmentSwitcher.switchFragment(TAB_MINE);
                    return true;


            }
            return false;
        }

    };

    @BindView(R.id.navigation) BottomNavigationView mNavigation;

    @Override
    protected void getBundleExtra(Bundle extra) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle("");
        mFragmentSwitcher = new FragmentSwitcher(this, R.id.content);
        mFragmentSwitcher.addFragment(TAB_MINE, new MineFragment(), FragmentSwitcher.OPERATION_SHOW_HIDE);
        mFragmentSwitcher.addFragment(TAB_TRENDING, new TrendingFragment(), FragmentSwitcher.OPERATION_SHOW_HIDE);
        mFragmentSwitcher.addFragment(TAB_HOME, new HomeFragment(), FragmentSwitcher.OPERATION_SHOW_HIDE);
    }

    @Override
    protected void setupComponent(ActivityComponent component) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * 切换日夜间模式
     */
    public void setDayNightMode(boolean isDayMode){
        saveDayNightMode(isDayMode);
        if(AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.AppTheme_Dark);
            mToolbar.setBackgroundResource(R.color.colorPrimaryNightMode);
            mNavigation.setBackgroundResource(R.color.colorBackgroundDark);
            if(Build.VERSION.SDK_INT >= 21)
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkNightMode));
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            setTheme(R.style.AppTheme_Light);
            mToolbar.setBackgroundResource(R.color.colorPrimary);
            mNavigation.setBackgroundResource(R.color.colorBackground);
            if(Build.VERSION.SDK_INT >= 21)
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        SparseArray fragmentArray = mFragmentSwitcher.getFragmentArray();
        for(int i=0; i<fragmentArray.size(); i++){
            int key = fragmentArray.keyAt(i);
            Fragment fragment = (Fragment) fragmentArray.get(key);
            if(fragment instanceof HomeFragment)
                ((HomeFragment) fragment).setDayNightMode();
            else if(fragment instanceof TrendingFragment)
                ((TrendingFragment) fragment).setDayNightMode();
            else if(fragment instanceof MineFragment)
                ((MineFragment) fragment).setDayNightMode();
        }
    }

    private void saveDayNightMode(boolean isDayMode){
        SharedPreferences sharedPreferences = ((GitHubViewerApplication) getApplication()).getAppComponent().getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SharedPreferenceConstant.IS_DAY_MODE, isDayMode);
        editor.apply();
    }
}
