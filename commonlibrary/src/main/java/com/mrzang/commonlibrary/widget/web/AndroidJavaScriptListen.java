package com.mrzang.commonlibrary.widget.web;

import android.webkit.JavascriptInterface;

public interface AndroidJavaScriptListen {
    /**
     * 点击图片
     *
     * @param iSrcs
     * @param iIndex
     */
    @JavascriptInterface
    void imgGalleryClick(String iSrcs, String iIndex);

    /**
     * 点击播放视频
     *
     * @param iSrc
     */
    @JavascriptInterface
    void videoPlayClick(String iSrc);

    /**
     * 点击绘本浏览
     *
     * @param iID
     */
    @JavascriptInterface
    void drawBookClick(String iID);

    /**
     * 获取绘本图片列表
     *
     * @param iDrawBookPageListUrl
     */
    @JavascriptInterface
    void getPictureList(String iDrawBookPageListUrl);

    /**
     * 点击跳转纪念册
     *
     * @param menu
     */
    @JavascriptInterface
    void openMenu(String menu);
}
