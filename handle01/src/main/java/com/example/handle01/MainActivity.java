package com.example.handle01;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    /**
     *  1 自动显示3个图片的切换  2 传送消息   3  移除handler对象   4 handler 消息的截取 callback
     */
    private ImageView imageView;
    private TextView textView;
    private Button button;
    private int images[]={R.drawable.img3,R.drawable.img1,R.drawable.img2};//t图片
    private  int index;// 索引  ，充当0 .1 .2
//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
////            textView.setText(""+msg.arg1);
//            textView.setText(""+msg.obj);
//        }
//    };
    private  Handler handler=new Handler(new Handler.Callback() {
    @Override
//    .....
    public boolean handleMessage(Message message) {
        return false;
    }
});
    private MyRunnable  myRunnable=new MyRunnable();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView= (ImageView) findViewById(R.id.imageView);
        textView= (TextView) findViewById(R.id.textView);
        button= (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        handler.postDelayed(myRunnable,1000);
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
//                    Message message=new Message();
//                    message.arg1=88;
                    Message message=handler.obtainMessage();//调用系统的message对象
                    Person person=new Person();
                    person.age=23;
                    person.name="cyh";
                    message.obj=person;
//                    发生的2种方法
//                    handler.sendMessage(message);
                    message.sendToTarget();//封装了sendMessage
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

//    移除handler对象
    public void onClick(View view) {
        handler.removeCallbacks(myRunnable);
    }

    /**
     * 集成Runnable 实现逻辑功能  图片自动切换
     */
    class MyRunnable implements  Runnable{
        @Override
        public void run() {
            index++;
            index=index%3;
            imageView.setImageResource(images[index]);
            handler.postDelayed(myRunnable,1000);
        }
    }
    class Person {
        public  int age;
        public  String name;
        public String toString(){
            return "name="+name+",age="+age;
        }
    }
}
