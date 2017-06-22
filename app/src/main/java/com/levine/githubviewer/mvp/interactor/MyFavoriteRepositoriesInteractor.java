package com.levine.githubviewer.mvp.interactor;

import android.content.Context;

import com.levine.githubviewer.db.DbManager;
import com.levine.githubviewer.entity.RepositoriesEntity;

import java.util.List;

import javax.inject.Inject;

/**
 * Created on 2017/6/15.
 *
 * @author Levine
 */

public class MyFavoriteRepositoriesInteractor extends BaseInteractor{
    private DbManager mDbManager;

    @Inject
    public MyFavoriteRepositoriesInteractor(Context context){
        mDbManager = new DbManager(context);
    }

    public List<RepositoriesEntity> getMyFavoriteList(int startIndex, int endIndex){
        return mDbManager.getFavoriteRepositoriesList(startIndex, endIndex);
    }
}
