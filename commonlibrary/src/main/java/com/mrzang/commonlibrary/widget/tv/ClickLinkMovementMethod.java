package com.mrzang.commonlibrary.widget.tv;

import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

/**
 * description:
 * @author : mr.zang
 * createDate: 2019-11-28 15:09
 */
public class ClickLinkMovementMethod extends LinkMovementMethod {
    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        boolean b = super.onTouchEvent(widget,buffer,event);
        //解决点击事件冲突问题
        if(!b && event.getAction() == MotionEvent.ACTION_UP){
            ViewParent parent = widget.getParent();//处理widget的父控件点击事件
            if (parent instanceof ViewGroup) {
                return ((ViewGroup) parent).performClick();
            }
        }
        return b;
    }

    public static ClickLinkMovementMethod getInstance() {
        if (sInstance == null)
            sInstance = new ClickLinkMovementMethod();

        return sInstance;
    }
    private static ClickLinkMovementMethod sInstance;
}
