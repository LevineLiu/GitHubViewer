package com.levine.githubviewer.injector.module;

import android.app.Activity;
import android.content.Context;

import com.levine.githubviewer.injector.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created on 2017/3/8
 *
 * @author Levine
 */
@Module
public class ActivityModule {
    private Context mContext;

    public ActivityModule(Context context){
        mContext = context;
    }

    @ActivityScope
    @Provides
    public Context provideActivityContext(){
        return mContext;
    }
}
