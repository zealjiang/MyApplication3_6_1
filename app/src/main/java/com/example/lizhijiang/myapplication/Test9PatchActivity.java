package com.example.lizhijiang.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.TextView;


public class Test9PatchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_9_patch_test);

        Log.d("mtest", "onCreate");

        TextView tv = findViewById(R.id.tv);
        SpannableString span3 = new SpannableString("点击添加转场动画");
        //ImageSpan image = new ImageSpan(this,R.mipmap.ba, DynamicDrawableSpan.ALIGN_BOTTOM);
        ImageSpan image = new ImageSpan(getDrawable(), DynamicDrawableSpan.ALIGN_BASELINE);

        span3.setSpan(image,2,3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(span3);
    }

    private Drawable getDrawable(){
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.mipmap.ba);
            BitmapDrawable drawable = new BitmapDrawable(this.getResources(),bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2,
                    drawable.getIntrinsicHeight()/2);

            return drawable;
        } catch (Exception e) {
            //Log.e("ImageSpan", "Unable to find resource: " + mResourceId);
        }

        return null;
    }
}
