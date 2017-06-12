package com.levine.githubviewer.mvp.presenter;

import com.levine.githubviewer.mvp.interactor.HomeFragmentInteractor;
import com.levine.githubviewer.mvp.view.ICommonPagerContainerView;

import javax.inject.Inject;

/**
 * Created on 2017/3/9
 *
 * @author Levine
 */

public class HomePresenter extends BasePresenter<HomeFragmentInteractor, ICommonPagerContainerView> {

    @Inject
    public HomePresenter(HomeFragmentInteractor interactor){
        mInteractor = interactor;
    }

    @Override
    public void initialize() {
        mView.initPagerView(mInteractor.getPagerFragments());
    }
}
