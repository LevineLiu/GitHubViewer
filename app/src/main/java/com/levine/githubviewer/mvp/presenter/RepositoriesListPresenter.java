package com.levine.githubviewer.mvp.presenter;

import com.levine.githubviewer.entity.SearchResultEntity;
import com.levine.githubviewer.mvp.interactor.RepositoriesListInteractor;
import com.levine.githubviewer.mvp.view.ICommonListView;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created on 2017/3/10
 *
 * @author Levine
 */

public class RepositoriesListPresenter extends BasePresenter<ICommonListView<SearchResultEntity>>{
    private RepositoriesListInteractor mInteractor;

    @Inject
    public RepositoriesListPresenter(RepositoriesListInteractor interactor){
        mInteractor = interactor;
    }

    @Override
    public void initialize() {

    }

    public void getRepositoriesList(String keyWord, String sort, String order, final int page, int pageSize){
        mInteractor.createRepositoriesListObservable(keyWord, sort, order, page, pageSize)
                .subscribe(new Observer<SearchResultEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(SearchResultEntity searchResultEntity) {
                        if(mView != null){
                            if(page == 1)
                                mView.onRefreshSuccess(searchResultEntity);
                            else
                                mView.onLoadMoreSuccess(searchResultEntity);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(mView != null){
                            if(page == 1)
                                mView.onRefreshFailure();
                            else
                                mView.onLoadMoreFailure();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
