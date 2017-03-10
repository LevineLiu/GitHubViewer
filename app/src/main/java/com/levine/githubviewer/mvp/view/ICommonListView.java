package com.levine.githubviewer.mvp.view;

/**
 * Created on 16/4/29
 *
 * @author LLW
 */
public interface ICommonListView<T> extends IBaseView<T>{
    void onRefreshSuccess(T result);
    void onRefreshFailure();
    void onLoadMoreSuccess(T result);
    void onLoadMoreFailure();
}
