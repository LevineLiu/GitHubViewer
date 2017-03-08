package com.levine.githubviewer.injector.component;

import android.content.Context;

import com.levine.githubviewer.injector.ActivityScope;
import com.levine.githubviewer.injector.module.ActivityModule;

import dagger.Component;

/**
 * Created on 2017/3/8
 *
 * @author Levine
 */
@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {
    Context getContext();
}
