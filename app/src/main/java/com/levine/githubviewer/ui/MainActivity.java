package com.levine.githubviewer.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.levine.githubviewer.R;
import com.levine.githubviewer.ui.base.BaseAppCompatActivity;
import com.levine.githubviewer.ui.home.HomeFragment;
import com.levine.githubviewer.util.FragmentSwitcher;

public class MainActivity extends BaseAppCompatActivity {

    private FragmentSwitcher mFragmentSwitcher;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mFragmentSwitcher.switchFragment(0);
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
        mFragmentSwitcher.addFragment(0, new HomeFragment(), FragmentSwitcher.OPERATION_SHOW_HIDE);
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
