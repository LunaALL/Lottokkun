package com.example.lottokun;

import android.os.AsyncTask;
import android.widget.TextView;



import java.util.Random;

class CalGamethread extends AsyncTask<Integer ,Integer,Integer> {


    int[] arr;
    int[] arr2={0,0,0,0,0,0};
    int value=0;
    TextView ValueTV, T1,T2,T3,T4;
    int num=0;
    static Random rnd =new Random();
    int num1=0,num2=0,num3=0,num4=0;
    static Lottovalue saveg;


    CalGamethread(TextView valueTV){
        this.ValueTV=valueTV;
        ran();
    }

    void setTextview(TextView t1 , TextView t2, TextView t3, TextView t4){
        this.T1=t1;
        this.T2=t2;
        this.T3=t3;
        this.T4=t4;
    }
     void setArr(int[] arr){
        this.arr=arr;
    }

    protected void onPreExecute() {
        ValueTV.setText("연산중입니다.");
        if(saveg!=null){
            num1=saveg.num1;
            num2=saveg.num2;
            num3=saveg.num3;
            num4=saveg.num4;
            value=saveg.value;

        }
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
                publishProgress(0,1);
            }else if (num == 2) {
                publishProgress(0,2);
            }else if(num==3){
                publishProgress(0,3);
            }else if(num==4){
                publishProgress(0,4);
            }
            value++;

            if (value % 1000 == 0) {
                publishProgress(1,value);
            }

            //위에 onCreate()에서 호출한 excute(100)의 100을 사용할려면 이런식으로 해줘도 같은 결과가 나온다.
            //밑 대신 이렇게해도됨 if (value >= values[0].intValue())

        }

        if(isCancelled()){
            return 0;
        }
        return value;
    }

    //UI작업 관련 작업 (백그라운드 실행중 이 메소드를 통해 UI작업을 할 수 있다)
    //publishProgress(value)의 value를 값으로 받는다.values는 배열이라 여러개 받기가능
    protected void onProgressUpdate(Integer ... values) {
        if(values[0]==1){
            ValueTV.setText("회차 " + values[1].toString()+ "회");
        }


        if(values[0]==0 && values[1]==1){
            num1++;
            T1.setText(" 1등 "+num1 +" 번 당첨 ");
        }
        if(values[0]==0 && values[1]==2){
            num2++;
            T2.setText("2등 "+num2 +" 번 당첨" );
        }

        if(values[0]==0 && values[1]==3){
            num3++;
            T3.setText("3등 "+num3 +" 번 당첨" );
        }

        if(values[0]==0 && values[1]==4){
            num4++;
            T4.setText("4등 "+num4 +" 번 당첨" );
        }


    }


    //이 Task에서(즉 이 스레드에서) 수행되던 작업이 종료되었을 때 호출됨
    protected void onPostExecute(Integer result) {

    }

    //Task가 취소되었을때 호출
    protected void onCancelled() {
        ValueTV.setText(" " + value +" 회 로또 시뮬레이션 마침.");
        Lottovalue save=new Lottovalue(num1,num2,num3,num4,value);
        MainActivity.savegame(save);
    }

    static int lottocheck(int[] arr, int[] arr2) {
        Random rnd =new Random();
        int numtemp = 0;
        int value=0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (arr[i] == arr2[j]) {
                    numtemp++;
                    break;
                }
            }
        }

        if(numtemp==6){
            return 1;
        }else if(numtemp==5){
            value=rnd.nextInt(45)+1;
            for(int i=0; i<6; i++){
                if(arr[i]==value){
                    return 2;
                }else{
                    return 3;
                }
            }
        }else if(numtemp==4){
            return 4;
        }

        return 0;
    }

    public void savegame1(Lottovalue val){
        saveg=val;
    }



}
