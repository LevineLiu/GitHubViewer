package com.levine.githubviewer.injector.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.levine.githubviewer.GitHubViewerApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created on 2017/3/8
 *
 * @author Levine
 */
@Module
public class AppModule {
    private GitHubViewerApplication mApplication;
    private final static String SHARED_PREFERENCE_NAME = "ZhiHuDailySharedPreference";

    public AppModule(GitHubViewerApplication application){
        mApplication = application;
    }

    @Singleton
    @Provides
    public GitHubViewerApplication provideApplicationContext(){
        return mApplication;
    }

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences(){
        return mApplication.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

}
