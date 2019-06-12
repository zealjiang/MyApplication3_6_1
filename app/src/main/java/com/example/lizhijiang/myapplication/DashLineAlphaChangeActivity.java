package com.example.lizhijiang.myapplication;

import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DashLineAlphaChangeActivity extends AppCompatActivity {

    private RelativeLayout rlNew;
    private TextView tv1;
    private TextView tv2;
    private Button btn;
    private View view;
    private ValueAnimator animator;//背景虚线透明度变换动画

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_line_alpha_change);

        rlNew = findViewById(R.id.rlNew);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        btn = findViewById(R.id.btn);
        view = findViewById(R.id.view);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //setBgShowAnimation(rlNew,true);
        setViewAlphaAnimation(tv2,true);

    }

    /**
     * 设置背景虚线动画
     */
    private void setBgShowAnimation(View view,boolean isShow){
        if(view == null){
            return;
        }
        if(isShow) {
            //用一闪一闪的虚线框
            view.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.shape_dash_line_subtitle_editing));
            //view.setBackgroundColor(Color.parseColor("#ff0000"));
            final Drawable drawable = view.getBackground();


            ValueAnimator animator = ValueAnimator.ofInt(0, 255);
            animator.setDuration(1500);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.setInterpolator(new LinearInterpolator()); //均匀线性变化
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    drawable.setAlpha((int) animation.getAnimatedValue());
                }
            });
            animator.start();
        }else{
            view.setBackground(null);
        }
    }

    private void setViewAlphaAnimation(final View view,boolean isShow){
        if(view == null){
            return;
        }
        if(isShow) {
            //用一闪一闪的虚线框
            view.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.shape_dash_line_subtitle_editing));
            view.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
            final Drawable drawable = view.getBackground();

            ValueAnimator animator = ValueAnimator.ofInt(0, 255);
            animator.setDuration(1500);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.setInterpolator(new LinearInterpolator()); //均匀线性变化
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int)animation.getAnimatedValue();
                    drawable.setAlpha(value);
                }
            });
            animator.start();
        }else{
            view.setBackground(null);
        }
    }
}
