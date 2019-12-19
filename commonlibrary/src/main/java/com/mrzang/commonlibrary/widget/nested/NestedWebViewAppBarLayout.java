package com.mrzang.commonlibrary.widget.nested;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

/**
 * @author mr.zang
 * date 2019-10-29
 * desc:
 */
@CoordinatorLayout.DefaultBehavior(NestedWebViewAppBarLayout.Behavior.class)
public class NestedWebViewAppBarLayout extends AppBarLayout {

    public NestedWebViewAppBarLayout(Context context) {
        super(context);
    }

    public NestedWebViewAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static class Behavior extends AppBarLayout.Behavior {

        private Toolbar toolbar;

        @Override
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child,
                                      View target, int dx, int dy, int[] consumed, int type) {
            NestedWebView nestedWebView = (NestedWebView) target;
            nestedWebView.onChangeCollapseToolbar(true, dy);
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        }
    }

}
