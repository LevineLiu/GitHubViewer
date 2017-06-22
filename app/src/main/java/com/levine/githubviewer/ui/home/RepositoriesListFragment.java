package com.levine.githubviewer.ui.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.levine.githubviewer.R;
import com.levine.githubviewer.constant.Constants;
import com.levine.githubviewer.constant.ParameterConstants;
import com.levine.githubviewer.entity.RepositoriesEntity;
import com.levine.githubviewer.entity.SearchResultEntity;
import com.levine.githubviewer.listener.OnListItemClickListener;
import com.levine.githubviewer.listener.OnLoadMoreListener;
import com.levine.githubviewer.mvp.presenter.RepositoriesListPresenter;
import com.levine.githubviewer.mvp.view.ICommonListView;
import com.levine.githubviewer.mvp.view.IRepositoriesListView;
import com.levine.githubviewer.ui.RepositoriesDetailActivity;
import com.levine.githubviewer.ui.adapter.RepositoriesListAdapter;
import com.levine.githubviewer.ui.base.BaseAppCompatActivity;
import com.levine.githubviewer.ui.base.BaseFragment;
import com.levine.githubviewer.ui.base.BaseRecycleViewFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created on 2017/3/9
 *
 * @author Levine
 */

public class RepositoriesListFragment extends BaseRecycleViewFragment<RepositoriesEntity,
        RepositoriesListPresenter, RepositoriesListAdapter> implements
        IRepositoriesListView<List<RepositoriesEntity>>{
    public final static String EXTRA_KEYWORD = "extra_keyword";

    private String mKeyword;

    @Override
    protected int getLayoutId() {
        return R.layout.swipe_refresh_layout;
    }

    @Override
    protected void setupComponent(BaseAppCompatActivity activity) {
        activity.getActivityComponent().inject(this);
    }

    @Override
    protected void initAdapter(RecyclerView.LayoutManager layoutManager) {
        mAdapter = new RepositoriesListAdapter(mContext, layoutManager);
    }

    @Override
    protected void initView() {
        super.initView();
        if(getArguments() != null)
            mKeyword = getArguments().getString(EXTRA_KEYWORD);
    }

    @Override
    protected void onFragmentFirstVisible() {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
                mPresenter.getData(mPage, Constants.PAGE_SIZE);
            }
        }, Constants.DELAY_TIME);
    }


    @Override
    public void onItemClick(int position) {
        RepositoriesEntity repositoriesEntity = mAdapter.getData().get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EXTRA_CONTENT, repositoriesEntity);
        navigateTo(RepositoriesDetailActivity.class, bundle);
    }



    @Override
    public String getKeyword() {
        return mKeyword;
    }

    @Override
    public String getSort() {
        return ParameterConstants.SORT.STARS;
    }

    @Override
    public String getOrder() {
        return ParameterConstants.ORDER.DESC;
    }

}
