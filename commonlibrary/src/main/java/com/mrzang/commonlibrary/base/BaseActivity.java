package com.mrzang.commonlibrary.base;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author mr.zang
 * date 2019-12-17
 * desc: Activity 基础类
 * 封装 ProgressDialog
 * 实现 状态栏适配
 * 实现文字大小 管理
 * 实现 ui适配
 */
public class BaseActivity extends AppCompatActivity {

    private CountDownTimer downTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏适配
        int color = getStatusColor();
        //设置状态栏颜色

    }

    /**
     * 获取状态栏颜色
     *
     * @return
     */
    public int getStatusColor() {
        return Color.WHITE;
    }

    /**
     * 显示进度加载框
     */
    public void showProgressDialog() {

    }

    /**
     * 显示进度加载框
     *
     * @param progressMessage 所需要的信息
     */
    public void showProgressDialog(String progressMessage) {

    }

    /**
     * 关闭加载框
     */
    public void hiddenProgressDialog() {
        this.hiddenTimeProgressDialog(0);
    }

    /**
     * 延时关闭ProgressDialog
     *
     * @param progressHiddenCountDown
     */
    public void hiddenTimeProgressDialog(final long progressHiddenCountDown) {
        if (progressHiddenCountDown >= 1000) {
            downTimer = new CountDownTimer(progressHiddenCountDown, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (millisUntilFinished == progressHiddenCountDown) {
                        closeProgressDialog();
                    }
                }

                @Override
                public void onFinish() {

                }
            }.start();
        } else {
            closeProgressDialog();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        closeProgressDialog();
    }

    private void closeProgressDialog() {
        if (downTimer != null) {
            downTimer.cancel();
        }
        // TODO: 2019-12-17 关闭加载框
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hiddenProgressDialog();
    }
}
