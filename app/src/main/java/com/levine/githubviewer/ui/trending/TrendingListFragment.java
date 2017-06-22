package com.levine.githubviewer.ui.trending;

import android.os.Bundle;
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
import com.levine.githubviewer.mvp.view.ITrendingListView;
import com.levine.githubviewer.ui.RepositoriesDetailActivity;
import com.levine.githubviewer.ui.adapter.RepositoriesListAdapter;
import com.levine.githubviewer.ui.base.BaseAppCompatActivity;
import com.levine.githubviewer.ui.base.BaseFragment;
import com.levine.githubviewer.ui.base.BaseRecycleViewActivity;
import com.levine.githubviewer.ui.base.BaseRecycleViewFragment;

import java.util.List;

import butterknife.BindView;

/**
 * Created on 2017/3/9
 *
 * @author Levine
 */

public class TrendingListFragment extends BaseRecycleViewFragment<RepositoriesEntity, TrendingListPresenter,
        RepositoriesListAdapter> implements ITrendingListView<List<RepositoriesEntity>>{

    private int mPosition;

    @Override
    protected void initView() {
        super.initView();
        mPosition = getArguments().getInt(Constants.EXTRA_CONTENT);
    }

    @Override
    protected void onFragmentFirstVisible() {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
                mPresenter.getData(0, Constants.PAGE_SIZE);
            }
        }, Constants.DELAY_TIME);
    }

    @Override
    protected void setupComponent(BaseAppCompatActivity activity) {
        activity.getActivityComponent().inject(this);
    }



    @Override
    public void onItemClick(int position) {
        RepositoriesEntity repositoriesEntity = mAdapter.getData().get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EXTRA_CONTENT, repositoriesEntity);
        navigateTo(RepositoriesDetailActivity.class, bundle);
    }

    @Override
    protected void initAdapter(RecyclerView.LayoutManager layoutManager) {
        mAdapter = new RepositoriesListAdapter(mContext, layoutManager);
    }

    @Override
    public int getPosition() {
        return mPosition;
    }

    @Override
    public void onRefreshSuccess(List<RepositoriesEntity> result) {
        super.onRefreshSuccess(result);
        mAdapter.setLoadMoreEnable(false);
    }

    @Override
    public void onRefreshFailure() {
        super.onRefreshFailure();
        mAdapter.setLoadMoreEnable(false);
    }
}
