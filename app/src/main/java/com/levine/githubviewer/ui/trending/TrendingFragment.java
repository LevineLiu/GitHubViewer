package com.levine.githubviewer.ui.trending;

import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.levine.githubviewer.R;
import com.levine.githubviewer.mvp.presenter.TrendingPresenter;
import com.levine.githubviewer.mvp.view.ICommonPagerContainerView;
import com.levine.githubviewer.ui.base.BaseAppCompatActivity;
import com.levine.githubviewer.ui.base.BaseFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;


import butterknife.BindView;

/**
 * Created on 2017/3/10
 *
 * @author Levine
 */

public class TrendingFragment extends BaseFragment<TrendingPresenter> implements ICommonPagerContainerView{
    private FragmentPagerItemAdapter mAdapter;

    @BindView(R.id.stl_fragment_trending)
    SmartTabLayout mSmartTabLayout;
    @BindView(R.id.vp_fragment_trending)
    ViewPager mViewPager;

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
        mAdapter = new FragmentPagerItemAdapter(getFragmentManager(), fragmentPagerItems);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(fragmentPagerItems.size());
        mSmartTabLayout.setDistributeEvenly(true);
        mSmartTabLayout.setCustomTabView(R.layout.custom_tab_view, R.id.tv_tab_title);
        mSmartTabLayout.setViewPager(mViewPager);
    }

    /**
     * 设置日夜间模式
     */
    public void setDayNightMode(){
        if(getView() == null)
            return;
        TypedValue background = new TypedValue();
        TypedValue textColor = new TypedValue();
        TypedValue cardBackground = new TypedValue();
        TypedValue colorPrimary = new TypedValue();
        Resources.Theme theme = mContext.getTheme();
        theme.resolveAttribute(R.attr.backgroundColor, background, true);
        theme.resolveAttribute(R.attr.primaryTextColor, textColor, true);
        theme.resolveAttribute(R.attr.backgroundCardColor, cardBackground, true);
        theme.resolveAttribute(R.attr.colorPrimary, colorPrimary, true);

        mSmartTabLayout.setBackgroundResource(background.resourceId);
        int count = mViewPager.getChildCount();
        for(int i=0; i<count; i++){
            ((TrendingListFragment) mAdapter.getPage(i)).setDayNightMode();
        }
    }
}
