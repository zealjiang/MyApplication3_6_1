package com.example.lizhijiang.myapplication;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaExtractor;
import android.media.MediaMuxer;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TestSubtitleTypefaceActivity extends AppCompatActivity {

    private RelativeLayout rlRootView;
    private TextView tv1;
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typeface);

        rlRootView = findViewById(R.id.rlRootView);
        tv1 = findViewById(R.id.tv1);
        //tv2 = findViewById(R.id.tv2);


        Typeface typeface = Typeface.createFromAsset(getAssets(),"fangzhengdabiaosongfanti.ttf");
        tv1.setTypeface(typeface);
        tv1.setIncludeFontPadding(false);


        TextView tv2 = new TextView(this);
        tv2.setTypeface(typeface);
        tv2.setText(tv1.getText());
        tv2.setTextSize(30);
        tv2.setTextColor(Color.parseColor("#000000"));

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = (int)(getResources().getDisplayMetrics().density * 15);
        params.addRule(RelativeLayout.BELOW,R.id.tv1);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tv2.setLayoutParams(params);

        rlRootView.addView(tv2);

    }
}
