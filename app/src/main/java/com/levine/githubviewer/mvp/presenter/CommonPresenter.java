package com.levine.githubviewer.mvp.presenter;

import com.levine.githubviewer.mvp.view.IBaseView;

import io.reactivex.disposables.Disposable;

/**
 * Created on 16/4/26
 *
 * @author LLW
 */
public abstract class CommonPresenter<V extends IBaseView> {
    protected V mView;
    protected Disposable mDisposable;

    public abstract void initialize();

    public void attachView(V view){
        mView = view;
    }

    public void onDestroyView(){
        mView = null;
        if(mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();
    }
}
