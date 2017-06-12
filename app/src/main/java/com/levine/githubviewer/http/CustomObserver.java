package com.levine.githubviewer.http;

import android.widget.Toast;

import com.levine.githubviewer.R;
import com.levine.githubviewer.util.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created on 2017/6/12.
 *
 * @author Levine
 *
 */

public class CustomObserver<T> implements Observer<T>{
    protected Disposable mDisposable;
    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        ToastUtil.showToast(R.string.data_load_failed);
    }

    @Override
    public void onComplete() {

    }
}
