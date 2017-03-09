package com.levine.githubviewer.ui.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.levine.githubviewer.R;
import com.levine.githubviewer.ui.base.BaseFragment;

import butterknife.BindView;

/**
 * Created on 2017/3/9
 *
 * @author Levine
 */

public class CommonRepositoriesListFragment extends BaseFragment{

    @BindView(R.id.srl_content_common_list) SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_content_common_list) RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.swipe_refresh_layout;
    }

    @Override
    protected void initView() {

    }
}
