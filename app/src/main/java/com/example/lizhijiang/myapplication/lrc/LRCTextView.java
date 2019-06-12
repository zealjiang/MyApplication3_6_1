package com.example.lizhijiang.myapplication.lrc;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * https://blog.csdn.net/jflex/article/details/46550849
 */
public class LRCTextView extends RelativeLayout {
    private TextView tvDefault;
    private TextView tvSelect;
    private String lrc = "我是测试歌词 我是测试歌词 我是测试歌词";

    /**
     * 设置歌词
     *
     * @param lrc
     */
    public void setLrc(String lrc) {
        this.lrc = lrc;
        tvDefault.setText(lrc);
        tvSelect.setText(lrc);
    }

    public LRCTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LRCTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LRCTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        tvDefault = new TextView(getContext());
        tvDefault.setText(lrc);
        tvDefault.setTextColor(Color.parseColor("#726463"));
        tvDefault.setEllipsize(null);
        tvDefault.setSingleLine();
        tvDefault.setTextSize(16);

        tvSelect = new TextView(getContext());
        tvSelect.setTextColor(Color.parseColor("#39DF7C"));
        tvSelect.setText(lrc);
        tvSelect.setEllipsize(null);
        tvSelect.setSingleLine();
        tvSelect.setTextSize(16);
        addView(tvDefault);
        addView(tvSelect);
        tvSelect.setWidth(0);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == View.VISIBLE) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    setPercent(percent);
                }
            }, 30);
        }
    }

    private float percent;

    /**
     * 设置颜色渐变百分比
     *
     * @param percent
     */
    public void setPercent(float percent) {
        this.percent = percent;
        setSelectWidth((int) (getSelectWidth() * percent));
    }

    private int getSelectWidth() {
        return tvDefault.getWidth();
    }

    private void setSelectWidth(int pixels) {
        if (pixels <= getSelectWidth()) {
            tvSelect.setWidth(pixels);
        }
    }
}
