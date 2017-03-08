package com.levine.githubviewer.injector.component;

import android.content.SharedPreferences;

import com.levine.githubviewer.GitHubViewerApplication;
import com.levine.githubviewer.api.GitHubApi;
import com.levine.githubviewer.injector.module.AppModule;
import com.levine.githubviewer.injector.module.GitHubApiModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created on 2017/3/8
 *
 * @author Levine
 */
@Singleton
@Component(modules = {GitHubApiModule.class, AppModule.class})
public interface AppComponent {
    GitHubApi getGitHubAPi();
    GitHubViewerApplication getApplication();
    SharedPreferences getSharedPreferences();
}
