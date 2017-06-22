package com.levine.githubviewer.ui.mine;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.levine.githubviewer.R;
import com.levine.githubviewer.constant.Constants;
import com.levine.githubviewer.entity.RepositoriesEntity;
import com.levine.githubviewer.injector.component.ActivityComponent;
import com.levine.githubviewer.mvp.presenter.MyFavoriteRepositoriesPresenter;
import com.levine.githubviewer.ui.RepositoriesDetailActivity;
import com.levine.githubviewer.ui.adapter.RepositoriesListAdapter;
import com.levine.githubviewer.ui.base.BaseRecycleViewActivity;

import java.util.List;


/**
 * Created on 2017/6/15.
 *
 * @author Levine
 */

public class MyFavoriteRepositoriesActivity extends BaseRecycleViewActivity<RepositoriesEntity,
        MyFavoriteRepositoriesPresenter, RepositoriesListAdapter>{

    private int mStartIndex;
    private int mEndIndex;

    @Override
    protected void setupComponent(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setDisplayHomeAsUp(getString(R.string.my_favorite));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mStartIndex = 0;
                mEndIndex = Constants.PAGE_SIZE;
                mPresenter.loadData(mStartIndex, mEndIndex);
            }
        }, Constants.DELAY_TIME);
    }

    @Override
    protected void initAdapter(RecyclerView.LayoutManager layoutManager) {
        mAdapter = new RepositoriesListAdapter(this, layoutManager);
    }

    @Override
    public void onRefresh() {
        mStartIndex = 0;
        mEndIndex = Constants.PAGE_SIZE;
        mPresenter.loadData(mStartIndex, mEndIndex);
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadData(mEndIndex + 1, mEndIndex + Constants.PAGE_SIZE);
    }

    @Override
    public void onRefreshSuccess(List<RepositoriesEntity> result) {
        super.onRefreshSuccess(result);
        if(result != null){
            setIndex();
        }
    }

    @Override
    public void onLoadMoreSuccess(List<RepositoriesEntity> result) {
        super.onLoadMoreSuccess(result);
        if(result != null){
            setIndex();
        }
    }

    @Override
    public void onItemClick(int position) {
        RepositoriesEntity repositoriesEntity = mAdapter.getData().get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EXTRA_CONTENT, repositoriesEntity);
        navigateTo(RepositoriesDetailActivity.class, bundle);
    }

    private void setIndex(){
        mStartIndex = mEndIndex + 1;
        mEndIndex = mEndIndex + Constants.PAGE_SIZE;
    }
}
