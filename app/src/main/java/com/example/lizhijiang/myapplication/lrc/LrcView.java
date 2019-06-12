package com.example.lizhijiang.myapplication.lrc;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Looper;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import com.example.lizhijiang.myapplication.R;
import com.example.lizhijiang.myapplication.util.ScreenUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 歌词
 * Created by wcy on 2015/11/9.
 */
public class LrcView extends View {

    private List<LrcEntry> mLrcEntryList = new ArrayList<>();
    private TextPaint mLrcPaint = new TextPaint();
    private TextPaint mTimePaint = new TextPaint();
    private Paint.FontMetrics mTimeFontMetrics;
    private Drawable mPlayDrawable;
    private float mDividerHeight;
    private long mAnimationDuration;
    private int mNormalTextColor;
    private int mCurrentTextColor;
    private int mTimelineColor;
    private int mTimeTextColor;
    private int mDrawableWidth;
    private int mTimeTextWidth;
    private String mDefaultLabel;
    private float mLrcPadding;
    private ValueAnimator mAnimator;
    private GestureDetector mGestureDetector;
    private Scroller mScroller;
    private float mOffset;
    private int mCurrentLine;
    private Object mFlag;
    private boolean isShowTimeline;
    private boolean isTouching;
    private boolean isFling;
    private int mTextGravity = 2;//歌词显示位置，靠左/居中/靠右
    /**
     * 是否将中间的Item放大
     */
    private boolean mIsZoomInSelectedItem = true;
    private float lrcTextSize;
    private float addedSize = 0.7f;
    private float diffAlpha = 0.15f;
    /**
     * 字体渐变，开启后越靠近边缘，字体越模糊
     */
    private boolean mIsTextGradual = true;
    /**
     * 是否显示幕布中间的线
     */
    private boolean mIsShowCenterLine = true;
    /**
     * 允许超出滑动区域的最大高度
     */
    private int maxExceedTopAndBottomHeight;


    public LrcView(Context context) {
        this(context, null);
    }

