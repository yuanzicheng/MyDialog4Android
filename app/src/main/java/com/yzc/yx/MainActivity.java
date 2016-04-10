package com.yzc.yx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yzc.mydialoglibrary.MyDialog;
import com.yzc.mydialoglibrary.MyDialogListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button)findViewById(R.id.button1);
        Button button2 = (Button)findViewById(R.id.button2);
        Button button3 = (Button)findViewById(R.id.button3);
        Button button4 = (Button)findViewById(R.id.button4);

        assert button1 != null;
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDialog(MainActivity.this).prompt(MyDialog.TYPE_ERROR,"测试一下test");
            }
        });
        assert button2 != null;
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDialog(MainActivity.this).waiting("加载中...",true);
            }
        });
        assert button3 != null;
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDialog(MainActivity.this).message(MyDialog.TYPE_OK,"此处显示提示内容文字内容不宜过长，否则显示成多行影响视觉效果.此处显示提示内容文字内容不宜过长，否则显示成多行影响视觉效果.此处显示提示内容文字内容不宜过长，否则显示成多行影响视觉效果","朕知道了");
            }
        });

        assert button4 != null;
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialogListener listener1 = new MyDialogListener() {
                    @Override
                    public void callback(String[] array) {

                    }
                };

                MyDialogListener listener2 = new MyDialogListener() {
                    @Override
                    public void callback(String[] array) {

                    }
                };

                new MyDialog(MainActivity.this).callback(MyDialog.TYPE_INFO,"确定XX吗？",listener1,"确定",listener2,"取消");
            }
        });
    }
}
