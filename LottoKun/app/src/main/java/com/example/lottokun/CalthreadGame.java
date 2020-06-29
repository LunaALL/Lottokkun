package com.example.lottokun;

import java.util.Random;
import java.util.logging.Handler;

public class CalthreadGame extends Thread {

    static Random rnd= new Random();
    Handler mhandler;
    int[] temp;
    boolean stop_flag=true;

    public CalthreadGame(int[] arr){
        temp=arr;
    }
    @Override
    public void run() {

        while(stop_flag){




        }


    }



    public void stopTh(boolean stop_flag){
        this.stop_flag=stop_flag;
    }

    public void setHandler(Handler mhandler){
        this.mhandler=mhandler;
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



    }
}

  new Thread(new Runnable() {
@Override
public void run() {
        Lotto L1 =new Lotto();
        Lotto L2 =new Lotto();
        L1.setLotto(arr2);
        gamerun =true;
        int num=0;

        while(gamerun){
        L1.setLotto();
        num=lottocheck(L1,L2);
        switch (num){
        case 1:
        one++;
        break;
        case 2:
        two++;
        break;
        case 3:
        three++;
        break;
        case 4:
        four++;
        break;
        }

        handler2.post(new Runnable() {
@Override
public void run() {
        tv.setText("1등 "+one+"회 2등 "+two+"회 3등 "+three+"회 4등 "+four+"회");
        //뷰
        }
        });
        }
        }
        });