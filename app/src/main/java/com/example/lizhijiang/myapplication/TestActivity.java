package com.example.lizhijiang.myapplication;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.lizhijiang.myapplication.kotlint.TestGrammar;
import com.example.lizhijiang.myapplication.util.FontUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class TestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Log.d("mtest","onCreate");


/*        findViewById(R.id.btnFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TestActivity.this.finish();
                Log.d("","");
            }
        });*/



        findViewById(R.id.btnFinish).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("","");
                testHashSet();

                ArrayList<Person> list = new ArrayList();
                Person p = new Person("唐僧","pwd1",25);
                list.add(p);
                Person person = list.get(0);
                list.clear();
                if(person != null){//不为空
                   String name = person.getName();
                }

                TestGrammar tg = new TestGrammar();
                tg.main();
                return false;
            }
        });

        findViewById(R.id.btnFinish2).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("","");
                return false;
            }
        });

        findViewById(R.id.ivBg).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String str = "中国ab@";
                String str2 = "中国123;*";
                int length = str.length();
                int length2 = str2.length();
                int utf8Length = getCharacterLength(str);
                int utf8Length2 = getCharacterLength(str2);
                int a;
                return false;
            }
        });

        //测试换行
        String content = "中国空军专家\n傅前哨\n11月10日接受《环球时报》记者采访时说，双11不仅是购物节，更是人民空军的生日。对于要如何建设一支世界一流空军，傅前哨表示，中国官方之前曾提出要建设一流军队，这是面向全军讲的，从空军军种角度讲，建设世界一流空军也是顺理成章的。“空天一体，攻防兼备”是人民空军的发展战略，最终要达到的目标就是建设世界一流空军。";
        TextView tvLinefeed = findViewById(R.id.tvLinefeed);
        content = FontUtil.autoInsertLinefeed(tvLinefeed,240,4,true,false,content);
/*        String[] contentAndline = FontUtil.handleTextWidthLines(tvLinefeed,content,240,3);
        Log.d("mtest","lineNum :"+contentAndline[1]);
        Log.d("mtest","content :"+contentAndline[0]);*/
        tvLinefeed.setText(content);
        //tvLinefeed.setMaxLines(2);
        tvLinefeed.setMovementMethod(ScrollingMovementMethod.getInstance());
        String getText = tvLinefeed.getText().toString();
        //Log.d("mtest","getText :"+getText);
    }

    public static String toUtf8(String str) {
        String result = null;
        try {
            result = new String(str.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取字符长度  一个汉字占两个字符，一个英文和一个数字占一个字符
     * @param str
     * @return
     */
    public static int getCharacterLength(String str){
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char item = str.charAt(i);
            if (item < 128) {
                count = count + 1;
            } else {
                count = count + 2;
            }
        }
        return count;
    }

    private void testHashSet(){
        Vector v = new Vector(10);
        for (int i = 1; i<3; i++)
        {
            Person p = new Person("唐僧","pwd1",25);
            v.add(p);
            p = null;
            Object object = v.get(0);
            if(object != null){//发现object 不为null
                String name = ((Person)object).getName();
                Log.d("name",name);//竟然有值
            }

        }
    }

    class Person{
        private String name;
        private String pwd;
        private int age;

        public Person(String name, String pwd, int age) {
            this.name = name;
            this.pwd = pwd;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Log.d("mtest","onNewIntent");
    }
}
