package com.mrzang.commonlibrary.widget.tv;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.RectF;

/**
 * 字符串转换成Bitmap图像
 *
 * @author ejiang
 */
public class StringTransformationBitmapUtils {

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 把文字转换成图片
     *
     * @param context
     * @param str
     * @return
     */
    public static Bitmap createBitmap(Context context, String str) {
        int txtHeight = sp2px(context, 14) + 1;
        Paint measurePaint = new Paint();
        measurePaint.setTextSize(txtHeight);
        int bmWidth = (int) measurePaint.measureText(str);// 计算文字实际占用的宽度
        int bmHeight = 12 + txtHeight;// 将高度+10防止绘制椭圆时左右的文字超出椭圆范围
        Bitmap bm = Bitmap.createBitmap(bmWidth + 20, bmHeight,
                Config.ARGB_8888);
        Canvas bmCanvas = new Canvas(bm);
        Paint bgPaint = new Paint();
        RectF targetRect = new RectF(5, 0, bmWidth + 15, bmHeight);// 矩形
        float roundPx = 5;
        bgPaint.setAntiAlias(true);// 设置Paint为无锯齿
        bmCanvas.drawARGB(0, 0, 0, 0);// 透明色
        bgPaint.setColor(Color.parseColor("#c5ebcc"));// 背景色
        bmCanvas.drawRoundRect(targetRect, roundPx, roundPx, bgPaint);// 绘制圆角矩形
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.parseColor("#000000"));// 字体颜色
        textPaint.setAntiAlias(true);
        textPaint.setStrokeWidth(2);
        textPaint.setTextSize(sp2px(context, 14));
        FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (int) ((targetRect.bottom + targetRect.top
                - fontMetrics.bottom - fontMetrics.top) / 2);
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        textPaint.setTextAlign(Paint.Align.CENTER);
        bmCanvas.drawText(str, targetRect.centerX(), baseline, textPaint);
        bmCanvas.save();
        bmCanvas.restore();
        return bm;
    }

    public static Bitmap createBitmap(Context context, String str, int bgColor, int fontColor, int fontSize) {
        int txtHeight = sp2px(context, 14) + 1;
        Paint measurePaint = new Paint();
        measurePaint.setTextSize(txtHeight);
        int bmWidth = (int) measurePaint.measureText(str);// 计算文字实际占用的宽度
        int bmHeight = 12 + txtHeight;// 将高度+10防止绘制椭圆时左右的文字超出椭圆范围
        Bitmap bm = Bitmap.createBitmap(bmWidth + 20, bmHeight,
                Config.ARGB_8888);
        Canvas bmCanvas = new Canvas(bm);
        Paint bgPaint = new Paint();
        RectF targetRect = new RectF(5, 0, bmWidth + 15, bmHeight);// 矩形
        float roundPx = 5;
        bgPaint.setAntiAlias(true);// 设置Paint为无锯齿
        bmCanvas.drawARGB(0, 0, 0, 0);// 透明色
        bgPaint.setColor(bgColor);// 背景色
        bmCanvas.drawRoundRect(targetRect, roundPx, roundPx, bgPaint);// 绘制圆角矩形
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(fontColor);// 字体颜色
        textPaint.setAntiAlias(true);
        textPaint.setStrokeWidth(2);
        textPaint.setTextSize(sp2px(context, fontSize));
        FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (int) ((targetRect.bottom + targetRect.top
                - fontMetrics.bottom - fontMetrics.top) / 2);
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        textPaint.setTextAlign(Paint.Align.CENTER);
        bmCanvas.drawText(str, targetRect.centerX(), baseline, textPaint);
        bmCanvas.save();
        bmCanvas.restore();
        return bm;
    }
}
