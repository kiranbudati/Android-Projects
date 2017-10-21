package amritasai.campusconnect;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextPassword;
    private EditText editTextHallticket;
    private Button buttonLogin;
    private TextView signup;

    private static final String REGISTER_URL = "http://orltest.96.lt/as/login.php";

    public static final String hallTicket = "HALLTICKET";
    SharedPreferences sharedpreferences;

    //private GestureDetectorCompat gestureDetectorCompat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextHallticket = (EditText)findViewById(R.id.editTextHallticket);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        signup = (TextView)findViewById(R.id.signup);
        //gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());

        sharedpreferences = getSharedPreferences(hallTicket, Context.MODE_PRIVATE);

        buttonLogin.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    //swipe
/*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        //handle 'swipe left' action only

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

         /*
         Toast.makeText(getBaseContext(),
          event1.toString() + "\n\n" +event2.toString(),
          Toast.LENGTH_SHORT).show();

            if(event2.getX() < event1.getX()){
                //switch another activity
                Intent intent = new Intent(
                        MainActivity.this, Register.class);
                startActivity(intent);
            }

            return true;
        }
    }
*/
    //register

    @Override
    public void onClick(View v) {
        if(v == buttonLogin){
            loginUser();
        }
        if(v==signup){
            Intent intent = new Intent(MainActivity.this,Register.class);
            startActivity(intent);
        }
    }

    private void loginUser() {

        String password = editTextPassword.getText().toString().trim().toLowerCase();
        String hallticketnum = editTextHallticket.getText().toString().trim().toLowerCase();

        login(password,hallticketnum);
    }

    private void login(String password, final String hallticketnum) {
        class RegisterUser extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait",null, true, true);
            }

            /*
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            */

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("password",params[0]);
                data.put("hallticketnum",params[1]);


                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }

            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                loading.dismiss();
                if(s.equalsIgnoreCase("Success")){

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(hallTicket, hallticketnum);

                   Intent intent = new Intent(MainActivity.this, UserActivity.class);
                    intent.putExtra(hallTicket, hallticketnum);

                        finish();
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Invalid User Name or Password", Toast.LENGTH_LONG).show();
                    }
                }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(password,hallticketnum);
    }
}