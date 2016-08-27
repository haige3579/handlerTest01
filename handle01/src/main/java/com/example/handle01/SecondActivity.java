package com.example.handle01;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    /**
     * 区别UI线程和子线程。。。。
     * @param savedInstanceState
     */
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            System.out.println("UI----->"+Thread.currentThread());
        }
    };
    class MyThead extends  Thread{
        public Handler handler;

        @Override
        public void run() {
            Looper.prepare();
            handler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    System.out.println("currentThread:"+Thread.currentThread());
                }
            };
            Looper.loop();
        }
    }
    private MyThead thead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_second);
        TextView textView=new TextView(this);
        textView.setText("hello handler");
//        指定视图  。。。。
//        sb
        setContentView(textView);
        thead=new MyThead();
        thead.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thead.handler.sendEmptyMessage(1);
        handler.sendEmptyMessage(1);
    }
}
