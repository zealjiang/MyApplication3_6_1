package com.example.lizhijiang.myapplication;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class TestTypefaceTextRemoveAddActivity extends AppCompatActivity {


    private RelativeLayout rl1;
    private RelativeLayout rl2;
    private RelativeLayout rlTv;
    private RelativeLayout rlActionBox;
    private TextView tv;
    private Button btn1;
    private Button btn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_typeface_add_remove);

        rl1 = findViewById(R.id.rl1);
        rl2 = findViewById(R.id.rl2);
        rlTv = findViewById(R.id.rlTv);
        rlActionBox = findViewById(R.id.rlActionBox);
        tv = findViewById(R.id.tv);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);


        Typeface typeface = Typeface.createFromAsset(getAssets(),"fangzhengdabiaosongfanti.ttf");
        tv.setTypeface(typeface);
        tv.setIncludeFontPadding(false);

        rlActionBox.setScaleX(4);
        rlActionBox.setScaleY(4);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToRl2();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToRl1();
            }
        });
    }

    private void addToRl2(){
        //外面的父控件移除此view
        ((RelativeLayout)rlTv.getParent()).removeView(rlTv);

        //设置位置为父控件的中心位置
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rlTv.getLayoutParams();
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        rlTv.setLayoutParams(params);

        //当前的父控件添加此view
        rl2.addView(rlTv);

        rlTv.setLayerType(View.LAYER_TYPE_SOFTWARE,null);

    }

    private void addToRl1(){
        //外面的父控件移除此view
        ((RelativeLayout)rlTv.getParent()).removeView(rlTv);

        //当前的父控件添加此view
        rlActionBox.addView(rlTv);

        rlTv.setLayerType(View.LAYER_TYPE_NONE,null);
    }

    public void test(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

            }
        };
        timer.schedule(timerTask,0,100);
    }
}
