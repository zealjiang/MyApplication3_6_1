package com.example.lizhijiang.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.lizhijiang.myapplication.AppApplication;
import com.example.lizhijiang.myapplication.util.ScreenUtils;

/**
 * Region.Op.DIFFERENCE: clip之外的部分 设置有效
 * Region.Op.REPLACE: clip的部分设置有效
 * Region.Op.INTERSECT: clip的部分设置有效
 * Region.Op.XOR: clip之外的部分 设置有效
 * Region.Op.UNION: 所有区域都有效 相当于没设置 两个clip区域都有效
 * Region.Op.REVERSE_DIFFERENCE: clip去除相交的部分设置有效
 * Region.Op.XOR: 两个clip相交之外的部分有效   当A、B两个不同时结果为1，否则为0
 *
 * clipxx方法只对设置以后的drawxx起作用
 */
public class ClipRectView extends View {

    private Paint paint;

    public ClipRectView(Context context) {
        super(context);
        init();
    }

    public ClipRectView(Context context, @Nullable  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClipRectView(Context context, @Nullable  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint= new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

/*        test2(canvas);
        test3(canvas);
        if(1>0)return;*/

        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ScreenUtils.dipConvertPx(AppApplication.getContext(),3));


        canvas.save();
        //画框
        canvas.drawRect(0, 0, 200,200, paint);
        canvas.drawRect(100, 100, 200,200, paint);
        canvas.drawRect(100, 100, 300,300, paint);
        paint.setStyle(Paint.Style.FILL);
        //A
        canvas.drawRect(0, 0, 200,200, paint);//rect之外的部分

        //相交
        //canvas.clipRect(100, 100, 200,200, Region.Op.DIFFERENCE);//rect的部分显示红色 B的其余显示蓝色 A全显示红色
        //canvas.clipRect(100, 100, 200,200, Region.Op.INTERSECT);//rect的部分显示蓝色 B的其余部分不显示 A的其余部分显示红色
        //canvas.clipRect(100, 100, 200,200, Region.Op.REVERSE_DIFFERENCE);//A全显示 B不显示  rect的部分显示红色
        //canvas.clipRect(100, 100, 200,200, Region.Op.REPLACE);//rect的部分显示蓝色 B的其余部分不显示 A的其余部分显示红色
        canvas.clipRect(100, 100, 200,200, Region.Op.UNION);//rect的部分显示蓝色 B的全显示蓝色 A的其余部分显示红色
        //canvas.clipRect(100, 100, 200,200, Region.Op.XOR);//rect的部分显示红色 B的其余显示蓝色 A全显示红色

        //B
        paint.setColor(Color.BLUE);
        canvas.drawRect(100, 100, 300,300, paint);
        canvas.restore();



        canvas.save();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        //画框
        canvas.drawRect(100, 400, 400,700, paint);
        canvas.drawRect(200, 500, 300,600, paint);
        paint.setStyle(Paint.Style.FILL);

        //A
        canvas.drawRect(100, 400, 400,700, paint);

        //相交
        //canvas.clipRect(200, 500, 300,600, Region.Op.DIFFERENCE);//rect的部分显示红色 B的其余显示蓝色 A全显示红色
        //canvas.clipRect(200, 500, 300,600, Region.Op.INTERSECT);//rect的部分显示蓝色 B的其余部分不显示 A的其余部分显示红色
        //canvas.clipRect(200, 500, 300,600, Region.Op.REVERSE_DIFFERENCE);//A全显示 B不显示  rect的部分显示红色
        //canvas.clipRect(200, 500, 300,600, Region.Op.REPLACE);//rect的部分显示蓝色 B的其余部分不显示 A的其余部分显示红色
        //canvas.clipRect(200, 500, 300,600, Region.Op.UNION);//rect的部分显示蓝色 B的全显示蓝色 A的其余部分显示红色
        //canvas.clipRect(200, 500, 300,600, Region.Op.XOR);//rect的部分显示红色 B的其余显示蓝色 A全显示红色

        //B
        paint.setColor(Color.BLUE);
        canvas.drawRect(200, 500, 300,600, paint);
        canvas.restore();


        canvas.save();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(ScreenUtils.dipConvertPx(AppApplication.getContext(),30));

        //A
        canvas.drawText("庆祝建国70周年", 100, 800, paint);

        //相交
        canvas.clipRect(200, 710, 400,900, Region.Op.INTERSECT);//rect的部分显示蓝色 B的其余部分不显示 A的其余部分显示红色

        //B
        paint.setColor(Color.BLUE);
        canvas.drawText("庆祝建国70周年", 100, 800, paint);
        canvas.restore();

        //start---

        paint.setColor(Color.RED);
        canvas.save();
        canvas.clipRect(100, 1000, 300, 1100);
        canvas.drawText("还有关于绘制文字这一点", 100, 1090, paint);
        canvas.restore();

        paint.setColor(Color.BLUE);
        canvas.save();
        canvas.clipRect(300, 1000, 1200, 1100);
        canvas.drawText("还有关于绘制文字这一点", 100, 1090, paint);
        canvas.restore();
        //end----
    }

