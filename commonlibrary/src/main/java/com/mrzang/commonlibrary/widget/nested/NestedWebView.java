package com.mrzang.commonlibrary.widget.nested;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Size;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;

/**
 * @author mr.zang
 * date 2019-10-29
 * desc:
 */
public class NestedWebView extends WebView implements NestedScrollingChild {
    private NestedScrollingChildHelper childHelper;

    private int lastTouchY;
    private int touchSlop;
    private boolean isBeingDragged;

    private int[] scrollConsumed = new int[2];
    private int[] scrollOffset = new int[2];

    private boolean isToolbarClosed;

    private int scrollingMode = SCROLLING_NOTHING_MODE;

    private final static short SCROLLING_NOTHING_MODE = 0;
    private final static short SCROLLING_APPBAR_MODE = 1;
    private final static short SCROLLING_WEBVIEW_MODE = 2;

    public NestedWebView(Context context) {
        super(context);
        init();
    }

    public NestedWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestedWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastTouchY = (int) ev.getY();
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                isBeingDragged = true;
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) ev.getY();
                int yDiff = Math.abs(y - lastTouchY);
                if (yDiff > touchSlop) {
                    lastTouchY = y;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                stopNestedScroll();
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int actionMasked = ev.getActionMasked();
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                lastTouchY = (int) ev.getY();
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) ev.getY();
                int yDiff = lastTouchY - y;
                if (dispatchNestedPreScroll(0, yDiff, scrollConsumed, scrollOffset)) {
                    yDiff -= scrollConsumed[1];
                }
                if (isBeingDragged) {
                    lastTouchY = y - scrollOffset[1];
                    if (scrollingMode == SCROLLING_WEBVIEW_MODE) {
                        yDiff = 0;
                    }
                    if (dispatchNestedScroll(0, 0, 0, yDiff, scrollOffset)) {
                        lastTouchY -= scrollOffset[1];
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isBeingDragged = false;
                stopNestedScroll();
                break;
        }
        if (actionMasked == MotionEvent.ACTION_MOVE) {
            if (this.isToolbarClosed) {
                return super.onTouchEvent(ev);
            } else {
                return true;
            }
        }
        return super.onTouchEvent(ev);
    }

    protected void init() {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        this.touchSlop = viewConfiguration.getScaledTouchSlop();
        this.childHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
        initWebViewSettings(this);
        initWebViewClient(this);
    }

    /**
     * dispatchNestedScrollに渡す値の制御用.
     * dispatchNestedScrollの第三引数によってAppBarLayoutの移動量が決まる(?)ので
     * WebViewかAppBarLayoutかを移動させたいタイミングを計算.
     *
     * @param isToolbarClosed
     * @param dy
     */
    void onChangeCollapseToolbar(boolean isToolbarClosed, int dy) {
        this.isToolbarClosed = isToolbarClosed;
        boolean isWebviewScrollTop = getScrollY() == 0;
        boolean scrollingUp = 0 < dy;
        if (isWebviewScrollTop && !(isToolbarClosed && scrollingUp)) {
            scrollingMode = SCROLLING_APPBAR_MODE;
        } else if (isToolbarClosed && (scrollingUp && isWebviewScrollTop || !scrollingUp && !isWebviewScrollTop)) {
            scrollingMode = SCROLLING_WEBVIEW_MODE;
        } else {
            scrollingMode = SCROLLING_NOTHING_MODE;
        }
    }

    //NestedScrollingChild
    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        childHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return childHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return childHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        childHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return childHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, @Size(value = 2) int[] offsetInWindow) {
        return childHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
                offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy,
                                           @Size(value = 2) int[] consumed,
                                           @Size(value = 2) int[] offsetInWindow) {
        return childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return childHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return childHelper.dispatchNestedPreFling(velocityX, velocityY);
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
        //        settings.setDomStorageEnabled(true);
        //        settings.setDatabaseEnabled(true);
        //        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //        settings.setAppCacheEnabled(true);
        //        settings.setAppCachePath(webView.getContext().getCacheDir().getAbsolutePath());

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
    }

    /**
     * 设置粗体
     */
    public void setBold() {
        loadUrl("javascript:tinyMCE.execCommand('bold')");
    }


    /**
     * 设置下划线
     */
    public void setUnderline() {
        loadUrl("javascript:tinyMCE.execCommand('Underline')");
    }

    /**
     * 设置斜体
     */
    public void setItalic() {
        loadUrl("javascript:tinyMCE.execCommand('Italic')");
    }

    /**
     * 设置左对齐
     */
    public void setJustifyLeft() {
        loadUrl("javascript:tinyMCE.execCommand('JustifyLeft')");
    }

    /**
     * 设置居中
     */
    public void setJustifyCenter() {
        loadUrl("javascript:tinyMCE.execCommand('JustifyCenter')");
    }


    /**
     * 设置右对齐
     */
    public void setJustifyRight() {
        loadUrl("javascript:tinyMCE.execCommand('JustifyRight')");
    }

    /**
     * 有序列表
     */
    public void setInsertOrderedList() {
        loadUrl("javascript:tinyMCE.execCommand('InsertOrderedList')");
    }

    /**
     * 无序列表
     */
    public void setInsertUnorderedList() {
        loadUrl("javascript:tinyMCE.execCommand('InsertUnorderedList')");
    }

    /**
     * 插入图片
     *
     * @param id
     * @param src
     */
    public void insertImg(String id, String src) {
        loadUrl("javascript:insertImg('" + id + "','" + src + "')");
    }

    /**
     * 插入图片占位
     *
     * @param id
     */
    public void insertPlaceholder(String id) {
        loadUrl("javascript:insertPlaceholder('" + id + "')");
    }

    /**
     * 插入视频
     *
     * @param url
     */
    public void insertVideo(String url) {
        loadUrl("javascript:insertVideo('" + url + "')");
    }

    /**
     * 插入音频
     *
     * @param url
     */
    public void insertRadio(String url) {
        loadUrl("javascript:insertRadio('" + url + "')");
    }

    /**
     * 插入链接
     *
     * @param href
     * @param title
     */
    public void setInsertLink(String href, String title) {
        loadUrl("javascript:insertLink('" + href + "','" + title + "')");
    }

    /**
     * 撤销
     */
    public void undo() {
        loadUrl("javascript:tinyMCE.execCommand('Undo')");
    }

    /**
     * 重做
     */
    public void redo() {
        loadUrl("javascript:tinyMCE.execCommand('Redo')");
    }

    /**
     * 设置字体
     *
     * @param size
     */
    public void setFontSize(@Size(min = 0, max = 2) int size) {
        loadUrl("javascript:setFontSize('" + size + "')");
    }

    public void setEditor(String editorContent) {
        loadUrl("javascript:setEditor('" + editorContent + "')");
    }

    /**
     * 获取输入的文章
     *
     * @param valueCallback
     * @throws Exception
     */
    public void getHtml(ValueCallback<String> valueCallback) throws Exception {
        if (Build.VERSION.SDK_INT < 19) {
            throw new Exception("此方法只能19：4.4以上设备使用");
        } else {
            evaluateJavascript("javascript:getEditor()", valueCallback);
        }
    }

    /**
     * 获取文章简介100字以内
     *
     * @param valueCallback
     * @throws Exception
     */
    public void getAbst(ValueCallback<String> valueCallback) throws Exception {
        if (Build.VERSION.SDK_INT < 19) {
            throw new Exception("此方法只能19：4.4以上设备使用");
        } else {
            evaluateJavascript("javascript:getAbst()", valueCallback);
        }
    }
}
