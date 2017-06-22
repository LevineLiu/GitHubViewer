package com.levine.githubviewer.mvp.interactor;

import android.content.Context;
import android.os.Bundle;

import com.levine.githubviewer.R;
import com.levine.githubviewer.constant.Constants;
import com.levine.githubviewer.ui.trending.TrendingListFragment;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import javax.inject.Inject;

/**
 * Created on 2017/3/10
 *
 * @author Levine
 */

public class TrendingInteractor extends BaseInteractor{
    private Context mContext;

    @Inject
    public TrendingInteractor(Context context){
        super();
        mContext = context;
    }

    @Inject
    public FragmentPagerItems getPagerFragments(){
        String[] title = mContext.getResources().getStringArray(R.array.trending_array);
        int count = title.length;
        FragmentPagerItems.Creator creator = FragmentPagerItems.with(mContext);
        for (int i=0; i<count; i++){
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.EXTRA_CONTENT, i);
            creator.add(title[i], TrendingListFragment.class, bundle);
        }
        return creator.create();
    }
}
