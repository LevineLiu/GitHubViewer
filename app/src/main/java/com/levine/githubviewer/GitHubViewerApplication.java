package com.levine.githubviewer;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.levine.githubviewer.constant.SharedPreferenceConstant;
import com.levine.githubviewer.injector.component.AppComponent;
import com.levine.githubviewer.injector.component.DaggerAppComponent;
import com.levine.githubviewer.injector.module.AppModule;
import com.levine.githubviewer.injector.module.GitHubApiModule;

/**
 * Created on 2017/3/8
 *
 * @author Levine
 */

public class GitHubViewerApplication extends Application{
    private AppComponent mAppComponent;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mAppComponent = DaggerAppComponent.builder().
                appModule(new AppModule(this)).
                gitHubApiModule(new GitHubApiModule()).
                build();
        if(mAppComponent.getSharedPreferences().getBoolean(SharedPreferenceConstant.IS_DAY_MODE, true))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    public AppComponent getAppComponent(){
        return mAppComponent;
    }

    public static Context getContext(){
        return mContext;
    }
}
