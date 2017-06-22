package com.levine.githubviewer.injector.component;

import android.content.Context;

import com.levine.githubviewer.injector.ActivityScope;
import com.levine.githubviewer.injector.module.ActivityModule;
import com.levine.githubviewer.ui.MainActivity;
import com.levine.githubviewer.ui.home.RepositoriesListFragment;
import com.levine.githubviewer.ui.home.HomeFragment;
import com.levine.githubviewer.ui.mine.MyFavoriteRepositoriesActivity;
import com.levine.githubviewer.ui.trending.TrendingFragment;
import com.levine.githubviewer.ui.trending.TrendingListFragment;

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
    void inject(HomeFragment fragment);
    void inject(RepositoriesListFragment fragment);
    void inject(TrendingListFragment fragment);
    void inject(TrendingFragment fragment);
    void inject(MyFavoriteRepositoriesActivity activity);
}
