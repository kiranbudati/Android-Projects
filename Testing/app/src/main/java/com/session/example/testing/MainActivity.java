package com.session.example.testing;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteException;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.net.Uri;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.ButtonBarLayout;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.RelativeLayout;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;


        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Spinner s1, s2;
    ArrayAdapter<CharSequence> a1,a2;
    public String f1, f2,f3,f4;
    public int i1,i2;
    Button b1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DataBaseHelper myDbHelper;
        myDbHelper = new DataBaseHelper(this);

        s1 = (Spinner) findViewById(R.id.spinner1);
        s2 = (Spinner) findViewById(R.id.spinner2);
        b1 = (Button)findViewById(R.id.button);
        a1 = ArrayAdapter.createFromResource(this, R.array.nationality, android.R.layout.simple_spinner_item);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        a2 = ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_item);
        a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(a1);
        s2.setAdapter(a2);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " selected ", Toast.LENGTH_LONG).show();
                }
                f1 = (String) parent.getItemAtPosition(position);

                i1 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " selected", Toast.LENGTH_LONG).show();
                }
                f2 = (String) parent.getItemAtPosition(position);

                i2 = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

            b1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if(i1!=0 && i2!=0) {
                    Intent myIntent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(myIntent);
                        try {

                            myDbHelper.openDataBase(f1, f2);

                        } catch (SQLException sqle) {

                            throw sqle;
                        }
                    }
                    else
                    {
                        Toast.makeText(getBaseContext(),"please select country",Toast.LENGTH_LONG).show();;
                    }

                }
            });


        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }





    }
}

