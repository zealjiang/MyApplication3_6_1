package com.example.lizhijiang.myapplication;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WordartActivity extends Activity {

    private RelativeLayout rlRootView;
    private TextView tvArt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordart);

        tvArt = findViewById(R.id.tvArt);
        //setArt();
    }

    private void setArt(){
        TextPaint textPaint = tvArt.getPaint();
        textPaint.setAntiAlias(true);

        //系统默认不支持描边，需要自己去用一个t
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setStrokeWidth(0);

    }

}
