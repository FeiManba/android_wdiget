package com.mrzang.commonlibrary.widget.tv;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import com.mrzang.commonlibrary.utils.DisplayUtils;


/**
 * Created by sflin
 */

public class CSSTextView extends AppCompatTextView {

    public boolean isClickSpan = false;

    private String[] colorArr, sizeArr, styleArr;

    public CSSTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CSSTextView(Context context) {
        this(context, null);
    }

    public CSSTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        if (isClickSpan) {
            return true;
        }
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        isClickSpan = false;
        return super.onTouchEvent(event);
    }

    /**
     * 设置文本内容其中文字的颜色
     *
     * @param text  内容
     * @param color 颜色
     */
    public CSSTextView setTextArrColor(String text, int color) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        int startIndex = (getText() + "").indexOf(text);
        if (startIndex > -1) {
            styledText.setSpan(new ForegroundColorSpan(color), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(styledText, TextView.BufferType.SPANNABLE);
        }
        startIndex = (getText() + "").lastIndexOf(text);
        if (startIndex > -1) {
            styledText.setSpan(new ForegroundColorSpan(color), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(styledText, TextView.BufferType.SPANNABLE);
        }
        return this;
    }

    /**
     * 设置文本内容 其中的文本颜色
     * 1.添加默认设置
     *
     * @param text
     * @param bgColor
     * @param fontColor
     * @param fontSize
     * @return
     */
    public CSSTextView setTextBgArrColor(String text, int bgColor,
                                         int fontColor, int fontSize) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        Bitmap createBitmap = StringTransformationBitmapUtils.createBitmap(
                getContext(), text, bgColor, fontColor, fontSize);
        if (createBitmap != null) {
            ImageSpan imgSpan = new ImageSpan(getContext(), createBitmap);
            SpannableString styledText = new SpannableString(text);
            styledText.setSpan(imgSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            append(styledText);
        }
        return this;
    }

    /**
     * 文字后边追加 drawable 文字
     *
     * @param bitmap
     * @param onClickSpan 点击事件
     * @return
     */
    public CSSTextView setBitmapSpan(Bitmap bitmap, final OnClickSpan onClickSpan) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        if (bitmap != null) {
            ImageSpan imageSpan = new ImageSpan(getContext(), bitmap);
            SpannableString spannableString = new SpannableString("iconicon");
            spannableString.setSpan(imageSpan, 0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    onClickSpan.onClick("");
                }
            }, 0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            append(spannableString);
            setMovementMethod(ClickLinkMovementMethod.getInstance());
        }
        return this;
    }

    /**
     * 设置文本内容 其中的文本颜色
     * 1.添加默认设置
     *
     * @param text
     * @param fontColor
     * @param fontSize
     * @return
     */
    public CSSTextView setTextBgArrColor(final String text, final int fontColor, final int fontSize, final OnClickSpan onClickSpan) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(text);
        styledText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                onClickSpan.onClick(text);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //设置文本的颜色
                ds.setColor(fontColor);
                ds.setTextSize(fontSize);
                //超链接形式的下划线，false 表示不
                ds.setUnderlineText(false);
            }
        }, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        append(styledText);
        return this;
    }

    /**
     * 设置文本内容其中文字的颜色
     *
     * @param text  内容
     * @param color 颜色
     */
    public CSSTextView setTextArrColorLast(String text, int color) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        int startIndex = (getText() + "").lastIndexOf(text);
        if (startIndex > -1) {
            styledText.setSpan(new ForegroundColorSpan(color), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(styledText, TextView.BufferType.SPANNABLE);
        }
        return this;
    }

    /**
     * 设置文本内容其中文字的颜色
     *
     * @param text        内容
     * @param color       颜色
     * @param onClickSpan 点击事件
     */
    public CSSTextView setTextArrColor(final String text, final int color, final OnClickSpan onClickSpan) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        int startIndex = (getText() + "").indexOf(text);
        if (startIndex > -1) {
            styledText.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    onClickSpan.onClick(text);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置文本的颜色
                    ds.setColor(color);
                    //超链接形式的下划线，false 表示不
                    ds.setUnderlineText(false);
                }
            }, startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(styledText, TextView.BufferType.SPANNABLE);
            setMovementMethod(CustomLinkMovementMethod.getInstance());
        }
        return this;
    }

    /**
     * 设置文本内容其中文字的颜色
     *
     * @param text       内容
     * @param color      颜色
     * @param startIndex 开始位置
     */
    public CSSTextView setTextArrColor(String text, int color, int startIndex) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        styledText.setSpan(new ForegroundColorSpan(color), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(styledText, TextView.BufferType.SPANNABLE);
        return this;
    }

    /**
     * 设置文本内容其中文字的颜色
     *
     * @param text        内容
     * @param color       颜色
     * @param startIndex  开始位置
     * @param onClickSpan 点击事件
     */
    public CSSTextView setTextArrColor(final String text, final int color, int startIndex, final OnClickSpan onClickSpan) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        styledText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                onClickSpan.onClick(text);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //设置文本的颜色
                ds.setColor(color);
                //超链接形式的下划线，false 表示不
                ds.setUnderlineText(false);
            }
        }, startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(styledText, TextView.BufferType.SPANNABLE);
        setMovementMethod(CustomLinkMovementMethod.getInstance());

        return this;
    }

    /**
     * 设置文本内容其中文字的字体大小
     *
     * @param text 内容
     * @param size 字体大小
     */
    public CSSTextView setTextArrSize(String text, int size) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        int startIndex = (getText() + "").indexOf(text);
        if (startIndex > -1) {
            styledText.setSpan(new AbsoluteSizeSpan(DisplayUtils.dp2px(getContext(), size)), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(styledText, TextView.BufferType.SPANNABLE);
        }
        return this;
    }

    /**
     * 设置文本内容其中文字的字体大小
     *
     * @param text        内容
     * @param size        字体大小
     * @param onClickSpan 点击事件
     */
    public CSSTextView setTextArrSize(final String text, int size, final OnClickSpan onClickSpan) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        int startIndex = (getText() + "").indexOf(text);
        if (startIndex > -1) {
            styledText.setSpan(new AbsoluteSizeSpan(DisplayUtils.dp2px(getContext(), size)), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    onClickSpan.onClick(text);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置文本的颜色
                    ds.setColor(getTextColors().getDefaultColor());
                    //超链接形式的下划线，false 表示不
                    ds.setUnderlineText(false);
                }
            }, startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(styledText, TextView.BufferType.SPANNABLE);
            setMovementMethod(CustomLinkMovementMethod.getInstance());
        }
        return this;
    }

    /**
     * 设置文本内容其中文字的字体大小
     *
     * @param text       内容
     * @param size       字体大小
     * @param startIndex 开始位置
     */
    public CSSTextView setTextArrSize(String text, int size, int startIndex) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        styledText.setSpan(new AbsoluteSizeSpan(DisplayUtils.dp2px(getContext(), size)), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(styledText, TextView.BufferType.SPANNABLE);
        return this;
    }

    /**
     * 设置文本内容其中文字的字体大小
     *
     * @param text        内容
     * @param size        字体大小
     * @param startIndex  开始位置
     * @param onClickSpan 点击事件
     */
    public CSSTextView setTextArrSize(final String text, int size, int startIndex, final OnClickSpan onClickSpan) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        styledText.setSpan(new AbsoluteSizeSpan(DisplayUtils.dp2px(getContext(), size)), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                onClickSpan.onClick(text);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //设置文本的颜色
                ds.setColor(getTextColors().getDefaultColor());
                //超链接形式的下划线，false 表示不
                ds.setUnderlineText(false);
            }
        }, startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(styledText, TextView.BufferType.SPANNABLE);
        setMovementMethod(CustomLinkMovementMethod.getInstance());
        return this;
    }

    /**
     * 设置文本内容其中文字的字体样式
     *
     * @param text  内容
     * @param style 字体样式
     */
    public CSSTextView setTextArrStyle(String text, int style) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        int startIndex = (getText() + "").indexOf(text);
        if (startIndex > -1) {
            styledText.setSpan(new StyleSpan(style), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(styledText, TextView.BufferType.SPANNABLE);
        }
        return this;
    }

    /**
     * 设置文本内容其中文字的字体样式
     *
     * @param text        内容
     * @param style       字体样式
     * @param onClickSpan 点击事件
     */
    public CSSTextView setTextArrStyle(final String text, int style, final OnClickSpan onClickSpan) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        int startIndex = (getText() + "").indexOf(text);
        if (startIndex > -1) {
            styledText.setSpan(new StyleSpan(style), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    onClickSpan.onClick(text);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置文本的颜色
                    ds.setColor(getTextColors().getDefaultColor());
                    //超链接形式的下划线，false 表示不
                    ds.setUnderlineText(false);
                }
            }, startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(styledText, TextView.BufferType.SPANNABLE);
            setMovementMethod(CustomLinkMovementMethod.getInstance());
        }
        return this;
    }

    /**
     * 设置文本内容其中文字的字体样式
     *
     * @param text       内容
     * @param style      字体样式
     * @param startIndex 开始位置
     */
    public CSSTextView setTextArrStyle(String text, int style, int startIndex) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        styledText.setSpan(new StyleSpan(style), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(styledText, TextView.BufferType.SPANNABLE);
        return this;
    }

    /**
     * 设置文本内容其中文字的字体样式
     *
     * @param text        内容
     * @param style       字体样式
     * @param startIndex  开始位置
     * @param onClickSpan 点击事件
     */
    public CSSTextView setTextArrStyle(final String text, int style, int startIndex, final OnClickSpan onClickSpan) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        styledText.setSpan(new StyleSpan(style), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                onClickSpan.onClick(text);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //设置文本的颜色
                ds.setColor(getTextColors().getDefaultColor());
                //超链接形式的下划线，false 表示不
                ds.setUnderlineText(false);
            }
        }, startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(styledText, TextView.BufferType.SPANNABLE);
        setMovementMethod(CustomLinkMovementMethod.getInstance());
        return this;
    }

    /**
     * 设置文本内容其中文字的字体颜色，字体大小
     *
     * @param text  内容
     * @param color 字体颜色
     * @param size  字体大小
     */
    public CSSTextView setTextArrColorSize(String text, int color, int size) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        int startIndex = (getText() + "").indexOf(text);
        if (startIndex > -1) {
            styledText.setSpan(new ForegroundColorSpan(color), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new AbsoluteSizeSpan(DisplayUtils.dp2px(getContext(), size)), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(styledText, TextView.BufferType.SPANNABLE);
        }
        return this;
    }

    /**
     * 设置文本内容其中文字的字体颜色，字体大小
     *
     * @param text        内容
     * @param color       字体颜色
     * @param size        字体大小
     * @param onClickSpan 点击事件
     */
    public CSSTextView setTextArrColorSize(final String text, final int color, int size, final OnClickSpan onClickSpan) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        int startIndex = (getText() + "").indexOf(text);
        if (startIndex > -1) {
            styledText.setSpan(new AbsoluteSizeSpan(DisplayUtils.dp2px(getContext(), size)), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    onClickSpan.onClick(text);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置文本的颜色
                    ds.setColor(color);
                    //超链接形式的下划线，false 表示不
                    ds.setUnderlineText(false);
                }
            }, startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(styledText, TextView.BufferType.SPANNABLE);
            setMovementMethod(CustomLinkMovementMethod.getInstance());
        }
        return this;
    }

    /**
     * 设置文本内容其中文字的字体颜色，字体大小
     *
     * @param text       内容
     * @param color      字体颜色
     * @param size       字体大小
     * @param startIndex 开始位置
     */
    public CSSTextView setTextArrColorSize(String text, int color, int size, int startIndex) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        styledText.setSpan(new ForegroundColorSpan(color), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new AbsoluteSizeSpan(DisplayUtils.dp2px(getContext(), size)), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(styledText, TextView.BufferType.SPANNABLE);
        return this;
    }

    /**
     * 设置文本内容其中文字的字体颜色，字体大小
     *
     * @param text        内容
     * @param color       字体颜色
     * @param size        字体大小
     * @param startIndex  开始位置
     * @param onClickSpan 点击事件
     */
    public CSSTextView setTextArrColorSize(final String text, final int color, int size, int startIndex, final OnClickSpan onClickSpan) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        styledText.setSpan(new AbsoluteSizeSpan(DisplayUtils.dp2px(getContext(), size)), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                onClickSpan.onClick(text);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //设置文本的颜色
                ds.setColor(color);
                //超链接形式的下划线，false 表示不
                ds.setUnderlineText(false);
            }
        }, startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(styledText, TextView.BufferType.SPANNABLE);
        setMovementMethod(CustomLinkMovementMethod.getInstance());
        return this;
    }

    /**
     * 设置文本内容其中文字的字体颜色，字体样式
     *
     * @param text  内容
     * @param color 字体颜色
     * @param style 字体样式
     */
    public CSSTextView setTextArrColorStyle(String text, int color, int style) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        int startIndex = (getText() + "").indexOf(text);
        if (startIndex > -1) {
            styledText.setSpan(new ForegroundColorSpan(color), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new StyleSpan(style), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(styledText, TextView.BufferType.SPANNABLE);
        }
        return this;
    }

    /**
     * 设置文本内容其中文字的字体颜色，字体样式
     *
     * @param text        内容
     * @param color       字体颜色
     * @param style       字体样式
     * @param onClickSpan 点击事件
     */
    public CSSTextView setTextArrColorStyle(final String text, final int color, int style, final OnClickSpan onClickSpan) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        int startIndex = (getText() + "").indexOf(text);
        if (startIndex > -1) {
            styledText.setSpan(new StyleSpan(style), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    onClickSpan.onClick(text);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置文本的颜色
                    ds.setColor(color);
                    //超链接形式的下划线，false 表示不
                    ds.setUnderlineText(false);
                }
            }, startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(styledText, TextView.BufferType.SPANNABLE);
            setMovementMethod(CustomLinkMovementMethod.getInstance());
        }
        return this;
    }

    /**
     * 设置文本内容其中文字的字体颜色，字体样式
     *
     * @param text       内容
     * @param color      字体颜色
     * @param style      字体样式
     * @param startIndex 开始位置
     */
    public CSSTextView setTextArrColorStyle(String text, int color, int style, int startIndex) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        styledText.setSpan(new ForegroundColorSpan(color), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new StyleSpan(style), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(styledText, TextView.BufferType.SPANNABLE);
        return this;
    }

    /**
     * 设置文本内容其中文字的字体颜色，字体样式
     *
     * @param text        内容
     * @param color       字体颜色
     * @param style       字体样式
     * @param startIndex  开始位置
     * @param onClickSpan 点击事件
     */
    public CSSTextView setTextArrColorStyle(final String text, final int color, int style, int startIndex, final OnClickSpan onClickSpan) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        styledText.setSpan(new StyleSpan(style), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                onClickSpan.onClick(text);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //设置文本的颜色
                ds.setColor(color);
                //超链接形式的下划线，false 表示不
                ds.setUnderlineText(false);
            }
        }, startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(styledText, TextView.BufferType.SPANNABLE);
        setMovementMethod(CustomLinkMovementMethod.getInstance());
        return this;
    }

    /**
     * 设置文本内容其中文字的字体大小，字体样式
     *
     * @param text  内容
     * @param size  字体大小
     * @param style 字体样式
     */
    public CSSTextView setTextArrSizeStyle(String text, int size, int style) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        int startIndex = (getText() + "").indexOf(text);
        if (startIndex > -1) {
            styledText.setSpan(new AbsoluteSizeSpan(DisplayUtils.dp2px(getContext(), size)), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new StyleSpan(style), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(styledText, TextView.BufferType.SPANNABLE);
        }
        return this;
    }

    /**
     * 设置文本内容其中文字的字体大小，字体样式
     *
     * @param text        内容
     * @param size        字体大小
     * @param style       字体样式
     * @param onClickSpan 点击事件
     */
    public CSSTextView setTextArrSizeStyle(final String text, int size, int style, final OnClickSpan onClickSpan) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        int startIndex = (getText() + "").indexOf(text);
        if (startIndex > -1) {
            styledText.setSpan(new AbsoluteSizeSpan(DisplayUtils.dp2px(getContext(), size)), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new StyleSpan(style), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    onClickSpan.onClick(text);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置文本的颜色
                    ds.setColor(getTextColors().getDefaultColor());
                    //超链接形式的下划线，false 表示不
                    ds.setUnderlineText(false);
                }
            }, startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(styledText, TextView.BufferType.SPANNABLE);
            setMovementMethod(CustomLinkMovementMethod.getInstance());
        }
        return this;
    }

    /**
     * 设置文本内容其中文字的字体大小，字体样式
     *
     * @param text       内容
     * @param size       字体大小
     * @param style      字体样式
     * @param startIndex 开始位置
     */
    public CSSTextView setTextArrSizeStyle(String text, int size, int style, int startIndex) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        styledText.setSpan(new AbsoluteSizeSpan(DisplayUtils.dp2px(getContext(), size)), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new StyleSpan(style), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(styledText, TextView.BufferType.SPANNABLE);
        return this;
    }

    /**
     * 设置文本内容其中文字的字体大小，字体样式
     *
     * @param text        内容
     * @param size        字体大小
     * @param style       字体样式
     * @param startIndex  开始位置
     * @param onClickSpan 点击事件
     */
    public CSSTextView setTextArrSizeStyle(final String text, int size, int style, int startIndex, final OnClickSpan onClickSpan) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        styledText.setSpan(new AbsoluteSizeSpan(DisplayUtils.dp2px(getContext(), size)), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new StyleSpan(style), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                onClickSpan.onClick(text);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //设置文本的颜色
                ds.setColor(getTextColors().getDefaultColor());
                //超链接形式的下划线，false 表示不
                ds.setUnderlineText(false);
            }
        }, startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(styledText, TextView.BufferType.SPANNABLE);
        setMovementMethod(CustomLinkMovementMethod.getInstance());
        return this;
    }

    /**
     * 设置文本内容其中文字的字体颜色，字体大小，字体样式
     *
     * @param text  内容
     * @param color 字体颜色
     * @param size  字体大小
     * @param style 字体样式
     */
    public CSSTextView setTextArrColorSizeStyle(String text, int color, int size, int style) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        int startIndex = (getText() + "").indexOf(text);
        if (startIndex > -1) {
            styledText.setSpan(new ForegroundColorSpan(color), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new AbsoluteSizeSpan(DisplayUtils.dp2px(getContext(), size)), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new StyleSpan(style), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(styledText, TextView.BufferType.SPANNABLE);
        }
        return this;
    }

    /**
     * 设置文本内容其中文字的字体颜色，字体大小，字体样式
     *
     * @param text        内容
     * @param color       字体颜色
     * @param size        字体大小
     * @param style       字体样式
     * @param onClickSpan 点击事件
     */
    public CSSTextView setTextArrColorSizeStyle(final String text, final int color, int size, int style, final OnClickSpan onClickSpan) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        int startIndex = (getText() + "").indexOf(text);
        if (startIndex > -1) {
            styledText.setSpan(new AbsoluteSizeSpan(DisplayUtils.dp2px(getContext(), size)), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new StyleSpan(style), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    onClickSpan.onClick(text);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置文本的颜色
                    ds.setColor(color);
                    //超链接形式的下划线，false 表示不
                    ds.setUnderlineText(false);
                }
            }, startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(styledText, TextView.BufferType.SPANNABLE);
            setMovementMethod(CustomLinkMovementMethod.getInstance());
        }
        return this;
    }

    /**
     * 设置文本内容其中文字的字体颜色，字体大小，字体样式
     *
     * @param text       内容
     * @param color      字体颜色
     * @param size       字体大小
     * @param style      字体样式
     * @param startIndex 开始位置
     */
    public CSSTextView setTextArrColorSizeStyle(String text, int color, int size, int style, int startIndex) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        styledText.setSpan(new ForegroundColorSpan(color), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new AbsoluteSizeSpan(DisplayUtils.dp2px(getContext(), size)), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new StyleSpan(style), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(styledText, TextView.BufferType.SPANNABLE);
        return this;
    }

    /**
     * 设置文本内容其中文字的字体颜色，字体大小，字体样式
     *
     * @param text        内容
     * @param color       字体颜色
     * @param size        字体大小
     * @param style       字体样式
     * @param startIndex  开始位置
     * @param onClickSpan 点击事件
     */
    public CSSTextView setTextArrColorSizeStyle(final String text, final int color, int size, int style, int startIndex, final OnClickSpan onClickSpan) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        styledText.setSpan(new AbsoluteSizeSpan(DisplayUtils.dp2px(getContext(), size)), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new StyleSpan(style), startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                onClickSpan.onClick(text);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //设置文本的颜色
                ds.setColor(color);
                //超链接形式的下划线，false 表示不
                ds.setUnderlineText(false);
            }
        }, startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(styledText, TextView.BufferType.SPANNABLE);
        setMovementMethod(CustomLinkMovementMethod.getInstance());
        return this;
    }

    /**
     * 设置文本内容其中文字的点击事件
     *
     * @param text        内容
     * @param onClickSpan 点击事件
     */
    public CSSTextView setTextClick(final String text, final OnClickSpan onClickSpan) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        int startIndex = (getText() + "").indexOf(text);
        if (startIndex > -1) {
            styledText.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    onClickSpan.onClick(text);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置文本的颜色
                    ds.setColor(getTextColors().getDefaultColor());
                    //超链接形式的下划线，false 表示不
                    ds.setUnderlineText(false);
                }
            }, startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(styledText, TextView.BufferType.SPANNABLE);
            setMovementMethod(CustomLinkMovementMethod.getInstance());
        }

        return this;
    }

    /**
     * 设置文本内容其中文字的点击事件
     *
     * @param text        内容
     * @param startIndex  开始位置
     * @param onClickSpan 点击事件
     */
    public CSSTextView setTextClick(final String text, int startIndex, final OnClickSpan onClickSpan) {
        if (getText().length() == 0) {
            throw new NullPointerException("Please Set The textView Content!");
        }
        SpannableString styledText = new SpannableString(getText());
        styledText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                onClickSpan.onClick(text);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //设置文本的颜色
                ds.setColor(getTextColors().getDefaultColor());
                //超链接形式的下划线，false 表示不
                ds.setUnderlineText(false);
            }
        }, startIndex, startIndex + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(styledText, TextView.BufferType.SPANNABLE);
        setMovementMethod(CustomLinkMovementMethod.getInstance());

        return this;
    }

    public interface OnClickSpan {
        void onClick(String text);
    }
}
