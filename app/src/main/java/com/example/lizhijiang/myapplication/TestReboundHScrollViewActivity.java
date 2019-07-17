package com.example.lizhijiang.myapplication;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.example.lizhijiang.myapplication.util.ScrollviewUITools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestReboundHScrollViewActivity extends AppCompatActivity {

    private com.example.lizhijiang.myapplication.view.ReboundHScrollView rbhscrollview;
    private HorizontalScrollView hscrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebound_hscrollview);

        rbhscrollview = findViewById(R.id.rbhscrollview);
        hscrollview = findViewById(R.id.hscrollview);

        rbhscrollview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rbhscrollview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = rbhscrollview.getWidth();

                View child = rbhscrollview.getChildAt(0);
                int childWidth = child.getWidth();
                Log.d("mtest","rbhscrollview width: "+width+" childW: "+childWidth);
                //int scrollRange = Math.max(0, child.getWidth() - (width - mPaddingLeft - mPaddingRight));
            }
        });

        ScrollviewUITools.elasticPadding(hscrollview,300);
    }


}
