package com.example.lizhijiang.myapplication.lottie;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.example.lizhijiang.myapplication.R;

public class LottieDemoActivity extends AppCompatActivity {

    private LottieAnimationView animationView;
    private Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie_demo2);

        animationView = findViewById(R.id.animation_view);
        buttonStart = findViewById(R.id.btnStart);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //animationView.setImageAssetsFolder("images");
                animationView.playAnimation();

                animationView.addAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        Log.d("mtest","onAnimationStart ");
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.d("mtest","onAnimationEnd ");
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        Log.d("mtest","onAnimationCancel ");
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        Log.d("mtest","onAnimationRepeat ");
                    }
                });
                animationView.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Object value = animation.getAnimatedValue();
                        Log.d("mtest","value: "+value);
                    }
                });
            }
        });



        //animationView.setProgress(0.5f);

        // Custom animation speed or duration.
/*        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animationView.setProgress(animation.getAnimatedValue());
            }
        });
        animator.start();*/
    }
}
