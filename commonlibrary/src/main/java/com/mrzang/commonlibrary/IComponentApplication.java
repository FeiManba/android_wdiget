package com.mrzang.commonlibrary;

import android.app.Application;

/**
 * 组件化 解决方案
 * lib module child 实现该方法
 * app module 反射拿到对象
 */
public interface IComponentApplication {
    void init(Application application);
}