    private void test2(Canvas canvas){
        paint.setAntiAlias(true);                           //设置画笔为无锯齿
        paint.setColor(Color.BLACK);                        //设置画笔颜色

        canvas.clipRect(100, 100, 350, 600, Region.Op.UNION);//设置显示范围
        canvas.drawColor(Color.RED);
        canvas.drawCircle(100,100,100,paint);

    }

    private void test3(Canvas canvas){

        canvas.drawColor(Color.GRAY);

        canvas.save();
        drawScene(canvas);
        canvas.restore();

/*        //DIFFERENCE,以第一次剪裁的为基础，在此基础上擦除第二次剪裁的部分(裁出第一次操作，并擦除掉第二次操作)
        canvas.save();
        canvas.translate(560, 0);
        canvas.clipRect(0, 0, 300, 300);
        canvas.clipRect(200, 200, 500, 500, Region.Op.DIFFERENCE);
        drawScene(canvas);
        canvas.restore();*/


/*        //INTERSECT,交叉,保留第一次剪裁和第二次剪裁重叠的部分
        canvas.save();
        canvas.translate(560, 0);
        canvas.clipRect(0, 0, 300, 300);
        canvas.clipRect(200, 200, 500, 500, Region.Op.INTERSECT);
        drawScene(canvas);
        canvas.restore();*/

/*        //REPLACE,替换,用第二次剪裁替换第一次剪裁(相当于只做了第二次剪裁,丢弃第一次剪裁)
        //分别放开下面三种操作有助于理解
        canvas.save();
        canvas.translate(560, 0);
        //1.第一种操作
        //canvas.clipRect(0, 0, 300, 300);
        //canvas.clipRect(200, 200, 500, 500, Region.Op.REPLACE);
        //2.第二次操作
        //canvas.clipRect(200, 200, 500, 500);
        //canvas.clipRect(0, 0, 300, 300, Region.Op.REPLACE);
        //3.第三种操作
        //canvas.clipRect(0, 0, 300, 300);
        drawScene(canvas);
        canvas.restore();*/

        //REVERSE_DIFFERENCE,以第二次剪裁的为基础，在此基础上擦除第一次剪裁的部分(裁出第二次操作，并擦除掉第一次操作)
        canvas.save();
        canvas.translate(560, 0);
        canvas.clipRect(0, 0, 300, 300);
        canvas.clipRect(200, 200, 500, 500, Region.Op.REVERSE_DIFFERENCE);
        drawScene(canvas);
        canvas.restore();

/*        //UNION,合并,即保留第一次剪裁和第二次剪裁的并集
        canvas.save();
        canvas.translate(560, 0);
        canvas.clipRect(0, 0, 300, 300);
        canvas.clipRect(200, 200, 500, 500, Region.Op.UNION);
        drawScene(canvas);
        canvas.restore();*/

/*        //XOR,异或操作，做出第一次剪裁，并且做出第二次剪裁，在此基础上擦除掉重叠的部分(留下两次操作不重叠的部分)
        canvas.save();
        canvas.translate(560, 0);
        canvas.clipRect(0, 0, 300, 300);
        canvas.clipRect(200, 200, 500, 500, Region.Op.XOR);
        drawScene(canvas);
        canvas.restore();*/

    }

    private void drawScene(Canvas canvas) {
        canvas.clipRect(0, 0, 500, 500);

        canvas.drawColor(Color.WHITE);

        paint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, 300, 300, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        canvas.drawText("A", 140, 140, paint);

        paint.setColor(Color.GREEN);
        canvas.drawRect(200, 200, 500, 500, paint);

        paint.setColor(Color.WHITE);
        canvas.drawText("B", 350, 350, paint);

        paint.setColor(Color.RED);
        canvas.drawRect(200, 200, 300, 300, paint);

    }
}
