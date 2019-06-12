package com.example.lizhijiang.myapplication.lrc;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * https://www.cnblogs.com/dame/p/8085983.html
 * https://developer.android.com/reference/android/graphics/PorterDuff.Mode
 * https://blog.csdn.net/u013278099/article/details/50881431
 *         作者：Losileeya
 来源：CSDN
 原文：https://blog.csdn.net/u013278099/article/details/50881431?utm_source=copy
 版权声明：本文为博主原创文章，转载请附上博文链接！
 */
public class SongTextView extends View {
    private int postIndex;
    private Paint mPaint;
    private int delta = 15;
    private float mTextHeight;
    private float mTextWidth;
    private String mText="梦 里 面 看 我 七 十 二 变";
    private PorterDuffXfermode xformode;
    private Context context;
    private float baselineGap;
    public SongTextView(Context ctx)
    {
        this(ctx,null);
    }
    public SongTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SongTextView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }
    public void init()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        xformode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        mPaint.setColor(Color.CYAN);
        mPaint.setTextSize(60.0f);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setXfermode(null);
        //文字精确高度
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        mTextHeight = fontMetrics.descent-fontMetrics.ascent;
        mTextWidth  = mPaint.measureText(mText);
        baselineGap = -fontMetrics.ascent;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final  int mWidth;
        final  int mHeight;

        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == View.MeasureSpec.EXACTLY)// match_parent , accurate
            mWidth = widthSize;
        else
        {
            // 由图片决定的宽
            int desireByImg = getPaddingLeft() + getPaddingRight()
                    + (int)mTextWidth;
            if (widthMode == View.MeasureSpec.AT_MOST)// wrap_content
                mWidth = Math.min(desireByImg, widthSize);
            else
                mWidth = desireByImg;
        }

        int   heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int   heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY)// match_parent , accurate
            mHeight = heightSize;
        else
        {
            int desire = getPaddingTop() + getPaddingBottom()
                    + (int)mTextHeight;
            if (heightMode == MeasureSpec.AT_MOST)// wrap_content
                mHeight = Math.min(desire, heightSize);
            else
                mHeight = desire;
        }
        setMeasuredDimension( mWidth,  mHeight );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float left = (getScreenWidth(context) - mTextWidth)/2;

        //Bitmap srcBitmap = Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Bitmap srcBitmap = Bitmap.createBitmap((int)mTextWidth,(int)mTextHeight, Bitmap.Config.ARGB_8888);
        Canvas srcCanvas = new Canvas(srcBitmap);
        srcCanvas.drawText(mText, 0, baselineGap, mPaint);
        mPaint.setXfermode(xformode);
        mPaint.setColor(Color.RED);
        RectF rectF = new RectF(0,0,postIndex,mTextHeight);
        srcCanvas.drawRect(rectF, mPaint);
        canvas.drawBitmap(srcBitmap, 0, 0, null);
        init();
        if(postIndex<mTextWidth)
        {
            postIndex+=10;
        }else{
            postIndex=0;
        }
        postInvalidateDelayed(30);
    }

    public static int getScreenWidth(Context mContext) {
        try {
            // 获取屏幕信息
            DisplayMetrics dMetrics = getDisplayMetrics(mContext);
            if (dMetrics != null) {
                return dMetrics.widthPixels;
            }
            // 这种也可以获取，不过已经提问过时(下面这段可以注释掉)
            WindowManager wManager = getWindowManager(mContext);
            if (wManager != null) {
                return wManager.getDefaultDisplay().getWidth();
            }
        } catch (Exception e) {
        }
        return -1;
    }

    public static DisplayMetrics getDisplayMetrics(Context mContext) {
        try {
            WindowManager wManager = getWindowManager(mContext);
            if (wManager != null) {
                DisplayMetrics dMetrics = new DisplayMetrics();
                wManager.getDefaultDisplay().getMetrics(dMetrics);
                return dMetrics;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static WindowManager getWindowManager(Context mContext) {
        try {
            return (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        } catch (Exception e) {
        }
        return null;
    }
}

