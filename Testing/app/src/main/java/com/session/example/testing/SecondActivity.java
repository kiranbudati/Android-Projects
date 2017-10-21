package com.session.example.testing;

import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by ahamad shaik on 02-02-2017.
 */

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        DataBaseHelper dbhelper = new DataBaseHelper(this);
        String s3 =dbhelper.printRecord();
        TextView t3 = (TextView)findViewById(R.id.textView3);
        t3.setText(s3);
    }
}
