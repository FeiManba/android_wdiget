package com.mrzang.commonlibrary.widget.web;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.mrzang.commonlibrary.R;


/**
 * File: WebLoadingView.java
 * Author: ejiang
 * Version: V100R001C01
 * Create: 2017-12-13 10:21
 */

public class WebLoadingView extends LinearLayout {
    private Context mContext;
    private LayoutParams mParams;
    private AndroidJavaScriptListen mAndroidJavaScriptListen;
    private onWebLoadingViewListener mOnWebLoadingViewListener;

    public void setmOnWebLoadingViewListener(onWebLoadingViewListener mOnWebLoadingViewListener) {
        this.mOnWebLoadingViewListener = mOnWebLoadingViewListener;
    }

    private WebView mWebView;

    public WebView getmWebView() {
        return mWebView;
    }

    public WebLoadingView(Context context) {
        this(context, null);
    }

    public WebLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setOrientation(LinearLayout.VERTICAL); //默认横向排列
        init(attrs);
    }

    public void setmAndroidJavaScriptListen(AndroidJavaScriptListen mAndroidJavaScriptListen) {
        this.mAndroidJavaScriptListen = mAndroidJavaScriptListen;
        if (mAndroidJavaScriptListen != null) {
            if (mWebView != null) {
                mWebView.addJavascriptInterface(mAndroidJavaScriptListen, "growingjs");
            }
        }
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {

        }

        this.setWillNotDraw(false);

        //设置进度条
        mParams = new LayoutParams(LayoutParams.MATCH_PARENT, 5);
        ProgressBar progressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bar_states));

        addView(progressBar, mParams);

        //设置WebView
        mParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mWebView = new WebView(mContext);
        mWebView.setLayoutParams(mParams);
        mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        mWebView.setVerticalScrollBarEnabled(false); //垂直不显示
        initWebViewSettings(mWebView);
        initWebViewChromeClient(mWebView, progressBar);
        initWebViewClient(mWebView);

        addView(mWebView, mParams);

    }

    /**
     * @param webView
     */
    private void initWebViewClient(WebView webView) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!TextUtils.isEmpty(url) && (url.contains("http://") || url.contains("https://")))
                    return false;
                else return true;
            }
        });
    }

    private boolean isPlay = false;//是否播放WebView music

    public void setPlay(boolean play) {
        isPlay = play;
    }

    private void initWebViewChromeClient(final WebView webView, final ProgressBar progressBar) {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                    progressBar.setProgress(0);
                    if (isPlay) {
                        webView.loadUrl("javascript:audio.cutoff(" + true + ")");//暂停播放
                    }
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (mOnWebLoadingViewListener != null) mOnWebLoadingViewListener.getWebTitle(title);
            }
        });
    }

    public interface onWebLoadingViewListener {
        void getWebTitle(String title);
    }

    public void destroy() {
        if (mWebView != null) {
            mWebView.clearCache(false); //清除缓存
            mWebView.destroy();
        }
    }

    public void loadUrl(String url) {
        if (mWebView != null) {
            mWebView.loadUrl(url);
        }
    }


    //初始化webviewSettings
    private void initWebViewSettings(WebView webView) {
        WebSettings settings = webView.getSettings();
        //支持获取手势焦点
        webView.requestFocusFromTouch();

        //自动播放音乐
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            settings.setMediaPlaybackRequiresUserGesture(false);
        }

        // 不使用缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // 启用js交互
        settings.setJavaScriptEnabled(true);
        //注入js调用代码
        mWebView.addJavascriptInterface(mAndroidJavaScriptListen, "growingjs");
        //支持插件
        settings.setPluginState(WebSettings.PluginState.ON);
        //设置适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //支持缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        //隐藏原生的缩放控件
        settings.setDisplayZoomControls(false);
        //支持内容重新布局
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.supportMultipleWindows();
        settings.setSupportMultipleWindows(true);
        //设置缓存模式
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(webView.getContext().getCacheDir().getAbsolutePath());

        //设置可访问文件
        settings.setAllowFileAccess(true);
        //当webview调用requestFocus时为webview设置节点
        settings.setNeedInitialFocus(true);
        //支持自动加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        settings.setNeedInitialFocus(true);
        //设置编码格式
        settings.setDefaultTextEncodingName("UTF-8");
        //开启https http 混合调用
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    /**
     * 重定向退出
     *
     * @return
     */
    public boolean canGoBack() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            return false;
        }
    }
}
