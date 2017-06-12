package com.levine.githubviewer.mvp.view;


/**
 * Created on 2017/1/13
 *
 * @author LLW
 */

public interface ICommonView<T> extends IBaseView{
    void onSuccess(T result);
    void onFailure();
}
