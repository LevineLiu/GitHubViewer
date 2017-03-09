package com.levine.githubviewer.ui.home;

import android.support.v4.view.ViewPager;

import com.levine.githubviewer.R;
import com.levine.githubviewer.ui.base.BaseFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;

/**
 * Created on 2017/3/9
 *
 * @author Levine
 */

public class HomeFragment extends BaseFragment{

    @BindView(R.id.stl_fragment_home) SmartTabLayout mSmartTabLayout;
    @BindView(R.id.vp_fragment_home) ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

    }
}
