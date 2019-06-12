package com.example.lizhijiang.myapplication.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class RelativelayoutA extends RelativeLayout {

    public RelativelayoutA(Context context) {
        super(context);
    }

    public RelativelayoutA(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RelativelayoutA(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("event","RelativeLayoutA : dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("event","RelativeLayoutA : onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("event","RelativeLayoutA : onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }
}
