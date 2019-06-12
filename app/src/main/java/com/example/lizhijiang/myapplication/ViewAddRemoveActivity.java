package com.example.lizhijiang.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewAddRemoveActivity extends AppCompatActivity {

    private RelativeLayout rlOld;
    private RelativeLayout rlNew;
    private TextView tv1;
    private TextView tv2;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_add_remove);

        rlOld = findViewById(R.id.rlOld);
        rlNew = findViewById(R.id.rlNew);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RelativeLayout)rlOld.getParent()).removeView(rlOld);
                rlNew.addView(rlOld);
            }
        });

        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) tv1.getLayoutParams();
        params1.width = 200;
        params1.height = 50;
        tv1.setLayoutParams(params1);

        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) tv2.getLayoutParams();
        params2.width = 350;
        params2.height = 50;
        params2.topMargin = 60;
        tv2.setLayoutParams(params2);

    }
}
