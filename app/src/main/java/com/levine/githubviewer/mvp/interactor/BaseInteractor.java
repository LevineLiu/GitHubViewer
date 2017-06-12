package com.levine.githubviewer.mvp.interactor;

import android.content.Context;

import com.levine.githubviewer.api.GitHubApi;
import com.levine.githubviewer.api.GitHubTrendingApi;

import javax.inject.Inject;

/**
 * Created on 2017/6/6.
 *
 * @author Levine
 */

public class BaseInteractor implements IInteractor{
    protected GitHubApi mApi;
    protected GitHubTrendingApi mTrendingApi;

    public BaseInteractor(){

    }
    public BaseInteractor(GitHubApi api){
        mApi = api;
    }

    public BaseInteractor(GitHubTrendingApi trendingApi){
        mTrendingApi = trendingApi;
    }

    @Override
    public void onDestroy() {
        mApi = null;
    }
}
