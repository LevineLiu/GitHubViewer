package com.levine.githubviewer.mvp.presenter;

import com.levine.githubviewer.entity.RepositoriesEntity;
import com.levine.githubviewer.http.CustomObserver;
import com.levine.githubviewer.mvp.interactor.TrendingListInteractor;
import com.levine.githubviewer.mvp.view.ICommonView;
import com.levine.githubviewer.mvp.view.ITrendingListView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created on 2017/3/10
 *
 * @author Levine
 */

public class TrendingListPresenter extends BaseRecyclePresenter<TrendingListInteractor,
        ITrendingListView<List<RepositoriesEntity>>>{

    @Inject
    public TrendingListPresenter(TrendingListInteractor interactor){
        mInteractor = interactor;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void getData(int page, int pageSize) {
        String since = "daily";
        switch (mView.getPosition()){
            case 0:
                since = "daily";
                break;
            case 1:
                since = "weekly";
                break;
            case 2:
                since = "monthly";
                break;
        }
        mInteractor.createTrendingObservable("", since)
                .subscribe(new CustomObserver<List<RepositoriesEntity>>() {
                    @Override
                    public void onNext(List<RepositoriesEntity> resultEntity) {
                        if(mView != null)
                            mView.onRefreshSuccess(resultEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(mView != null)
                            mView.onRefreshFailure();
                    }
                });
    }

    @Override
    public boolean isAutoLoadData() {
        return false;
    }

}
