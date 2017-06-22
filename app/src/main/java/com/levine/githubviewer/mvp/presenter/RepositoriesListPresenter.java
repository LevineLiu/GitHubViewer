package com.levine.githubviewer.mvp.presenter;

import com.levine.githubviewer.entity.RepositoriesEntity;
import com.levine.githubviewer.entity.SearchResultEntity;
import com.levine.githubviewer.http.CustomObserver;
import com.levine.githubviewer.mvp.interactor.RepositoriesListInteractor;
import com.levine.githubviewer.mvp.view.IRepositoriesListView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created on 2017/3/10
 *
 * @author Levine
 */

public class RepositoriesListPresenter extends BaseRecyclePresenter<RepositoriesListInteractor,
        IRepositoriesListView<List<RepositoriesEntity>>>{

    @Inject
    public RepositoriesListPresenter(RepositoriesListInteractor interactor){
        mInteractor = interactor;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void getData(final int page, int pageSize) {
        mInteractor.createRepositoriesListObservable(mView.getKeyword(), mView.getSort(), mView.getOrder(), page,
                pageSize)
                .subscribe(new CustomObserver<SearchResultEntity>() {
                    @Override
                    public void onNext(SearchResultEntity searchResultEntity) {
                        if(mView != null){
                            if(page == 1)
                                mView.onRefreshSuccess(searchResultEntity.getItems());
                            else
                                mView.onLoadMoreSuccess(searchResultEntity.getItems());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(mView != null){
                            if(page == 1)
                                mView.onRefreshFailure();
                            else
                                mView.onLoadMoreFailure();
                        }
                    }
                });
    }

    @Override
    public boolean isAutoLoadData() {
        return false;
    }

}
