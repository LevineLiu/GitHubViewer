package com.levine.githubviewer.api;

import com.levine.githubviewer.entity.RepositoriesEntity;
import com.levine.githubviewer.entity.SearchResultEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created on 2017/3/10
 *
 * @author Levine
 */

public interface GitHubTrendingApi {

    @GET("trending?")
    Observable<List<RepositoriesEntity>> getTrendingRepositories(
            @Query("language") String language,
            @Query("since") String since
    );
}
