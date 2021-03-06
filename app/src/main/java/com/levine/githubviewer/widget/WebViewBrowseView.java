package com.levine.githubviewer.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.levine.githubviewer.R;
import com.levine.githubviewer.util.ConvertUtils;


/**
 * Created on 16/6/23
 * update on 17/1/22 configure whether progress bar is displayed or not
 * @author Levine
 */
public class WebViewBrowseView extends LinearLayout implements View.OnClickListener {
    private WebView mWebView;
    private ProgressBar mProgressBar;

    private String mUrl;
    private boolean mIsProgressDisplayed = true; //默认显示进度条
    private final int mProgressBarHeight = 3;
    private int mCurrentProgress;

    public WebViewBrowseView(Context context) {
        this(context, null);
    }

    public WebViewBrowseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebViewBrowseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        Resources res = context.getResources();
        TypedArray typedArray = res.obtainAttributes(attrs, R.styleable.WebViewBrowseView);
        mIsProgressDisplayed = typedArray.getBoolean(R.styleable.WebViewBrowseView_progress_visible, true);
        typedArray.recycle();
        if (mIsProgressDisplayed) {
            mProgressBar = (ProgressBar) LayoutInflater.from(context).inflate(R.layout.webview_progressbar, null);
            mProgressBar.setMax(100);
            LayoutParams progressParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(context, mProgressBarHeight));
            mProgressBar.setLayoutParams(progressParams);
            addView(mProgressBar);
        }

        mWebView = new WebView(context);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);

        //mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        //启用硬件加速后，在某些设备上（5.0以上的系统）：
        //java.util.concurrent.TimeoutException: android.view.ThreadedRenderer.finalize() timed out after 10 seconds
        //at java.lang.Daemons$Daemon.isRunning(Daemons.java:79)
        //at java.lang.Daemons$FinalizerDaemon.run(Daemons.java:174)
        //at java.lang.Thread.run(Thread.java:818)

        //loads the WebView completely zoomed out
        //mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setBuiltInZoomControls(false);

        LayoutParams webParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        mWebView.setLayoutParams(webParams);
        mWebView.setBackgroundColor(0);

        addView(mWebView);

        mWebView.setWebChromeClient(new WebChromeClient() {


            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (mIsProgressDisplayed) {
                    if (newProgress == 100) {
                        mCurrentProgress = 0;
                        mProgressBar.setVisibility(GONE);
                    } else if (mCurrentProgress < newProgress) {
                        mProgressBar.setVisibility(VISIBLE);
                        mProgressBar.setProgress(newProgress);
                        mCurrentProgress = newProgress;
                    }
                }
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mUrl = url;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        setOrientation(VERTICAL);
    }

    public WebView getWebView() {
        return mWebView;
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    public void loadHtmlWithUrl(String url) {
        mWebView.loadDataWithBaseURL(url, null, "text/html; charset=UTF-8", "utf-8", null);
    }

    public void loadHtmlWithData(String body){
        mWebView.loadDataWithBaseURL(null, getHtmlData(body), "text/html; charset=UTF-8", "utf-8", null);
    }

    public void loadHtmlWithData(String css, String body){
        mWebView.loadDataWithBaseURL(null, getHtmlDataWithCss(css, body), "text/html; charset=UTF-8", "utf-8", null);
    }

    /**
     * load local css
     */
    public void loadHtmlWithLocalCss(String css, String body){
        mWebView.loadDataWithBaseURL("file:///android_asset/", getHtmlDataWithCss(css, body), "text/html; charset=UTF-8", "utf-8", null);
    }

    /**
     * load html of night mode
     */
    public void loadNightModeHtml(String css, String body){
        mWebView.loadDataWithBaseURL(null, getNightModeHtmlWithCss(css, body), "text/html; charset=UTF-8", "utf-8", null);
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    private String getHtmlDataWithCss(String css, String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "<link href=" + css + " rel=\"stylesheet\" type=\"text/css\"/>"+
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    private String getNightModeHtmlWithCss(String css, String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "<link href=" + css + " rel=\"stylesheet\" type=\"text/css\"/>"+
                "</head>";
        return "<html>" + head + "<body  class=\"night\">" + bodyHTML + "</body></html>";
    }

    public boolean canGoBack() {
        return mWebView.canGoBack();
    }

    public void setWebViewClient(WebViewClient webViewClient){
        mWebView.setWebViewClient(webViewClient);
    }

    public void goBack() {
        if (mWebView != null) {
            if (mWebView.canGoBack())
                mWebView.goBack();
        }
    }

    public void refresh() {
        if (mWebView != null)
            mWebView.loadUrl(mUrl);
    }

    public boolean canGoForward() {
        return mWebView.canGoForward();
    }

    public void goForward() {
        if (mWebView != null) {
            if (mWebView.canGoForward())
                mWebView.goForward();
        }
    }

    public void release() {
        if (mWebView != null){
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
            removeAllViews();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}

