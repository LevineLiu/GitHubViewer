package com.levine.githubviewer.mvp.interactor;

import android.content.Context;
import android.os.Bundle;

import com.levine.githubviewer.R;
import com.levine.githubviewer.ui.home.RepositoriesListFragment;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import javax.inject.Inject;

/**
 * Created on 2017/3/9
 *
 * @author Levine
 */

public class HomeFragmentInteractor extends BaseInteractor{
    private Context mContext;

    @Inject
    public HomeFragmentInteractor(Context context){
        super();
        mContext = context;
    }

    @Inject
    public FragmentPagerItems getPagerFragments(){
        String[] title = mContext.getResources().getStringArray(R.array.home_title_array);
        int count = title.length;
        FragmentPagerItems.Creator creator = FragmentPagerItems.with(mContext);
        for (int i=0; i<count; i++){
            Bundle bundle = new Bundle();
            bundle.putString(RepositoriesListFragment.EXTRA_KEYWORD, title[i]);
            creator.add(title[i], RepositoriesListFragment.class, bundle);
        }
        return creator.create();
    }
}
