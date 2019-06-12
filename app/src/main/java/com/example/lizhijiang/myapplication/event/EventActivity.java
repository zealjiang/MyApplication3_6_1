package com.example.lizhijiang.myapplication.event;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.lizhijiang.myapplication.R;

public class EventActivity extends AppCompatActivity {

    private RelativelayoutA relativelayoutA;
    private RelativelayoutB relativelayoutB;
    private MyView myView1;
    private MyView myView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        relativelayoutA = findViewById(R.id.RelativeLayoutA);
        relativelayoutB = findViewById(R.id.RelativeLayoutB);
        myView1 = findViewById(R.id.myView1);
        myView2 = findViewById(R.id.myView2);


    }
}
