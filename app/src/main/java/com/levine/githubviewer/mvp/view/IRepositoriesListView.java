package com.levine.githubviewer.mvp.view;

/**
 * Created on 2017/6/20.
 *
 * @author Levine
 */

public interface IRepositoriesListView<T> extends ICommonListView<T>{
    String getKeyword();
    String getSort();
    String getOrder();
}
