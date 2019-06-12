package com.example.lizhijiang.myapplication;

import android.animation.ObjectAnimator;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.lizhijiang.myapplication.kotlint.TestGrammar;

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
}
