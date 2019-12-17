package com.mrzang.android_widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author mr.zang
 * date 2019-12-17
 * desc: 加载框封装的基础类
 */
public class ProgressBarWidgetDialog extends Dialog {

    /**
     * 加载框
     */
    private ProgressBar progressBar;

    /**
     * 控件的宽
     */
    private int viewWidget;

    /**
     * 控件的高度
     */
    private int viewHeight;

    private Context context;

    public ProgressBarWidgetDialog(@NonNull Context context) {
        super(context);
    }

    public ProgressBarWidgetDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ProgressBarWidgetDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
