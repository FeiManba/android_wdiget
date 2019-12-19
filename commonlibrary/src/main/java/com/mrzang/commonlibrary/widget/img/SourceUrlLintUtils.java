
package com.mrzang.commonlibrary.widget.img;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * url+author mr.zang
 * date 2019-10-11
 * desc: 资源文件路径 处理类
 */
public final class SourceUrlLintUtils {
    private SourceUrlLintUtils() {
    }

    /**
     * @param url  资源路径
     * @param view 位图 ImageView
     * @return 处理后的路径
     */
    public static String lintSourceUrl(String url, @NonNull View view) {
        try {
            if (TextUtils.isEmpty(url)) {
                return url;
            }
            if (url.contains("?size")) {
                return url;
            }
            int width = view.getWidth();
            if (width <= 120) {
                url = url + "?size=s120";
            } else if (width <= 200) {
                url = url + "?size=s200";
            } else if (width <= 480) {
                url = url + "?size=m480";
            } else if (width <= 720) {
                url = url + "?size=m720";
            } else {
                url = url + "?size=lg1080";
            }
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return url;
        }
    }
}
