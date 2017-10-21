package com.aqua.orl.aquasoul;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        new Timer().schedule(new TimerTask(){
            public void run() {
                SecondActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        startActivity(new Intent(SecondActivity.this, ThirdActivity.class));
                    }
                });
            }
        }, 3000);
    }
}
