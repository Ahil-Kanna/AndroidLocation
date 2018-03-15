package com.example.admin.androidlocation;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Admin on 08/11/2017.
 */

public class haha extends AppCompatActivity{
    protected  void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.haha);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        new CountDownTimer(2000,1000){
            @Override
            public void onTick(long millisUntilFinished){}
            @Override
            public void onFinish(){
                Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(intent);
            }
        }.start();
    }
    }


