package com.example.lizhijiang.myapplication;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.widget.TextView;

public class ProjectionActivity extends AppCompatActivity {

    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projection);

        tv = findViewById(R.id.tv);

        //设置描边
        TextPaint textPaint = tv.getPaint();
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setStrokeWidth(70);


        tv.setTextColor(Color.parseColor("#00FFFFFF"));
        //设置投影
        tv.setShadowLayer(6,0,0, Color.parseColor("#feff0000"));
    }
}
