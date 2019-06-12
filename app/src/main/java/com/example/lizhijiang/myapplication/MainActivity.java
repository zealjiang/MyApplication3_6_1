package com.example.lizhijiang.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout rlRootView;
    private EditText tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        Intent intent = new Intent(this, WordartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/

        Intent it = new Intent(this, TestActivity.class);
        startActivity(it);


        if(1 >0){

            finish();
            return;
        }

        rlRootView = findViewById(R.id.rlRootView);
        tv = findViewById(R.id.tv);


        tv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int height = tv.getHeight();

                System.out.print("heidht" + height);

                int lineCount = tv.getLineCount();
                int lineHeight = tv.getLineHeight();

                CharSequence str = tv.getText().toString();

                int num = 0;
                for (int i = 0; i < str.length(); i++) {
                    if(str.charAt(i) == '\n'){
                        num ++;
                    }
                }
                System.out.print("num" + num);
            }
        });


        RelativeLayout rl = new RelativeLayout(this);
        TextView tv2 = new TextView(this);
        rl.addView(tv2);
        ViewGroup.LayoutParams params = tv2.getLayoutParams();
        System.out.print("params" + params);

        rlRootView.addView(rl);

        System.out.print("params" + params);

    }

}
