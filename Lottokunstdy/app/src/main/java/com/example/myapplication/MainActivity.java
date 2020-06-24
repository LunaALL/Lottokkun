package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView textView;
    boolean isRun = false;

    //스레드 내부에서 쓸 수 있는 핸들러를 전역으로 정의
    //ValueHandler handler = new ValueHandler();

    Handler handler2 = new Handler();

    //MyThread2에비해 더 간단해짐
    //실제로 가장 많이 쓰임
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);


        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //textView.setText("현재 값 : " + value);
                isRun=false;

            }
        });
    }

    public void thread (View view) {
        new Thread(new Runnable() {

            int value = 0;

            @Override
            public void run() {
                isRun = true;
                //1초마다 벨류값 1씩 증가시키는 스레드임
                while ((isRun)) {
                    value += 1;
                    //핸들러클래스로서 post로 던질수가있음.
                    //핸들러의 post 메소드를 호출하면 Runnable 객체를 전달할 수 있습니다.
                    //핸들러로 전달된 Runnable, 객체는 메인 스레드에서 실행될 수 있으며 따라서 UI를 접근하는 코드는 Runnable 객체 안에 넣어두면 됩니다.
                    //post 메소드 이외에도 지정된 시간에 실행하는 postAtTime 메소드와 지정된 시간만큼 딜레이된 시간후 실행되는 postDelayed 메소드가 있습니다.
                    handler2.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("현재값 : " + value);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            }
        }).start(); //start()붙이면 바로실행시킨다.
    }

    }




