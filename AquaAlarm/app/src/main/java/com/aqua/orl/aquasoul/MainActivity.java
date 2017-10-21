package com.aqua.orl.aquasoul;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = (VideoView)findViewById(R.id.VideoView);

        //video path
        String path = "android.resource://" + getPackageName() + "/" + R.raw.bubbl;
        videoView.setVideoURI(Uri.parse(path));
        videoView.start();

        new Timer().schedule(new TimerTask(){
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        startActivity(new Intent(MainActivity.this, AlarmActivity.class));
                    }
                });
            }
        }, 8000);

    }


}