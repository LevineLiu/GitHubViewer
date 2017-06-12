package com.levine.githubviewer.mvp.presenter;

import com.levine.githubviewer.mvp.interactor.BaseInteractor;
import com.levine.githubviewer.mvp.view.IBaseView;

import io.reactivex.disposables.Disposable;

/**
 * Created on 16/4/26
 *
 * @author LLW
 */
public abstract class BasePresenter<I extends BaseInteractor, V extends IBaseView> {
    protected I mInteractor;
    protected V mView;

    protected void initialize(){}

    public void attachView(V view){
        mView = view;
    }

    public void onDestroyView(){
        mView = null;
        if(mInteractor != null){
            mInteractor.onDestroy();
        }
    }
}
