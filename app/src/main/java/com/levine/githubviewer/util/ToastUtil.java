package com.levine.githubviewer.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.levine.githubviewer.GitHubViewerApplication;

/**
 * Created on 2017/6/12.
 *
 * @author Levine
 *
 * A util of toast message
 */

public class ToastUtil {
    private static Toast mToast;//防止上一个toast还没结束时弹出新的toast，这里只使用一个toast
    private static boolean mIsShortDuration;

    public static void showToast(String message){
        showToast(message, true);
    }

    public static void showToast(String message, boolean isShortDuration){
        if(TextUtils.isEmpty(message))
            return;
        if(mToast == null || mIsShortDuration != isShortDuration) {
            mToast = Toast.makeText(GitHubViewerApplication.getContext(), message, isShortDuration ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
            mIsShortDuration = isShortDuration;
        }
        else
            mToast.setText(message);
        mToast.show();
    }

    public static void showToast(int resId){
        showToast(resId, true);
    }

    public static void showToast(int resId, boolean isShortDuration) {
        Context context = GitHubViewerApplication.getContext();
        String message = context.getResources().getString(resId);
        if (TextUtils.isEmpty(message))
            return;
        if (mToast == null || mIsShortDuration != isShortDuration) {
            mToast = Toast.makeText(context, message, isShortDuration ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
            mIsShortDuration = isShortDuration;
        } else
            mToast.setText(message);
        mToast.show();
    }
}
