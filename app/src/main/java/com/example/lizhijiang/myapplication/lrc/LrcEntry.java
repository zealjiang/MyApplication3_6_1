package com.example.lizhijiang.myapplication.lrc;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.format.DateUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hzwangchenyan on 2016/10/19.
 */
class LrcEntry {
    private String text;
    private StaticLayout staticLayout;
    private float offset = Float.MIN_VALUE;
    public static final int GRAVITY_LEFT = 1;
    public static final int GRAVITY_CENTER = 2;
    public static final int GRAVITY_RIGHT = 3;

    public LrcEntry( String text) {
        this.text = text;
    }

    void init(TextPaint paint, int width, int gravity) {
        Layout.Alignment align;
        switch (gravity) {
            case GRAVITY_LEFT:
                align = Layout.Alignment.ALIGN_LEFT;
                break;

            default:
            case GRAVITY_CENTER:
                align = Layout.Alignment.ALIGN_CENTER;
                break;

            case GRAVITY_RIGHT:
                align = Layout.Alignment.ALIGN_OPPOSITE;
                break;
        }
        staticLayout = new StaticLayout(text, paint, width, align, 1f, 0f, false);
    }


    StaticLayout getStaticLayout() {
        return staticLayout;
    }

    int getHeight() {
        if (staticLayout == null) {
            return 0;
        }
        return staticLayout.getHeight();
    }

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }
}
