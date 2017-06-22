package com.levine.githubviewer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.levine.githubviewer.entity.RepositoriesEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/6/14.
 *
 * @author Levine
 */

public class DbManager {
    private SQLiteDatabase mSqlDb;

    public DbManager(Context context){
        mSqlDb = GitHubDbHelper.getInstance(context).getReadableDatabase();
    }

    public boolean isRepositoriesCollected(int id){
        String sql = "select* from " + DbConstants.FavoriteNewsEntry.TABLE_NAME_FAVORITE + " where " +
                DbConstants.FavoriteNewsEntry.REPOSITORIES_ID + "=" +id;
        Cursor cursor = mSqlDb.rawQuery(sql, null);
        if(cursor.getCount() == 0){
            cursor.close();
            return false;
        }
        else {
            cursor.close();
            return true;
        }
    }

    public boolean deleteFavoriteRepositories(int id){
        long result = mSqlDb.delete(DbConstants.FavoriteNewsEntry.TABLE_NAME_FAVORITE,
                DbConstants.FavoriteNewsEntry.REPOSITORIES_ID + "=?", new String[]{String.valueOf(id)});
        return result != 0;
    }

    public boolean insertFavoriteRepositories(RepositoriesEntity entity){
        if(entity == null)
            return false;

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbConstants.FavoriteNewsEntry.REPOSITORIES_ID, entity.getId());
        contentValues.put(DbConstants.FavoriteNewsEntry.REPOSITORIES_NAME, entity.getFull_name());
        contentValues.put(DbConstants.FavoriteNewsEntry.REPOSITORIES_DESCRIPTION, entity.getDescription());
        contentValues.put(DbConstants.FavoriteNewsEntry.REPOSITORIES_LANGUAGE, entity.getLanguage());
        contentValues.put(DbConstants.FavoriteNewsEntry.REPOSITORIES_STAR, entity.getStargazers_count());
        contentValues.put(DbConstants.FavoriteNewsEntry.REPOSITORIES_FORK, entity.getForks_count());
        contentValues.put(DbConstants.FavoriteNewsEntry.REPOSITORIES_URL, entity.getHtml_url());

        long result = mSqlDb.insert(DbConstants.FavoriteNewsEntry.TABLE_NAME_FAVORITE, null,
                contentValues);
        return result != -1;
    }

    public List<RepositoriesEntity> getFavoriteRepositoriesList(){
        Cursor cursor = mSqlDb.rawQuery("select* from " + DbConstants.FavoriteNewsEntry
                .TABLE_NAME_FAVORITE, null);
        List<RepositoriesEntity> list = new ArrayList<>();
        while (cursor.moveToNext()){
            list.add(getRepositoriesEntityByCursor(cursor));
        }
        cursor.close();
        return list;
    }

    public List<RepositoriesEntity> getFavoriteRepositoriesList(int startIndex, int endIndex){
        Cursor cursor = mSqlDb.rawQuery("select* from " + DbConstants.FavoriteNewsEntry
                .TABLE_NAME_FAVORITE +
                " where " + DbConstants.FavoriteNewsEntry._ID + " >=? and " +
                DbConstants.FavoriteNewsEntry._ID + " <=? ", new String[]{String.valueOf(startIndex), String.valueOf(endIndex)});
        List<RepositoriesEntity> list = new ArrayList<>();
        while (cursor.moveToNext()){
            list.add(getRepositoriesEntityByCursor(cursor));
        }
        cursor.close();
        return list;
    }


    private RepositoriesEntity getRepositoriesEntityByCursor(Cursor cursor){
        RepositoriesEntity entity = new RepositoriesEntity();
        entity.setId(cursor.getInt(cursor.getColumnIndex(DbConstants.FavoriteNewsEntry.REPOSITORIES_ID)));
        entity.setFull_name(cursor.getString(cursor.getColumnIndex(DbConstants.FavoriteNewsEntry
                .REPOSITORIES_NAME)));
        entity.setDescription(cursor.getString(cursor.getColumnIndex(DbConstants
                .FavoriteNewsEntry.REPOSITORIES_DESCRIPTION)));
        entity.setStargazers_count(cursor.getInt(cursor.getColumnIndex(DbConstants
                .FavoriteNewsEntry.REPOSITORIES_STAR)));
        entity.setLanguage(cursor.getString(cursor.getColumnIndex(DbConstants
                .FavoriteNewsEntry.REPOSITORIES_LANGUAGE)));
        entity.setHtml_url(cursor.getString(cursor.getColumnIndex(DbConstants
                .FavoriteNewsEntry.REPOSITORIES_URL)));

        return entity;
    }
}
