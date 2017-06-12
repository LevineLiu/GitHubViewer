package com.levine.githubviewer.http;

import com.levine.githubviewer.R;
import com.levine.githubviewer.util.ToastUtil;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created on 2017/6/12.
 *
 * @author Levine
 */

public class ErrorConsumer implements Consumer<Throwable>{
    @Override
    public void accept(@NonNull Throwable throwable) throws Exception {
        ToastUtil.showToast(R.string.data_load_failed);
    }

}
