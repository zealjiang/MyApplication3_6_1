package com.example.lizhijiang.myapplication.lrc;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.lizhijiang.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

public class SoundTextShowActivity extends AppCompatActivity {

    private LRCTextView lrcTextView;
    private float percent = 0;

    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lrc);

        lrcTextView = findViewById(R.id.lrcTv);

        showLrc();
    }

    private void showLrc(){


        Runnable r = new Runnable() {

            @Override
            public void run() {
                //do something
                if(percent >= 1){
                    percent = 0;
                }
                lrcTextView.setPercent(percent);
                percent += 0.01f;
                //每隔1s循环执行run方法
                mHandler.postDelayed(this, 100);
            }
        };

        mHandler.postDelayed(r, 100);//延时100毫秒


/*        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };*/
    }
}
