package com.levine.githubviewer.mvp.presenter;

import com.levine.githubviewer.constant.Constants;
import com.levine.githubviewer.entity.RepositoriesEntity;
import com.levine.githubviewer.mvp.interactor.MyFavoriteRepositoriesInteractor;
import com.levine.githubviewer.mvp.view.ICommonListView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created on 2017/6/15.m
 *
 * @author Levine
 */

public class MyFavoriteRepositoriesPresenter extends BaseRecyclePresenter<MyFavoriteRepositoriesInteractor,
                ICommonListView<List<RepositoriesEntity>>>{

    @Inject
    public MyFavoriteRepositoriesPresenter(MyFavoriteRepositoriesInteractor interactor){
        mInteractor = interactor;
    }


    @Override
    public void getData(int page, int pageSize) {

    }

    @Override
    public boolean isAutoLoadData() {
        return false;
    }

    public void loadData(int startIndex, int endIndex){
        List<RepositoriesEntity> result = mInteractor.getMyFavoriteList(startIndex, endIndex);
        if(startIndex == 0){
            mView.onRefreshSuccess(result);
        }else{
            mView.onLoadMoreSuccess(result);
        }
    }
}
