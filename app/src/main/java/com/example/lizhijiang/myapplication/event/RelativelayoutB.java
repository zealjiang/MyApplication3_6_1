package com.example.lizhijiang.myapplication.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class RelativelayoutB extends RelativeLayout {

    private boolean isSelected = false;

    public RelativelayoutB(Context context) {
        super(context);
    }

    public RelativelayoutB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RelativelayoutB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("event","RelativeLayoutB : dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        String str = "";
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                str = " ACTION_DOWN ";
                break;
            case MotionEvent.ACTION_MOVE:
                str = " ACTION_MOVE ";
                break;
            case MotionEvent.ACTION_UP:
                str = " ACTION_UP ";
                //Log.i("event","RelativeLayoutB : onTouchEvent "+str);
                break;
        }

        Log.i("event","RelativeLayoutB : onTouchEvent "+str);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("event","RelativeLayoutB : onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }
}
