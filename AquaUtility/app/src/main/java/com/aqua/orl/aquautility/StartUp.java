package com.aqua.orl.aquautility;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class StartUp extends Activity {
    WifiApManager wifiApManager;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_up);
        videoView = (VideoView) findViewById(R.id.VideoView);
         wifiApManager = new WifiApManager(this);
        if (Build.VERSION.SDK_INT > 22)
        {
            if (wifiApManager.getWifiApState() == WIFI_AP_STATE.WIFI_AP_STATE_ENABLED) {
                String path = "android.resource://" + getPackageName() + "/" + R.raw.simple;
                videoView.setVideoURI(Uri.parse(path));
                videoView.start();
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        StartUp.this.runOnUiThread(new Runnable() {
                            public void run() {
                                startActivity(new Intent(StartUp.this, MainActivity.class));
                            }
                        });
                    }
                }, 4000);
            }
            else
            {
                startActivity(new Intent(StartUp.this, Version.class));
            }
        }

        else {
                String path = "android.resource://" + getPackageName() + "/" + R.raw.simple;
                videoView.setVideoURI(Uri.parse(path));
                videoView.start();
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        StartUp.this.runOnUiThread(new Runnable() {
                            public void run() {
                                startActivity(new Intent(StartUp.this, MainActivity.class));
                            }
                        });
                    }
                }, 4000);
            }

        }

    }




