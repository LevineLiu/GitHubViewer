package com.levine.githubviewer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.levine.githubviewer.R;
import com.levine.githubviewer.constant.Constants;
import com.levine.githubviewer.db.DbManager;
import com.levine.githubviewer.entity.RepositoriesEntity;
import com.levine.githubviewer.injector.component.ActivityComponent;
import com.levine.githubviewer.ui.base.BaseAppCompatActivity;
import com.levine.githubviewer.widget.WebViewBrowseView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2017/3/16.
 *
 * @author Levine
 */

public class RepositoriesDetailActivity extends BaseAppCompatActivity{
    private RepositoriesEntity mRepositories;
    private DbManager mDbManager;

    @BindView(R.id.tv_repositories_collect) TextView mCollectTv;
    @BindView(R.id.wv_activity_repositories) WebViewBrowseView mWebView;
    @OnClick({R.id.tv_repositories_share, R.id.tv_repositories_collect})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_repositories_share:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                startActivity(intent);
                break;
            case R.id.tv_repositories_collect:
                if(mDbManager.isRepositoriesCollected(mRepositories.getId())){
                    if(mDbManager.deleteFavoriteRepositories(mRepositories.getId())){
                        mCollectTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable
                                .ic_uncollected, 0, 0, 0);
                    }
                }else{
                    if(mDbManager.insertFavoriteRepositories(mRepositories)){
                        mCollectTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable
                                .ic_collected, 0, 0, 0);
                    }
                }
                break;
        }
    }

    @Override
    protected void getBundleExtra(Bundle extra) {
        mRepositories = extra.getParcelable(Constants.EXTRA_CONTENT);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_repositories_detail;
    }

    @Override
    protected void setupComponent(ActivityComponent component) {

    }

    @Override
    protected void initView() {
        if(mRepositories == null)
            return;
        setDisplayHomeAsUp(mRepositories.getFull_name());
        mWebView.loadUrl(mRepositories.getHtml_url());
        mDbManager = new DbManager(this);
        if(mDbManager.isRepositoriesCollected(mRepositories.getId())){
            mCollectTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable
                    .ic_collected, 0, 0, 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mWebView != null)
            mWebView.release();
    }


    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()){
            mWebView.goBack();
        }else{
            finish();
        }
    }
}
