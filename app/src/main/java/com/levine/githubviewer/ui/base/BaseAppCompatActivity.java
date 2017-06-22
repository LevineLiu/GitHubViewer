package com.levine.githubviewer.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;


import com.levine.githubviewer.GitHubViewerApplication;
import com.levine.githubviewer.R;
import com.levine.githubviewer.broadcast.NetStateChangeBroadcast;
import com.levine.githubviewer.injector.component.ActivityComponent;
import com.levine.githubviewer.injector.component.DaggerActivityComponent;
import com.levine.githubviewer.injector.module.ActivityModule;
import com.levine.githubviewer.listener.NetStateListener;
import com.levine.githubviewer.mvp.presenter.BasePresenter;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.ButterKnife;


/**
 * Created on 16/4/26
 *
 * @author LLW
 */



public abstract class BaseAppCompatActivity<P extends BasePresenter> extends AppCompatActivity implements NetStateListener {
    protected String TAG;
    protected int mScreenWidth;
    protected int mScreenHeight;
    private boolean isFirstResume = true;
    private ActivityComponent mComponent;
    private Toast mToast;//防止上一个toast还没结束时弹出新的toast，这里只使用一个toast
    private boolean mIsShortDuration;
    public Toolbar mToolbar;

    @Inject
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.AppTheme_Dark);
        }else{
            setTheme(R.style.AppTheme_Light);
        }

        super.onCreate(savedInstanceState);
        NetStateChangeBroadcast.setNetStateChangeListener(this);
        Bundle extra = null;
        if(getIntent() != null)
            extra = getIntent().getExtras();
        getBundleExtra(extra);

        if(getLayoutId() != 0)
            setContentView(getLayoutId());
        else
            throw new IllegalArgumentException("you must return a right layout id");


        //get the width and height of the screen
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;

        TAG = this.getClass().getSimpleName();
        mComponent = DaggerActivityComponent.builder()
                .appComponent(((GitHubViewerApplication) getApplication()).getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
        setupComponent(mComponent);

        initView();

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        if(mToolbar != null){
            setSupportActionBar(mToolbar);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetStateChangeBroadcast.getInstance().registerNetStateChangeReceiver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NetStateChangeBroadcast.getInstance().unRegisterNetStateChangeReceiver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetStateChangeBroadcast.removeNetStateChangeListener(this);
    }

    @Override
    public void onNetConnected() {
        onNetworkConnected();
    }

    @Override
    public void onNetDisconnected() {
        onNetworkDisconnected();
    }

    public ActivityComponent getActivityComponent(){
        return mComponent;
    }

    protected abstract void setupComponent(ActivityComponent component);

    protected void onNetworkConnected(){}

    protected void onNetworkDisconnected(){
        //showToast(R.string.network_error);
    }

    protected void setDisplayHomeAsUp(String title){
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if(TextUtils.isEmpty(title))
                getSupportActionBar().setTitle("");
            else
                getSupportActionBar().setTitle(title);
        }
        if(mToolbar != null){
            mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
    /**
     * Retrieves the extended data from the last activity.
     *
     * @param extra
     */
    protected abstract void getBundleExtra(Bundle extra);

    /**
     * @return the layout id of this activity
     */
    protected abstract int getLayoutId();

    /**
     * initialize
     */
    protected abstract void initView();


    /*************************** start navigate *********************************/

    protected void navigateTo(Class toClass){
        Intent intent = new Intent(this, toClass);
        startActivity(intent);
    }

    protected void navigateAndFinish(Class toClass){
        Intent intent = new Intent(this, toClass);
        startActivity(intent);
        finish();
    }

    protected void navigateTo(Class toClass, Bundle bundle){
        Intent intent = new Intent(this, toClass);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void navigateForResult(Class toClass, int requestCode){
        Intent intent = new Intent(this, toClass);
        startActivityForResult(intent, requestCode);
    }

    protected void navigateForResult(Class toClass, Bundle bundle, int requestCode){
        Intent intent = new Intent(this, toClass);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    /*************************** end navigate *********************************/


    /*************************** start show toast *********************************/

    public void showToast(String message){
        showToast(message, true);
    }

    public void showToast(String message, boolean isShortDuration){
        if(TextUtils.isEmpty(message))
            return;
        if(mToast == null || mIsShortDuration != isShortDuration) {
            mToast = Toast.makeText(this, message, isShortDuration ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
            mIsShortDuration = isShortDuration;
        }
        else
            mToast.setText(message);
        mToast.show();
    }

    public void showToast(int resId){
        showToast(resId, true);
    }

    public void showToast(int resId, boolean isShortDuration) {
        String message = getResources().getString(resId);
        if (TextUtils.isEmpty(message))
            return;
        if (mToast == null || mIsShortDuration != isShortDuration) {
            mToast = Toast.makeText(this, message, isShortDuration ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
            mIsShortDuration = isShortDuration;
        } else
            mToast.setText(message);
        mToast.show();
    }
    /*************************** end show toast *********************************/



    /*************************** start  handler *********************************/
    private InnerWeakHandler mHandler;
    public InnerWeakHandler getHandlerInstance(){
        if(mHandler == null)
            mHandler = new InnerWeakHandler(BaseAppCompatActivity.this);
        return mHandler;
    }

    private static class InnerWeakHandler extends Handler {
        private WeakReference<BaseAppCompatActivity> context;
        public InnerWeakHandler(BaseAppCompatActivity context){
            this.context = new WeakReference<BaseAppCompatActivity>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseAppCompatActivity activity = context.get();
            if(activity != null)
                activity.handleHandlerMessage(msg);
        }
    }

    public void handleHandlerMessage(Message msg){

    }


    /*************************** end handler *********************************/

}
