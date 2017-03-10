package com.levine.githubviewer.ui.trending;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.levine.githubviewer.R;
import com.levine.githubviewer.constant.Constants;
import com.levine.githubviewer.entity.RepositoriesEntity;
import com.levine.githubviewer.listener.OnListItemClickListener;
import com.levine.githubviewer.mvp.presenter.TrendingListPresenter;
import com.levine.githubviewer.mvp.view.ICommonView;
import com.levine.githubviewer.ui.adapter.RepositoriesListAdapter;
import com.levine.githubviewer.ui.base.BaseAppCompatActivity;
import com.levine.githubviewer.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created on 2017/3/9
 *
 * @author Levine
 */

public class TrendingListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        OnListItemClickListener, ICommonView<List<RepositoriesEntity>>{
    public final static String TIME_SPAN = "time_span";

    private RepositoriesListAdapter mAdapter;
    private String mTimeSpan;

    @BindView(R.id.srl_content_common_list) SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_content_common_list) RecyclerView mRecyclerView;
    @Inject
    TrendingListPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.swipe_refresh_layout;
    }

    @Override
    protected void initView() {
        mTimeSpan = getArguments().getString(TIME_SPAN);
        mRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mAdapter = new RepositoriesListAdapter(mContext, layoutManager);
        mRecyclerView.addOnScrollListener(mAdapter.getOnScrollListener());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.attachView(this);
    }

    @Override
    protected void onFragmentFirstVisible() {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
                mPresenter.getTrendingRepositories("", mTimeSpan);
            }
        }, Constants.DELAY_TIME);
    }

    @Override
    protected void setupComponent(BaseAppCompatActivity activity) {
        activity.getActivityComponent().inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mPresenter != null)
            mPresenter.onDestroyView();
    }

    @Override
    public void onRefresh() {
        mPresenter.getTrendingRepositories("", mTimeSpan);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onSuccess(List<RepositoriesEntity> result) {
        mRefreshLayout.setRefreshing(false);
        mAdapter.addData(result);
    }

    @Override
    public void onFailure() {
        mRefreshLayout.setRefreshing(false);
    }
}
