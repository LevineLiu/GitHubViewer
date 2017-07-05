package com.levine.githubviewer.provider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created on 2017/7/4.
 *
 * @author Levine
 */

public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider{
    public final static String AUTHORITY = "com.levine.githubviewer.SearchSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SearchSuggestionProvider(){
        setupSuggestions(AUTHORITY, MODE);
    }

}
