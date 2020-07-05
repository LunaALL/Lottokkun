package com.example.lottokun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        try{
            Thread.sleep(2000);
            Intent intent = new Intent(Main2Activity.this,MainActivity.class);
            startActivity(intent);

        }catch (Exception e){

        }



    }
}
