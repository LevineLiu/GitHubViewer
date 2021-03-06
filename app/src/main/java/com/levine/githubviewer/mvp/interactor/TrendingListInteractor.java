package com.levine.githubviewer.mvp.interactor;

import com.levine.githubviewer.api.GitHubTrendingApi;
import com.levine.githubviewer.entity.RepositoriesEntity;
import com.levine.githubviewer.entity.SearchResultEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created on 2017/3/10
 *
 * @author Levine
 */

public class TrendingListInteractor extends BaseInteractor{

    @Inject
    public TrendingListInteractor(GitHubTrendingApi api){
        super(api);
    }

    public Observable<List<RepositoriesEntity>> createTrendingObservable(String language, String since){
        return mTrendingApi.getTrendingRepositories(language, since)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
