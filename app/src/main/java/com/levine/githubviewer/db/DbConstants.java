package com.levine.githubviewer.db;

import android.provider.BaseColumns;

/**
 * Created on 2017/2/24
 *
 * @author Levine
 */

class DbConstants {
    final static String DB_NAME = "github_repositories_db";
    final static int DB_VERSION = 1;

    final static String CREATE_FAVORITE_REPOSITORIES_ENTRY = "CREATE TABLE "
            + FavoriteNewsEntry.TABLE_NAME_FAVORITE + " (" + FavoriteNewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FavoriteNewsEntry.REPOSITORIES_ID + " INTEGER,"
            + FavoriteNewsEntry.REPOSITORIES_NAME + " TEXT, " + FavoriteNewsEntry.REPOSITORIES_DESCRIPTION + " TEXT, "
            + FavoriteNewsEntry.REPOSITORIES_LANGUAGE + " TEXT, " + FavoriteNewsEntry.REPOSITORIES_STAR + " INTEGER, "
            + FavoriteNewsEntry.REPOSITORIES_FORK + " INTEGER, " + FavoriteNewsEntry.REPOSITORIES_URL + " TEXT)";

    final static String DELETE_FAVORITE_NEWS_ENTRY = "DROP TABLE IF EXISTS " + FavoriteNewsEntry.TABLE_NAME_FAVORITE;

    static class FavoriteNewsEntry implements BaseColumns {
        final static String TABLE_NAME_FAVORITE = "favorite_repositories";
        final static String REPOSITORIES_ID = "repositories_id";
        final static String REPOSITORIES_NAME = "repositories_name";
        final static String REPOSITORIES_DESCRIPTION = "repositories_description";
        final static String REPOSITORIES_LANGUAGE = "repositories_language";
        final static String REPOSITORIES_STAR = "repositories_star";
        final static String REPOSITORIES_FORK = "repositories_fork";
        final static String REPOSITORIES_URL = "repositories_url";
    }
}
