package com.levine.githubviewer.ui.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.levine.githubviewer.R;
import com.levine.githubviewer.constant.Constants;
import com.levine.githubviewer.listener.OnListItemClickListener;
import com.levine.githubviewer.listener.OnLoadMoreListener;
import com.levine.githubviewer.mvp.presenter.BaseRecyclePresenter;
import com.levine.githubviewer.mvp.view.ICommonListView;
import com.levine.githubviewer.ui.adapter.BaseRecyclerViewLoadMoreAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created on 2017/6/15.
 *
 * @author Levine
 */

public abstract class BaseRecycleViewActivity<M, P extends BaseRecyclePresenter, A extends
        BaseRecyclerViewLoadMoreAdapter<M>> extends BaseAppCompatActivity<P> implements
        ICommonListView<List<M>>, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener,
        OnListItemClickListener{

    @BindView(R.id.srl_content_common_list)
    protected SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_content_common_list)
    protected RecyclerView mRecyclerView;

    protected A mAdapter;
    protected int mPage = 1;
    protected int mPageSize = Constants.PAGE_SIZE;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_recycleview;
    }


    @Override
    protected void initView() {
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        initAdapter(layoutManager);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnListItemClickListener(this);
        mRecyclerView.addOnScrollListener(mAdapter.getOnScrollListener());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration
                .VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.attachView(this);

        if(mPresenter.isAutoLoadData()){
            mRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(true);
                    mPresenter.getData(mPage, mPageSize);
                }
            }, Constants.DELAY_TIME);

        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected void getBundleExtra(Bundle extra) {

    }

    @Override
    public void onRefresh() {
        mPage = 1;
        mPresenter.getData(mPage, mPageSize);
    }

    @Override
    public void onLoadMore() {
        mRefreshLayout.setRefreshing(false);
        mPage++;
        mPresenter.getData(mPage, mPageSize);
    }

    @Override
    public void onRefreshSuccess(List<M> result) {
        mRefreshLayout.setRefreshing(false);
        mAdapter.setData(result);
        setLoadMoreEnable(result);
    }

    @Override
    public void onRefreshFailure() {
        mRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onLoadMoreSuccess(List<M> result) {
        mRefreshLayout.setRefreshing(false);
        mAdapter.addData(result);
        setLoadMoreEnable(result);
    }

    @Override
    public void onLoadMoreFailure() {
        mRefreshLayout.setRefreshing(false);

    }

    private void setLoadMoreEnable(List<M> list){
        if(list != null && list.size() != 0){
            if(list.size() < Constants.PAGE_SIZE)
                mAdapter.setLoadMoreEnable(false);
            else
                mAdapter.setLoadMoreEnable(true);
        }else
            mAdapter.setLoadMoreEnable(false);
    }

    protected abstract void initAdapter(RecyclerView.LayoutManager layoutManager);
}
