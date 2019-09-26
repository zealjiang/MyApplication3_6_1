package com.example.lizhijiang.myapplication.opengl;

import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * 渲染器类，实现了GLSurfaceView.Renderer接口，实现这个接口，需要实现3个方法：OnSurfaceCreated(),
 * OnSurfaceChanged(),OnDrawFrame()。
 */
public class OpenGLRenderer implements GLSurfaceView.Renderer {


    private float cr,cg,cb;

    // (这是附加的)设置RGB颜色的方法
    public void setColor(float r, float g, float b) {
        cr = r;
        cg = g;
        cb = b;
    }

    /**
     * 该方法是画图方法，类似于View类的OnDraw()，一般所有的画图操作都在这里实现。
     */
    @Override
    public void onDrawFrame(GL10 gl) {
        // 清除屏幕和深度缓存。
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(cr, cg, cb, 0.0f);

        // 重置当前的模型观察矩阵。
        gl.glLoadIdentity();

        //Log.d("mtest","onDrawFrame");

/*        drawTriangle(gl);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // 重置当前的模型观察矩阵。
        gl.glLoadIdentity();
        // 沿着X轴左移1.5个单位，Y轴不动(0.0f)，最后移入屏幕6.0f个单位
        gl.glTranslatef(0f, 0.0f, -6.0f);*/


        drawRect(gl);
    }

    /**
     * 在Surface创建的时候调用,一般在这里做一个初始化openggl的操作
     * @param gl
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {
        // 启用smooth shading（阴影平滑）。阴影平滑通过多边形精细的混合色彩，并对外部光进行平滑。
        gl.glShadeModel(GL10.GL_SMOOTH);

        // 设置清除屏幕时所用的颜色，参数对应(红,绿,蓝,Alpha值)。色彩值的范围从0.0f到1.0f。0.0f代表最黑的情况，1.0f就是最亮的情况。
        gl.glClearColor(1.0f, 0f, 0f, 0f);

        // 下面三行是关于depth buffer(深度缓存)的。将深度缓存设想为屏幕后面的层。深度缓存不断的对物体进入屏幕内部有多深进行跟踪。
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);

        // 这里告诉OpenGL我们希望进行最好的透视修正。这会十分轻微的影响性能。但使得透视图看起来好一点。
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);


        mTriangleBuffer = BufferUtil.floatToBuffer(mTriangleArray);

        mQuadsBuffer = BufferUtil.floatToBuffer(mQuadsArray);

    }

    /**
     * 在Surface发生改变的时候调用，例如从竖屏切换到横屏的时候
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 设置输出屏幕大小
        gl.glViewport(0, 0, width, height);

        //透视
        float ratio = (float) width*1.0f / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);

        Log.d("mtest","onSurfaceChanged   width: "+width+"   height: "+height);
    }


    private FloatBuffer mTriangleBuffer;

    private float[] mTriangleArray = {
            0f, 1f, 0f,   // 是上顶点
            -1f, -1f, 0f, // 左下顶点
            1f, -1f, 0f   // 右下顶点
    };

    private void drawTriangle(GL10 gl){
        // 利用数组模型来画模型，要加此句(原文中没有)
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // 三角形的颜色为红色，透明度为不透明
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);

        // 设置顶点，第一个参数是坐标的维数，这里是3维，第二个参数，表示buffer里面放的是float，第三个参数是0，
        // 是因为我们的坐标在数组中是紧凑的排列的，没有使用offset，最后的参数放的是存放顶点坐标的buffer
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mTriangleBuffer);

        // 画数组，第一个参数表示画三角形，第二个参数是first，第三个参数是count，表示在buffer里面的坐标的开始和个数
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
    }

    private void drawRect(GL10 gl){
        //画四边形
        // 利用数组模型来画模型，要加此句(原文中没有)
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // 三角形的颜色为红色，透明度为不透明
        gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f);

        // 坐标向右移1.5个单位
        gl.glTranslatef(0.0f, 0.0f, -2.0f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mQuadsBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4); // 画四边形
    }


    // 四边形的顶点数组
    private float[] mQuadsArray = {
            1f, 1f, 0f, // 右上
            -1f, 1f, 0f, // 左上
            -1f, -1f, 0f, // 左下
            1f, -1f, 0f // 右下
    }; // 从这里可以看出，我们按照逆时针的方向画图
    private FloatBuffer mQuadsBuffer;
}
