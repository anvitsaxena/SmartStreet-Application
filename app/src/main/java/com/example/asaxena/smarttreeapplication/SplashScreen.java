package com.example.asaxena.smarttreeapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by asaxena on 9/19/2017.
 */

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //declare the thread timer for number of seconds splash needs to be visible.
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        //This method will be called because we do not want the user to see this when he clicks back button
        super.onPause();
        finish(); //finish will end this activity and will not open again within the application
    }

}
