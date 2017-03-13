package com.levine.githubviewer.ui.home;

import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.levine.githubviewer.R;
import com.levine.githubviewer.mvp.presenter.HomePresenter;
import com.levine.githubviewer.mvp.view.ICommonPagerContainerView;
import com.levine.githubviewer.ui.base.BaseAppCompatActivity;
import com.levine.githubviewer.ui.base.BaseFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 2017/3/9
 *
 * @author Levine
 */

public class HomeFragment extends BaseFragment implements ICommonPagerContainerView{
    private FragmentPagerItemAdapter mAdapter;

    @BindView(R.id.stl_tab_layout) SmartTabLayout mSmartTabLayout;
    @BindView(R.id.vp_tab_layout) ViewPager mViewPager;
    @Inject HomePresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
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
            TextView textView = (TextView) mSmartTabLayout.getTabAt(i);
            textView.setTextColor(getResources().getColor(textColor.resourceId));
            ((RepositoriesListFragment) mAdapter.getPage(i)).setDayNightMode();
        }

    }
}
