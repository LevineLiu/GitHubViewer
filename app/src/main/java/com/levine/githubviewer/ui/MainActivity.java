package com.levine.githubviewer.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.levine.githubviewer.R;
import com.levine.githubviewer.ui.base.BaseAppCompatActivity;
import com.levine.githubviewer.ui.home.HomeFragment;
import com.levine.githubviewer.ui.mine.MineFragment;
import com.levine.githubviewer.ui.trending.TrendingFragment;
import com.levine.githubviewer.util.FragmentSwitcher;

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

    @Override
    protected void getBundleExtra(Bundle extra) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle("");
        mFragmentSwitcher = new FragmentSwitcher(this, R.id.content);
        mFragmentSwitcher.addFragment(TAB_TRENDING, new TrendingFragment(), FragmentSwitcher.OPERATION_SHOW_HIDE);
        mFragmentSwitcher.addFragment(TAB_HOME, new HomeFragment(), FragmentSwitcher.OPERATION_SHOW_HIDE);
        mFragmentSwitcher.addFragment(TAB_MINE, new MineFragment(), FragmentSwitcher.OPERATION_SHOW_HIDE);
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
}
