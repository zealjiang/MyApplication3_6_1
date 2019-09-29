package com.example.lizhijiang.myapplication.text;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.lizhijiang.myapplication.R;

public class TextClipRectActivity extends AppCompatActivity {


    com.example.lizhijiang.myapplication.view.ClipRectView clipRect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_rect);

        clipRect = findViewById(R.id.clipRect);

    }
}
