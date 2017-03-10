package com.levine.githubviewer.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.levine.githubviewer.R;
import com.levine.githubviewer.entity.RepositoriesEntity;

/**
 * Created on 2017/3/10
 *
 * @author Levine
 */

public class RepositoriesListAdapter extends BaseRecyclerViewLoadMoreAdapter<RepositoriesEntity>{

    public RepositoriesListAdapter(Context context, RecyclerView.LayoutManager layoutManager){
        super(context, layoutManager);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_repositories;
    }

    @Override
    protected void onBindItemViewHolder(VH viewHolder, int position) {
        TextView repositoriesName = viewHolder.getView(R.id.tv_repositories_name);
        TextView description = viewHolder.getView(R.id.tv_repositories_description);
        TextView language = viewHolder.getView(R.id.tv_repositories_language);
        TextView start = viewHolder.getView(R.id.tv_repositories_star);
        TextView fork = viewHolder.getView(R.id.tv_repositories_fork);

        RepositoriesEntity entity = mData.get(position);
        repositoriesName.setText(entity.getFull_name());
        description.setText(entity.getDescription());
        language.setText(entity.getLanguage());
        start.setText(String.valueOf(entity.getStargazers_count()));
        fork.setText(String.valueOf(entity.getForks_count()));
    }
}
