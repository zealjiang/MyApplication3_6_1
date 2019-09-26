package com.example.lizhijiang.myapplication.opengl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.lizhijiang.myapplication.R;

public class Lesson extends AppCompatActivity {


    private OpenGLView mOpenGLView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mOpenGLView = new OpenGLView(this);

        setContentView(mOpenGLView);
    }
}
