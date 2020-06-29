package com.example.lottokun;

import android.os.Handler;
import android.os.Message;

import java.util.Random;


public class CalthreadGame extends Thread {

    static Random rnd= new Random();
    Handler mhandler;
    int[] arr;
    int[] temp= {0,0,0,0,0,0};
    boolean stop_flag=true;
    int num;

    public CalthreadGame(Handler mhandler){
        this.mhandler=mhandler;
    }

    public void setArr(int[]arr){
        this.arr=arr;
    }
    @Override
    public void run() {

        while(stop_flag){
            temp=ran();
            num=lottocheck(arr,temp);

            switch(num){
                case 1:
                    Message msg= Message.obtain(mhandler,1);
                    mhandler.sendMessage(msg);
                    break;
                case 2:
                    mhandler.sendEmptyMessage(2);
                    break;
                case 3:
                    mhandler.sendEmptyMessage(3);
                    break;
                case 4:
                    mhandler.sendEmptyMessage(4);
                    break;
            }

        }


    }



    public void stopTh(boolean stop_flag){
        this.stop_flag=stop_flag;
    }


    public static int lottocheck(int []arr, int []arr2) {

        int num = 0;
        int value=0;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (arr[i] == arr2[j]) {
                    num++;
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

    public static int[] ran() {
        int[] temp={0,0,0,0,0,0};
        int i=0;
       while(i<temp.length){
           temp[i]=rnd.nextInt(45)+1;
       }

       return temp;

    }
}

