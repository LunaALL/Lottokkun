package com.lottotest.lottokun;

public class Lottovalue {
    int num1=0 ,num2=0 ,num3=0 , num4=0, value=0;

    public Lottovalue(int num1, int num2,int num3,int num4,int value){
        this.num1=num1;
        this.num2=num2;
        this.num3=num3;
        this.num4=num4;
        this.value=value;
    }

    public Lottovalue getLottovalue(){
        return new Lottovalue(num1,num2,num3,num4,value);
    }
}
