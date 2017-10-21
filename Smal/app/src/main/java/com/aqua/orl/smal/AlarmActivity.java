package com.aqua.orl.smal;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import static android.R.id.message;

public class AlarmActivity extends AppCompatActivity {

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private static AlarmActivity inst;
    private TextView alarmTextView;

    public static AlarmActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmTextView = (TextView) findViewById(R.id.alarmText);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker2);

        //set time
        String smsBody=getIntent().getExtras().getString("smsBody");
        Toast.makeText(getApplicationContext(),smsBody,Toast.LENGTH_LONG).show();

        String msg = smsBody.toString();
        String[] time = msg.split(":");
        int Hours = Integer.parseInt(time[0]);
        int Minutes = Integer.parseInt(time[1]);

        alarmTimePicker.setCurrentHour(Hours);
        alarmTimePicker.setCurrentMinute(Minutes);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
        calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());

        Intent myIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, 0);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        //Toast
        //Toast.makeText(getApplicationContext(),getIntent().getExtras().getString("smsBody"), Toast.LENGTH_LONG).show();

    }
    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }
}
