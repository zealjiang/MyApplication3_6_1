package com.example.lizhijiang.myapplication;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestSubtitlePositionActivity extends AppCompatActivity {

    private TextView tvPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);

        tvPosition = findViewById(R.id.tvPosition);
        tvPosition.setIncludeFontPadding(false);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"hanyiquhei.ttf");
        tvPosition.setTypeface(typeface);
        replaceFont(typeface);

    }

    protected void replaceFont(final Typeface newTypeface) {
        try {

            Paint paint = tvPosition.getPaint();


            final Field staticField = Typeface.class.getDeclaredField("native_instance");

            staticField.setAccessible(true);
            staticField.set(null, newTypeface);

            final Field nativePaint = Paint.class.getDeclaredField("mNativePaint");
            nativePaint.setAccessible(true);
            nativePaint.set(paint,null);

            Method setTypeface = Paint.class.getMethod("nSetTypeface");
            setTypeface.setAccessible(true);
            setTypeface.invoke(paint,nativePaint,staticField);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
