package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    TextView st1;

    //백그라운드Task
    BackgroundTask task;
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        final int[] arr= {5,20,33,13,14,11};
        st1= (TextView)findViewById(R.id.game1);


        // 실행 버튼 이벤트
        Button executeButton = (Button) findViewById(R.id.executeButton);
        executeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 백그라운드 task 생성
                task = new BackgroundTask(arr,textView,st1);
                //excute를 통해 백그라운드 task를 실행시킨다
                //여기선 100을 매개변수로 보내는데 여기 예제에서는 이 매개변수를 doInBackGround에서 사용을 안했다.
                task.execute();
            }
        });

        // 취소 버튼 이벤트
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //task종료한다. BackgrounTask의 onCancled()호출될것이다.
                //이미 스레드가 종료된 뒤에 cancel하면 아무일도 안일어난다. 이미 종료되었기 때문에
                task.cancel(true);
            }
        });
    }

    //새로운 TASK정의 (AsyncTask)
    // < >안에 들은 자료형은 순서대로 doInBackground, onProgressUpdate, onPostExecute의 매개변수 자료형을 뜻한다.(내가 사용할 매개변수타입을 설정하면된다)

}

class BackgroundTask extends AsyncTask<Integer , Integer , Integer> {
    //초기화 단계에서 사용한다. 초기화관련 코드를 작성했다.
    int[] arr;
    int[] arr2={0,0,0,0,0,0};
    int value;
    TextView textView;
    TextView textView2;
    int num=0;
    static Random rnd =new Random();
    int num1=0,num2=0,num3=0,num4=0;


    BackgroundTask (int[] arr,TextView textView,TextView st1){
        this.arr=arr;
        this.textView=textView;
        this.textView2=st1;
        ran();
    }

    protected void onPreExecute() {
        value = 0;
        textView.setText("연산중입니다.");
    }

    void ran() {
        for(int i=0;i<6;i++){
            arr2[i]=rnd.nextInt(45)+1;
        }
    }

    //스레드의 백그라운드 작업 구현
    //여기서 매개변수 Intger ... values란 values란 이름의 Integer배열이라 생각하면된다.
    //배열이라 여러개를 받을 수 도 있다. ex) excute(100, 10, 20, 30); 이런식으로 전달 받으면 된다.
    protected Integer doInBackground(Integer ... values) {
        //isCancelled()=> Task가 취소되었을때 즉 cancel당할때까지 반복
        while (isCancelled() == false) {
            ran();
            num = lottocheck(arr, arr2);
            if(num == 1){
                publishProgress(0,num);
            }else if (num == 2) {
                publishProgress(0,num);
            }else if(num==3){
                publishProgress(0,num);
            }else if(num==4){
                publishProgress(0,num);
            }
            value++;

            if (value % 1000 == 0) {
                publishProgress(1,value);
            }

            //위에 onCreate()에서 호출한 excute(100)의 100을 사용할려면 이런식으로 해줘도 같은 결과가 나온다.
            //밑 대신 이렇게해도됨 if (value >= values[0].intValue())

        }
        return value;
    }

    //UI작업 관련 작업 (백그라운드 실행중 이 메소드를 통해 UI작업을 할 수 있다)
    //publishProgress(value)의 value를 값으로 받는다.values는 배열이라 여러개 받기가능
    protected void onProgressUpdate(Integer ... values) {
        if(values[0]==1){
            textView.setText("횟수" + values[1].toString());
        }


        if(values[0]==0 && values[1]==1){
            num1++;
            textView2.setText("1등 "+num1 +"번");

        }
        if(values[0]==0 && values[1]==2){
            num2++;
            textView2.setText("2등 "+num2 +"번");
        }

        if(values[0]==0 && values[1]==3){
            num3++;
            textView2.setText("3등 "+num3 +"번");
        }


    }


    //이 Task에서(즉 이 스레드에서) 수행되던 작업이 종료되었을 때 호출됨
    protected void onPostExecute(Integer result) {

    }

    //Task가 취소되었을때 호출
    protected void onCancelled() {
        textView.setText("취소되었습니다");
    }

    public static int lottocheck(int[] arr, int[] arr2) {
        Random rnd =new Random();
        int num = 0;
        int value=0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (arr[i] == arr2[j]) {
                    num++;
                    break;
                }
            }
        }

        if(num==6){
            return 1;
        }else if(num==5){
            value=rnd.nextInt(45)+1;
            for(int i=0; i<6; i++){
                if(arr[i]==value){
                    return 2;
                }else{
                    return 3;
                }
            }
        }else if(num==4){
            return 4;
        }

        return 0;
    }
}




