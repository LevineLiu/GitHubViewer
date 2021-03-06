package com.levine.githubviewer.injector.module;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.levine.githubviewer.BuildConfig;
import com.levine.githubviewer.GitHubViewerApplication;
import com.levine.githubviewer.api.GitHubApi;
import com.levine.githubviewer.api.GitHubTrendingApi;
import com.levine.githubviewer.util.NetUtils;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 2017/3/8
 *
 * @author Levine
 */
@Module
public class GitHubApiModule {
    @Provides
    public Interceptor provideCachedInterceptor(final GitHubViewerApplication application) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //没有网络强制使用缓存
                if (!NetUtils.isNetWorkAvailable(application)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response originalResponse = chain.proceed(request);
                if (NetUtils.isNetWorkAvailable(application)) {
                    //有网络情况下,不读取缓存
                    int maxAge = 0;
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 7; //最大过时时间一周
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale" + maxStale)
                            .build();
                }
            }
        };
    }

    @Provides
    public OkHttpClient provideOkHttpClient(GitHubViewerApplication application, Interceptor interceptor) {
        File cacheDirectory = new File(application.getCacheDir(), "responses");
        int cacheSize = 5 * 1024 * 1024; // 5Mib
        Cache cache = new Cache(cacheDirectory, cacheSize);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(cache).addInterceptor(interceptor);
        return builder.build();
    }

    @Singleton
    @Provides
    public GitHubApi provideGitHubViewerApi(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.SERVER)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(GitHubApi.class);
    }

    @Singleton
    @Provides
    public GitHubTrendingApi provideTrendingApi(OkHttpClient client){
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.TRENDING_SERVER)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(GitHubTrendingApi.class);
    }
}
