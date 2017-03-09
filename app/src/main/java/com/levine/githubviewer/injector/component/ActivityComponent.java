package com.levine.githubviewer.injector.component;

import android.content.Context;

import com.levine.githubviewer.injector.ActivityScope;
import com.levine.githubviewer.injector.module.ActivityModule;
import com.levine.githubviewer.ui.MainActivity;
import com.levine.githubviewer.ui.home.CommonRepositoriesListFragment;
import com.levine.githubviewer.ui.home.HomeFragment;

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
    void inject(MainActivity activity);
    void inject(HomeFragment fragment);
    void inject(CommonRepositoriesListFragment fragment);
}
