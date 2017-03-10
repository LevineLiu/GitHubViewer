package com.levine.githubviewer.api;

import com.levine.githubviewer.entity.SearchResultEntity;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created on 2017/3/8
 *
 * @author Levine
 */

public interface GitHubApi {

    @GET("/search/repositories?")
    Observable<SearchResultEntity> searchRepositories(
            @Query("q") String q,
            @Query("sort") String sort,
            @Query("order") String order,
            @Query("page") int page,
            @Query("per_page") int per_page
    );
}
