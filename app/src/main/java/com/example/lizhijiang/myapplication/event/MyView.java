package com.example.lizhijiang.myapplication.event;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

public class MyView extends TextView {

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("event","MyView : dispatchTouchEvent "+this.getText().toString());
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
                break;
        }
        Log.i("event","MyView : onTouchEvent "+str+"  " +this.getText().toString());
        return super.onTouchEvent(event);
    }

}
