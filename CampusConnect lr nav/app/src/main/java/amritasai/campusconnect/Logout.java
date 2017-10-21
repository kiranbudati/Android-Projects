package amritasai.campusconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Logout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        Intent intent = getIntent();
        String hallticketnum = intent.getStringExtra(MainActivity.hallTicket);
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.hallTicket, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();

        Intent i = new Intent(Logout.this,MainActivity.class);
        startActivity(i);
    }
}
