package com.idiots.klip.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.idiots.klip.R;


public class Splash extends AppCompatActivity {

    private static  int SPLASH_TIME_OUT=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences =getSharedPreferences(MainActivity.PREFS_NAME,0);
                boolean hasloggedIn= sharedPreferences.getBoolean("hasloggedIN",false);
                if(hasloggedIn){
                    Intent intent = new Intent(Splash.this, com.idiots.klip.home.dashboard.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent =new Intent(Splash.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },SPLASH_TIME_OUT);

    }
}