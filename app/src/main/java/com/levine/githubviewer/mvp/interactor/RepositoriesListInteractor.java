package com.levine.githubviewer.mvp.interactor;

import com.levine.githubviewer.api.GitHubApi;
import com.levine.githubviewer.entity.SearchResultEntity;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created on 2017/3/10
 *
 * @author Levine
 */

public class RepositoriesListInteractor {
    private GitHubApi mApi;

    @Inject
    public RepositoriesListInteractor(GitHubApi api){
        mApi = api;
    }

    public Observable<SearchResultEntity> createRepositoriesListObservable(String keyWord, String sort, String order, int page, int pageSize){
        return mApi.searchRepositories(keyWord, sort, order, page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
