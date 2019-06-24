package com.example.lizhijiang.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lizhijiang.myapplication.kotlint.TestGrammar;
import com.example.lizhijiang.myapplication.view.BgDashlineTextView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Vector;

public class TestViewtreeObserverActivity extends AppCompatActivity {


    private LinearLayout llContainer;
    private Button btn;
    private Button btnClear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_viewtree_observer);

        llContainer = findViewById(R.id.llContainer);
        btn = findViewById(R.id.btn);
        btnClear = findViewById(R.id.btnClear);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BgDashlineTextView textView = new BgDashlineTextView(TestViewtreeObserverActivity.this);
                textView.setText("中新网6月20日电 教育部20日上午举行新闻发布会，解读《国务院办公厅关于新时代推进普" +
                        "通高中育人方式改革的指导意见》。教育部教材局对普通高中课程方案、课程标准及教材的修订情" +
                        "况进行介绍称，在课程标准修订情况方面，修订了语文等学科17个课程标准，新研制德语、法语" +
                        "和西班牙语3个课程标准，共计20个课程标准。教育部教材局指出，为落实中央关于立德树人要求，" +
                        "进一步深化基础教育课程改革，教育部组织专家对普通高中课程方案和课程标准进行修订，" +
                        "统编语文、历史、思想政治三科教材，修订其他非统编学科教材，情况如下。新华社“记者再走长征路”小" +
                        "分队在福建长汀的采访，第一站便是位于闽赣交界地区的四都镇楼子坝村姜畲坑。" +
                        "这是个山坳中的自然村落，只有七八户人家，依山而建的房屋零零散散地分布在溪水两岸。" +
                        "村中有四处与红军有关的建筑：医院旧址、兵工厂旧址、造币厂旧址和毛泽覃同志故居。其中，" +
                        "医院、兵工厂、造币厂是因中央红军长征后苏区大面积被敌人攻陷，从四都镇周边转移到这里的。");

                //textView.setText("队在福建长汀的采");
                textView.setTextColor(Color.parseColor("#ff0000"));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
                textView.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(17000,82);
                params.gravity = Gravity.CENTER;
                textView.setLayoutParams(params);

                textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        Log.d("mtest",textView.getWidth()+"");
                    }
                });

                ViewGroup.LayoutParams llParams = llContainer.getLayoutParams();
                llParams.width = 18000;
                llContainer.setLayoutParams(llParams);

                llContainer.addView(textView);


            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llContainer.removeAllViews();
            }
        });
    }


}
