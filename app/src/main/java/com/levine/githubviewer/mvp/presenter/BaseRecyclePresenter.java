package com.levine.githubviewer.mvp.presenter;

import com.levine.githubviewer.mvp.interactor.BaseInteractor;
import com.levine.githubviewer.mvp.view.ICommonListView;

/**
 * Created on 2017/6/15.
 *
 * @author Levine
 */

public abstract class BaseRecyclePresenter<I extends BaseInteractor, V extends ICommonListView> extends
        BasePresenter<I, V>{
    public abstract void getData(int page, int pageSize);
    public abstract boolean isAutoLoadData();
}
