package com.levine.githubviewer.mvp.presenter;

import com.levine.githubviewer.mvp.interactor.TrendingInteractor;
import com.levine.githubviewer.mvp.view.ICommonPagerContainerView;

import javax.inject.Inject;

/**
 * Created on 2017/3/10
 *
 * @author Levine
 */

public class TrendingPresenter extends BasePresenter<TrendingInteractor, ICommonPagerContainerView>{

    @Inject
    public TrendingPresenter(TrendingInteractor interactor){
        mInteractor = interactor;
    }

    @Override
    public void initialize() {
        mView.initPagerView(mInteractor.getPagerFragments());
    }
}
