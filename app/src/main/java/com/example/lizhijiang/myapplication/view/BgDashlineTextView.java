package com.example.lizhijiang.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class BgDashlineTextView extends android.support.v7.widget.AppCompatTextView {
    public BgDashlineTextView(Context context) {
        super(context);
    }

    public BgDashlineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BgDashlineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDashLine(canvas);
    }


    //下面用来画虚线
    private Paint mPaint;
    private Path mPath;
    public boolean isShowDashLine = true;

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#000000"));
        // 需要加上这句，否则画不出东西
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        //mPaint.setPathEffect(new DashPathEffect(new float[] {15, 5}, 0));

        mPath = new Path();
    }

    private void drawDashLine(Canvas canvas){
        if(canvas == null)return;
        if(mPaint == null){
            initPaint();
        }
        if(isShowDashLine) {
            int width = getWidth();
            int height = getHeight();

            Log.d("mtest","drawDashLine width:"+width+"  height: "+height);
            mPath.reset();
            mPath.moveTo(0, 10);
            mPath.lineTo(width, 10);
            mPath.lineTo(width, height);
            mPath.lineTo(0, height);
            mPath.lineTo(0, 10);
            canvas.drawPath(mPath, mPaint);

            //canvas.drawRect(new Rect(8500,0,8800,height),mPaint);
        }
    }
}
