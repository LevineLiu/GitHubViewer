package com.levine.githubviewer.mvp.interactor;

import android.content.Context;

import com.levine.githubviewer.R;
import com.levine.githubviewer.ui.home.CommonRepositoriesListFragment;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import javax.inject.Inject;

/**
 * Created on 2017/3/9
 *
 * @author Levine
 */

public class HomeFragmentInteractor {

    @Inject
    public FragmentPagerItems getPagerFragments(Context context){
        String[] title = context.getResources().getStringArray(R.array.home_title_array);
        return FragmentPagerItems.with(context)
                .add(title[0], CommonRepositoriesListFragment.class)
                .add(title[1], CommonRepositoriesListFragment.class)
                .create();
    }
}
