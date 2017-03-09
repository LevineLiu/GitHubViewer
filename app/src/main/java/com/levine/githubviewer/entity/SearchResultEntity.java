package com.levine.githubviewer.entity;

import java.util.List;

/**
 * Created on 2017/3/9
 *
 * @author Levine
 */

public class SearchResultEntity {
    /**
     * total_count : 640
     * incomplete_results : false
     * items : []
     */

    private int total_count;
    private boolean incomplete_results;
    private List<RepositoriesEntity> items;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public List<RepositoriesEntity> getItems() {
        return items;
    }

    public void setItems(List<RepositoriesEntity> items) {
        this.items = items;
    }
}
