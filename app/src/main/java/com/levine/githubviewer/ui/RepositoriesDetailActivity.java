package com.levine.githubviewer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.levine.githubviewer.R;
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
    public final static String EXTRA_REPOSITORIES_NAME = "extra_repositories_name";
    public final static String EXTRA_REPOSITORIES_URL = "extra_repositories_url";
    private String mUrl;
    private String mTitle;

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
                break;
        }
    }

    @Override
    protected void getBundleExtra(Bundle extra) {
        mUrl = extra.getString(EXTRA_REPOSITORIES_URL);
        mTitle = extra.getString(EXTRA_REPOSITORIES_NAME);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_repositories_detail;
    }

    @Override
    protected void initView() {
        setDisplayHomeAsUp(mTitle);
        mWebView.loadUrl(mUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mWebView != null)
            mWebView.release();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.KEYCODE_BACK){
            mWebView.goBack();
            return true;
        }
        else return super.onKeyDown(keyCode, event);
    }
}
