package com.example.lizhijiang.myapplication.event;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.lizhijiang.myapplication.R;

public class EventActivityButtonTextView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_btn_textview);


        View llContainer = findViewById(R.id.vCover);
        llContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("mtest","vCover clicked!!!");
            }
        });

        findViewById(R.id.btnComment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("mtest","btnComment clicked!!!");
            }
        });
    }

}
