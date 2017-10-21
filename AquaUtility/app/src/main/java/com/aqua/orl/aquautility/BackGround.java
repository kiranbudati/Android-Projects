package com.aqua.orl.aquautility;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class BackGround extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent i = new Intent(this, MainActivity.class);
        startService(i);

        return super.onStartCommand(intent, flags, startId);
    }
}