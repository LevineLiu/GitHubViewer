package com.levine.githubviewer.ui.trending;

import android.support.v4.view.ViewPager;

import com.levine.githubviewer.R;
import com.levine.githubviewer.mvp.presenter.TrendingPresenter;
import com.levine.githubviewer.mvp.view.ICommonPagerContainerView;
import com.levine.githubviewer.ui.base.BaseAppCompatActivity;
import com.levine.githubviewer.ui.base.BaseFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created on 2017/3/10
 *
 * @author Levine
 */

public class TrendingFragment extends BaseFragment implements ICommonPagerContainerView{
    @BindView(R.id.stl_fragment_trending)
    SmartTabLayout mSmartTabLayout;
    @BindView(R.id.vp_fragment_trending)
    ViewPager mViewPager;
    @Inject
    TrendingPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trending;
    }

    @Override
    protected void initView() {
        mPresenter.attachView(this);
        mPresenter.initialize();
    }

    @Override
    protected void setupComponent(BaseAppCompatActivity activity) {
        activity.getActivityComponent().inject(this);
    }

    @Override
    public void initPagerView(FragmentPagerItems fragmentPagerItems) {
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getFragmentManager(), fragmentPagerItems);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(fragmentPagerItems.size());
        mSmartTabLayout.setDistributeEvenly(true);
        mSmartTabLayout.setCustomTabView(R.layout.custom_tab_view, R.id.tv_tab_title);
        mSmartTabLayout.setViewPager(mViewPager);

    }
}