    public LrcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LrcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.LrcView);
        lrcTextSize = ta.getDimension(R.styleable.LrcView_lrcTextSize, getResources().getDimension(R.dimen.lrc_text_size));
        mDividerHeight = ta.getDimension(R.styleable.LrcView_lrcDividerHeight, getResources().getDimension(R.dimen.lrc_divider_height));
        int defDuration = getResources().getInteger(R.integer.lrc_animation_duration);
        mAnimationDuration = ta.getInt(R.styleable.LrcView_lrcAnimationDuration, defDuration);
        mAnimationDuration = (mAnimationDuration < 0) ? defDuration : mAnimationDuration;
        mNormalTextColor = ta.getColor(R.styleable.LrcView_lrcNormalTextColor, getResources().getColor(R.color.lrc_normal_text_color));
        mCurrentTextColor = ta.getColor(R.styleable.LrcView_lrcCurrentTextColor, getResources().getColor(R.color.lrc_current_text_color));
        mDefaultLabel = ta.getString(R.styleable.LrcView_lrcLabel);
        mDefaultLabel = TextUtils.isEmpty(mDefaultLabel) ? getContext().getString(R.string.lrc_label) : mDefaultLabel;
        mLrcPadding = ta.getDimension(R.styleable.LrcView_lrcPadding, 0);
        mTimelineColor = ta.getColor(R.styleable.LrcView_lrcTimelineColor, getResources().getColor(R.color.lrc_timeline_color));
        float timelineHeight = ta.getDimension(R.styleable.LrcView_lrcTimelineHeight, getResources().getDimension(R.dimen.lrc_timeline_height));
        mTimeTextColor = ta.getColor(R.styleable.LrcView_lrcTimeTextColor, getResources().getColor(R.color.lrc_time_text_color));
        float timeTextSize = ta.getDimension(R.styleable.LrcView_lrcTimeTextSize, getResources().getDimension(R.dimen.lrc_time_text_size));
        ta.recycle();

        mDrawableWidth = (int) getResources().getDimension(R.dimen.lrc_drawable_width);
        mTimeTextWidth = (int) getResources().getDimension(R.dimen.lrc_time_width);

        mLrcPaint.setAntiAlias(true);
        mLrcPaint.setTextSize(lrcTextSize);
        mLrcPaint.setTextAlign(Paint.Align.LEFT);
        mTimePaint.setAntiAlias(true);
        mTimePaint.setTextSize(timeTextSize);
        mTimePaint.setTextAlign(Paint.Align.CENTER);
        //noinspection SuspiciousNameCombination
        mTimePaint.setStrokeWidth(timelineHeight);
        mTimePaint.setStrokeCap(Paint.Cap.ROUND);
        mTimeFontMetrics = mTimePaint.getFontMetrics();

        mGestureDetector = new GestureDetector(getContext(), mSimpleOnGestureListener);
        mGestureDetector.setIsLongpressEnabled(false);
        mScroller = new Scroller(getContext());

        addedSize = ScreenUtils.dipConvertPx(getContext(),addedSize);
    }

    public void setNormalColor(int normalColor) {
        mNormalTextColor = normalColor;
        postInvalidate();
    }

    public void setCurrentColor(int currentColor) {
        mCurrentTextColor = currentColor;
        postInvalidate();
    }

    public void setTimelineColor(int timelineColor) {
        mTimelineColor = timelineColor;
        postInvalidate();
    }

    public void setTimeTextColor(int timeTextColor) {
        mTimeTextColor = timeTextColor;
        postInvalidate();
    }



    /**
     * 歌词是否有效
     *
     * @return true，如果歌词有效，否则false
     */
    public boolean hasLrc() {
        return !mLrcEntryList.isEmpty();
    }


    /**
     * 加载歌词文件
     *
     */
    public void loadLrc() {
        runOnUi(new Runnable() {
            @Override
            public void run() {
                reset();
            }
        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(maxExceedTopAndBottomHeight == 0){
            maxExceedTopAndBottomHeight = getHeight() / 4;
        }

        float centerY = getHeight() / 2;

        int centerLine = getCenterLine();

        if(mIsShowCenterLine){
/*            canvas.save();
            mLrcPadding = 0;
            canvas.translate(mLrcPadding, y - staticLayout.getHeight() / 2);
            staticLayout.draw(canvas);
            canvas.restore();*/

            //画中间的线
            mLrcPaint.setColor(mTimelineColor);
            canvas.drawLine(mLrcPadding,centerY,getRight()-mLrcPadding,centerY,mLrcPaint);
        }

        canvas.translate(0, mOffset);

        centerY = centerY * 1.2f;
        float y = 0;
        for (int i = 0; i < mLrcEntryList.size(); i++) {
            if (i > 0) {
                y += (mLrcEntryList.get(i - 1).getHeight() + mLrcEntryList.get(i).getHeight()) / 2 + mDividerHeight;
            }
            if (i == centerLine) {
                mLrcPaint.setColor(mCurrentTextColor);
            } else {
                mLrcPaint.setColor(mNormalTextColor);
            }

            //距离中心的Y轴距离
            float distanceY = Math.abs(mOffset - getOffset(i)); //Math.abs(mOffset);
            float multi = distanceY / centerY;

            //Log.d("test","onDraw i: "+ i+ " multi: "+multi+" distanceY: "+distanceY+" centerY: "+centerY);
            multi = multi > 1 ? 1 : multi;

            //开启此选项,会将越靠近中心的Item字体放大
            if (mIsZoomInSelectedItem) {
/*
                if (i == centerLine) {
                    mLrcPaint.setTextSize(lrcTextSize);
                } else {
                    int diff = Math.abs(i - centerLine);
                    mLrcPaint.setTextSize(diff);
                }
*/
                float fontSize = lrcTextSize - multi*lrcTextSize;
                fontSize = fontSize < 10 ? 10 :fontSize;
                mLrcPaint.setTextSize(fontSize);
            }

            if (mIsTextGradual) {
                //文字颜色渐变要在设置透明度上边，否则会被覆盖

                //计算透明度渐变
                float alphaRatio;
/*                if (i == centerLine) {
                    alphaRatio = 1;
                } else {
                    int diff = Math.abs(i - centerLine);
                    alphaRatio = 1 - diff * diffAlpha;
                }*/

                alphaRatio = 255 - multi*255;
                alphaRatio = alphaRatio < 0 ? 0 :alphaRatio;
                mLrcPaint.setAlpha((int)alphaRatio);
            }

            drawText(canvas, mLrcEntryList.get(i).getStaticLayout(), y);
        }
    }

    /**
     * 画一行歌词
     *
     * @param y 歌词中心 Y 坐标
     */
    private void drawText(Canvas canvas, StaticLayout staticLayout, float y) {
        canvas.save();
        mLrcPadding = 0;
        canvas.translate(mLrcPadding, y - staticLayout.getHeight() / 2);
        staticLayout.draw(canvas);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            isTouching = false;

            if (hasLrc() && !isFling) {
                scrollToTopOrBottom();

                //postDelayed(hideTimelineRunnable, 100);
            }


            //float top = Math.min(mOffset, getOffset(0));
            //float bottom = Math.max(mOffset, getOffset(mLrcEntryList.size() - 1));

/*            Log.d("test"," isFling: "+isFling);
            if (hasLrc() && !isFling) {
                adjustCenter();
                postDelayed(hideTimelineRunnable, 100);
            }*/
        }
        return mGestureDetector.onTouchEvent(event);
    }

    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            if (hasLrc()) {
                mScroller.forceFinished(true);
                removeCallbacks(hideTimelineRunnable);
                isTouching = true;
                isShowTimeline = true;
                invalidate();
                return true;
            }
            return super.onDown(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (hasLrc()) {
                mOffset += -distanceY;
                mOffset = Math.min(mOffset, getOffset(0) + maxExceedTopAndBottomHeight);
                mOffset = Math.max(mOffset, getOffset(mLrcEntryList.size() - 1) - maxExceedTopAndBottomHeight);
                //Log.d("test","onScroll mOffset: "+mOffset);
                invalidate();
                return true;
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (hasLrc()) {
                //mScroller.fling(0, (int) mOffset, 0, (int) velocityY, 0, 0, (int) getOffset(mLrcEntryList.size() - 1), (int) getOffset(0));
                mScroller.fling(0, (int) mOffset, 0, (int) velocityY, 0, 0,
                        (int) getOffset(mLrcEntryList.size() - 1) - maxExceedTopAndBottomHeight,
                        (int) getOffset(0)+maxExceedTopAndBottomHeight);
                isFling = true;
//                Log.d("test"," onFling  startY: "+mOffset+" minY: "+(int) getOffset(mLrcEntryList.size() - 1)+ " maxY: "+(int) getOffset(0));
                return true;
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (hasLrc()) {
                int centerLine = getCenterLine();

                // onPlayClick 消费了才更新 UI
                isShowTimeline = false;
                removeCallbacks(hideTimelineRunnable);
                mCurrentLine = centerLine;
                invalidate();
                return true;
            }
            return super.onSingleTapConfirmed(e);
        }


    };

    private Runnable hideTimelineRunnable = new Runnable() {
        @Override
        public void run() {
            if (hasLrc()) {
                scrollTo(mCurrentLine);
            }
        }
    };

    private void scrollToTopOrBottom(){
        float top = getOffset(0);
        float bottom = getOffset(mLrcEntryList.size() - 1);
        //Log.d("test"," onTouchEvent top: "+top+" bottom: "+bottom+"  mOffset: "+mOffset);
        if(mOffset > top){
            //回到第一行
            scrollTo(0,100l,new AccelerateInterpolator());
        }else if(mOffset < bottom){
            //回到最后一行
            scrollTo(mLrcEntryList.size() - 1,100l,new AccelerateInterpolator());
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mOffset = mScroller.getCurrY();
            invalidate();
        }
        if (isFling && mScroller.isFinished()) {
            isFling = false;
            if (hasLrc() && !isTouching) {
                scrollToTopOrBottom();
            }
        }
        Log.d("test","computeScroll mOffset: "+mOffset);
/*        if (isFling && mScroller.isFinished()) {
            isFling = false;
            if (hasLrc() && !isTouching) {
                adjustCenter();
                postDelayed(hideTimelineRunnable,100);
            }
        }*/
    }

    @Override
    protected void onDetachedFromWindow() {
        removeCallbacks(hideTimelineRunnable);
        super.onDetachedFromWindow();
    }

    public void onLrcLoaded(List<LrcEntry> entryList) {
        if (entryList != null && !entryList.isEmpty()) {
            mLrcEntryList.addAll(entryList);
        }

        initEntryList();
        invalidate();
    }

    private void initEntryList() {


        for (LrcEntry lrcEntry : mLrcEntryList) {
            lrcEntry.init(mLrcPaint, (int) getLrcWidth(), mTextGravity);
        }

        mOffset = getHeight() / 2;
        Log.d("test"," initEntryList mOffset: "+mOffset);

        if(maxExceedTopAndBottomHeight == 0){
            maxExceedTopAndBottomHeight = getHeight() / 4;
        }
    }

    public void reset() {
        endAnimation();
        mScroller.forceFinished(true);
        isShowTimeline = false;
        isTouching = false;
        isFling = false;
        removeCallbacks(hideTimelineRunnable);
        mLrcEntryList.clear();
        mOffset = 0;
        mCurrentLine = 0;
        invalidate();
    }

    /**
     * 滚动到某一行
     */
    private void scrollTo(int line) {
        scrollTo(line, mAnimationDuration,null);
    }

    /**
     * 将中心行微调至正中心
     */
    private void adjustCenter() {
        scrollTo(getCenterLine());
    }

    private void scrollTo(int line, long duration, TimeInterpolator timeInterpolator) {
        if(timeInterpolator == null){
            timeInterpolator = new LinearInterpolator();
        }
        float offset = getOffset(line);
        endAnimation();

        Log.d("test","scrollTo mOffset: "+mOffset);
        mAnimator = ValueAnimator.ofFloat(mOffset, offset);
        mAnimator.setDuration(duration);
        mAnimator.setInterpolator(timeInterpolator);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (float) animation.getAnimatedValue();
                Log.d("test","scrollTo onAnimationUpdate mOffset: "+mOffset);
                invalidate();
            }
        });
        mAnimator.start();
    }

    private void endAnimation() {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.end();
        }
    }

    /**
     * 找出距mOffset最近的对应字幕的position
     * @return
     */
    private int getCenterLine() {
        int centerLine = 0;
        float minDistance = Float.MAX_VALUE;
        for (int i = 0; i < mLrcEntryList.size(); i++) {
            if (Math.abs(mOffset - getOffset(i)) < minDistance) {
                minDistance = Math.abs(mOffset - getOffset(i));
                centerLine = i;
            }
        }
        return centerLine;
    }

    private float getOffset(int line) {
        if (mLrcEntryList.get(line).getOffset() == Float.MIN_VALUE) {
            float offset = getHeight() / 2;
            for (int i = 1; i <= line; i++) {
                offset -= (mLrcEntryList.get(i - 1).getHeight() + mLrcEntryList.get(i).getHeight()) / 2 + mDividerHeight;
            }
            mLrcEntryList.get(line).setOffset(offset);
        }

        return mLrcEntryList.get(line).getOffset();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            initEntryList();
        }
    }

    private float getLrcWidth() {
        return getWidth() - mLrcPadding * 2;
    }

    private void runOnUi(Runnable r) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            r.run();
        } else {
            post(r);
        }
    }

    private Object getFlag() {
        return mFlag;
    }

    private void setFlag(Object flag) {
        this.mFlag = flag;
    }
}
