package com.levine.githubviewer.ui.home;

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

public class RepositoriesListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        OnLoadMoreListener, OnListItemClickListener, ICommonListView<SearchResultEntity>{
    public final static String EXTRA_KEYWORD = "extra_keyword";

    private RepositoriesListAdapter mAdapter;
    private String mKeyword;
    private int mPage = 1;

    @BindView(R.id.srl_content_common_list) SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_content_common_list) RecyclerView mRecyclerView;
    @Inject RepositoriesListPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.swipe_refresh_layout;
    }

    @Override
    protected void setupComponent(BaseAppCompatActivity activity) {
        activity.getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        if(getArguments() != null)
            mKeyword = getArguments().getString(EXTRA_KEYWORD);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeColors(mContext.getResources().getColor(R.color.colorPrimary));
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mAdapter = new RepositoriesListAdapter(mContext, layoutManager);
        mAdapter.setOnLoadMoreListener(this);
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
                mPresenter.getRepositoriesList(mKeyword, ParameterConstants.SORT.STARS,
                        ParameterConstants.ORDER.DESC, mPage, Constants.PAGE_SIZE);
            }
        }, Constants.DELAY_TIME);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mPresenter != null)
            mPresenter.onDestroyView();
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        mPresenter.getRepositoriesList(mKeyword, ParameterConstants.SORT.STARS,
                ParameterConstants.ORDER.DESC, mPage, Constants.PAGE_SIZE);
    }

    @Override
    public void onLoadMore() {
        mRefreshLayout.setRefreshing(false);
        mPage++;
        mPresenter.getRepositoriesList(mKeyword, ParameterConstants.SORT.STARS,
                ParameterConstants.ORDER.DESC, mPage, Constants.PAGE_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onRefreshSuccess(SearchResultEntity result) {
        mRefreshLayout.setRefreshing(false);
        List<RepositoriesEntity> list = result.getItems();
        mAdapter.setData(list);
        setLoadMoreEnable(list);
    }

    @Override
    public void onRefreshFailure() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreSuccess(SearchResultEntity result) {
        List<RepositoriesEntity> list = result.getItems();
        mAdapter.addData(list);
        setLoadMoreEnable(list);
    }

    @Override
    public void onLoadMoreFailure() {

    }

    private void setLoadMoreEnable(List<RepositoriesEntity> list){
        if(list != null && list.size() != 0){
            if(list.size() < Constants.PAGE_SIZE)
                mAdapter.setLoadMoreEnable(false);
            else
                mAdapter.setLoadMoreEnable(true);
        }else
            mAdapter.setLoadMoreEnable(false);
    }
}
