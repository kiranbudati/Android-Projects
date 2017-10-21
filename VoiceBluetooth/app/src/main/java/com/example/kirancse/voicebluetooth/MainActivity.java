package com.example.kirancse.voicebluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button turnON = (Button) findViewById(R.id.turnON);
        final Button scan = (Button) findViewById(R.id.scan);
        final Button turnOFF = (Button) findViewById(R.id.turnOFF);
        final BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();

        turnON.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!bluetooth.isEnabled()) {
                    Toast.makeText(getApplicationContext(),
                            "Turning ON Bluetooth", Toast.LENGTH_LONG);
                    // Intent enableBtIntent = new
                    // Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(new Intent(
                            BluetoothAdapter.ACTION_REQUEST_ENABLE), 0);
                }
            }
        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (!bluetooth.isDiscovering()) {
                    Toast.makeText(getApplicationContext(),
                            "MAKING YOUR DEVICE DISCOVERABLE",
                            Toast.LENGTH_LONG);
                    // Intent enableBtIntent = new
                    // Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(new Intent(
                            BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE), 0);

                }
            }
        });
        turnOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                bluetooth.disable();
                Toast.makeText(getApplicationContext(),
                        "TURNING OFF BLUETOOTH", Toast.LENGTH_LONG);
            }
        });
    }

}