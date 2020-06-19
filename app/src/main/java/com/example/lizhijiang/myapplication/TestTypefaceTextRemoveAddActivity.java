package com.example.lizhijiang.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lizhijiang.myapplication.thread.LibTaskController;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TestTypefaceTextRemoveAddActivity extends Activity {


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
        test();

        //Typeface typeface = Typeface.createFromAsset(getAssets(),"fangzhengdabiaosongfanti.ttf");
        Typeface typeface = Typeface.createFromAsset(getAssets(),"Barrio-Regular.ttf");
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

        LibTaskController.postDelayed(new Runnable() {
            @Override
            public void run() {
                testUiLongTask();
            }
        },1000);

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


        MediaData mediaData = new MediaData();
        mediaData.mlstXunfeiSubtilteIds.add(1);
        mediaData.mlstXunfeiSubtilteIds.add(3);
        mediaData.mlstXunfeiSubtilteIds.add(5);

        XFSubtitleVoiceTransfer xfSubtitleVoiceTransfer = new XFSubtitleVoiceTransfer();
        xfSubtitleVoiceTransfer.mlstXunfeiSubtilteIds = mediaData.mlstXunfeiSubtilteIds;

        for (int i = 0; i < xfSubtitleVoiceTransfer.mlstXunfeiSubtilteIds.size(); i++) {
            Log.d("mtest",i+"  : "+xfSubtitleVoiceTransfer.mlstXunfeiSubtilteIds.get(i));
        }

        xfSubtitleVoiceTransfer.mlstXunfeiSubtilteIds.remove((Object)3);
        Log.d("mtest"," ---------");
        for (int i = 0; i < xfSubtitleVoiceTransfer.mlstXunfeiSubtilteIds.size(); i++) {
            Log.d("mtest",i+"  : "+xfSubtitleVoiceTransfer.mlstXunfeiSubtilteIds.get(i));
        }
        Log.d("mtest"," ---------");
        for (int i = 0; i < mediaData.mlstXunfeiSubtilteIds.size(); i++) {
            Log.d("mtest",i+"  : "+mediaData.mlstXunfeiSubtilteIds.get(i));
        }
    }

    public class MediaData{
        public List<Integer> mlstXunfeiSubtilteIds = new ArrayList<>();
    }
    public class XFSubtitleVoiceTransfer{
        public List<Integer> mlstXunfeiSubtilteIds = new ArrayList<>();
    }

    private void testUiLongTask(){
        LibTaskController.run(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    final int ii = i;
                    LibTaskController.post(new Runnable() {
                        @Override
                        public void run() {
                            TextView v = new TextView(TestTypefaceTextRemoveAddActivity.this);
                            rl1.addView(v);
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Log.d("mtest","addview :"+ii);
                        }
                    });

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
