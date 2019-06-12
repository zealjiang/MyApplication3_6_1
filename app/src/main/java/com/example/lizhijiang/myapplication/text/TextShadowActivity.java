package com.example.lizhijiang.myapplication.text;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;

import com.example.lizhijiang.myapplication.R;

public class TextShadowActivity extends AppCompatActivity {


    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_shadow);

        textView = findViewById(R.id.tv1);

        textView.setTextColor(Color.parseColor("#00fff000"));
        textView.setShadowLayer(1,0,0, Color.parseColor("#99ff0000"));
    }
}
