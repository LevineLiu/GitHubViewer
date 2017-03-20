package com.levine.githubviewer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created on 2017/3/20.
 *
 * @author Levine
 */

public class GitHubDbHelper extends SQLiteOpenHelper{
    private static GitHubDbHelper mHelper;

    public static synchronized GitHubDbHelper getInstance(Context context){
        if(mHelper == null)
            mHelper = new GitHubDbHelper(context.getApplicationContext());
        return mHelper;
    }
    private GitHubDbHelper(Context context){
        super(context, DbConstants.DB_NAME, null, DbConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbConstants.CREATE_FAVORITE_REPOSITORIES_ENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DbConstants.DELETE_FAVORITE_NEWS_ENTRY);
        onCreate(db);
    }
}
