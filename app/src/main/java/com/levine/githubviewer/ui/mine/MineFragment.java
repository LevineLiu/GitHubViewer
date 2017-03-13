package com.levine.githubviewer.ui.mine;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatDelegate;
import android.util.TypedValue;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.levine.githubviewer.R;
import com.levine.githubviewer.ui.MainActivity;
import com.levine.githubviewer.ui.base.BaseAppCompatActivity;
import com.levine.githubviewer.ui.base.BaseFragment;

import butterknife.BindView;

/**
 * Created on 2017/3/9
 *
 * @author Levine
 */

public class MineFragment extends BaseFragment{

    @BindView(R.id.tv_user_name) TextView mUserNameTv;
    @BindView(R.id.tv_my_favorite) TextView mMyFavoriteTv;
    @BindView(R.id.tv_night_mode) TextView mNightModeTv;
    @BindView(R.id.switch_night_mode) Switch mNightModeSwitch;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            mNightModeSwitch.setChecked(true);
        else
            mNightModeSwitch.setChecked(false);

        mNightModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    ((MainActivity) mContext).setDayNightMode(false);
                else
                    ((MainActivity) mContext).setDayNightMode(true);
                setDayNightMode();
            }
        });
    }

    @Override
    protected void setupComponent(BaseAppCompatActivity activity) {

    }

    public void setDayNightMode(){
        if(getView() == null)
            return;
        TypedValue background = new TypedValue();
        TypedValue textColor = new TypedValue();
        TypedValue cardBackground = new TypedValue();
        TypedValue colorPrimary = new TypedValue();
        Resources.Theme theme = mContext.getTheme();
        theme.resolveAttribute(R.attr.backgroundColor, background, true);
        theme.resolveAttribute(R.attr.primaryTextColor, textColor, true);
        theme.resolveAttribute(R.attr.backgroundCardColor, cardBackground, true);
        theme.resolveAttribute(R.attr.colorPrimary, colorPrimary, true);
        mUserNameTv.setTextColor(getResources().getColor(textColor.resourceId));
        mMyFavoriteTv.setTextColor(getResources().getColor(textColor.resourceId));
        mNightModeTv.setTextColor(getResources().getColor(textColor.resourceId));
        mConvertView.setBackgroundResource(background.resourceId);
    }
}
