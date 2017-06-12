package com.levine.githubviewer.mvp.presenter;

import com.levine.githubviewer.entity.RepositoriesEntity;
import com.levine.githubviewer.http.CustomObserver;
import com.levine.githubviewer.mvp.interactor.TrendingListInteractor;
import com.levine.githubviewer.mvp.view.ICommonView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created on 2017/3/10
 *
 * @author Levine
 */

public class TrendingListPresenter extends BasePresenter<TrendingListInteractor, ICommonView<List<RepositoriesEntity>>>{

    @Inject
    public TrendingListPresenter(TrendingListInteractor interactor){
        mInteractor = interactor;
    }

    @Override
    public void initialize() {

    }

    public void getTrendingRepositories(String language, String since){
        mInteractor.createTrendingObservable(language, since)
                .subscribe(new CustomObserver<List<RepositoriesEntity>>() {
                    @Override
                    public void onNext(List<RepositoriesEntity> resultEntity) {
                        if(mView != null)
                            mView.onSuccess(resultEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(mView != null)
                            mView.onFailure();
                    }
                });
    }
}
