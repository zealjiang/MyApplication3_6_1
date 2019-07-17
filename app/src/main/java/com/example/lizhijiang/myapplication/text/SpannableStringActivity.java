package com.example.lizhijiang.myapplication.text;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.example.lizhijiang.myapplication.R;

public class SpannableStringActivity extends AppCompatActivity {


    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable_string);

        tv1 = findViewById(R.id.tv1);

        String content = "内容测试";
        tv1.setText(content);
        int start = 0;
        int end = content.length();
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new UnderlineSpan(){
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#fec90d"));
                ds.setUnderlineText(true);
            }

        },start,end,SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        tv1.setText(spannableString);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
