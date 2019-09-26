package com.example.lizhijiang.myapplication.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * OpenGLView类继承自GLSurfaceView，这个类是android提供的用opengl画图的类。
 */
public class OpenGLView extends GLSurfaceView {

    private OpenGLRenderer mRenderer;

    public OpenGLView(Context context) {

        super(context);

        mRenderer = new OpenGLRenderer();

        setRenderer(mRenderer);

    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        /*
         * 由于渲染对象是运行在一个独立的渲染线程中，所以需要采用跨线程的机制来进行事件的处理。
         * 但是Android提供了一个简便的方法:
         * 我们只需要在事件处理中使用queueEvent(Runnable)就可以了.
         */
        queueEvent(new Runnable() {
            @Override
            public void run() {
                mRenderer.setColor(event.getX() / getWidth(), event.getY()
                        / getHeight(), 1.0f);
            }
        });

        return super.onTouchEvent(event);
    }
}
