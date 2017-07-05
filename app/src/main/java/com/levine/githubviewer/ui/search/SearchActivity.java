package com.levine.githubviewer.ui.search;

import android.app.SearchManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.levine.githubviewer.R;
import com.levine.githubviewer.constant.Constants;
import com.levine.githubviewer.constant.ParameterConstants;
import com.levine.githubviewer.entity.RepositoriesEntity;
import com.levine.githubviewer.injector.component.ActivityComponent;
import com.levine.githubviewer.mvp.presenter.RepositoriesListPresenter;
import com.levine.githubviewer.mvp.view.IRepositoriesListView;
import com.levine.githubviewer.provider.SearchSuggestionProvider;
import com.levine.githubviewer.ui.RepositoriesDetailActivity;
import com.levine.githubviewer.ui.adapter.RepositoriesListAdapter;
import com.levine.githubviewer.ui.base.BaseRecycleViewActivity;
import com.levine.githubviewer.util.ToastUtil;

import java.util.List;

/**
 * Created on 2017/6/22.
 *
 * @author Levine
 */

public class SearchActivity extends BaseRecycleViewActivity<RepositoriesEntity,
        RepositoriesListPresenter, RepositoriesListAdapter> implements
        IRepositoriesListView<List<RepositoriesEntity>>{
    private String mSearchKeyword = "";
    private SearchRecentSuggestions mSearchSuggestions;

    @Override
    protected void initView() {
        super.initView();
        setDisplayHomeAsUp("");
        mRefreshLayout.setEnabled(false);
        mSearchSuggestions = new SearchRecentSuggestions(this,
                SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
    }

    @Override
    public void onItemClick(int position) {
        RepositoriesEntity repositoriesEntity = mAdapter.getData().get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EXTRA_CONTENT, repositoriesEntity);
        navigateTo(RepositoriesDetailActivity.class, bundle);
    }

    @Override
    protected void setupComponent(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    protected void initAdapter(RecyclerView.LayoutManager layoutManager) {
        mAdapter = new RepositoriesListAdapter(this, layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.onActionViewExpanded();
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchView.setQueryRefinementEnabled(true);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Cursor cursor = (Cursor) searchView.getSuggestionsAdapter().getItem(position);
                String suggestion = cursor.getString(cursor.getColumnIndex(SearchManager
                        .SUGGEST_COLUMN_TEXT_1));
                searchView.setQuery(suggestion, true);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public String getKeyword() {
        return mSearchKeyword;
    }

    @Override
    public String getSort() {
        return ParameterConstants.SORT.STARS;
    }

    @Override
    public String getOrder() {
        return ParameterConstants.ORDER.DESC;
    }

    private void search(String query){
        mRefreshLayout.setRefreshing(true);
        if(TextUtils.isEmpty(query)){
            ToastUtil.showToast(getString(R.string.please_input_search_content));
            return;
        }

        mSearchSuggestions.saveRecentQuery(query, null);
        mSearchKeyword = query;
        mPresenter.getData(mPage, mPageSize);
    }
}
