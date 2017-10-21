package amritasai.campusconnect;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextName;
    private EditText editTextPassword;
    private EditText editTextHallticket;
    private EditText editTextEmail;
    private Button buttonRegister;
    private TextView login;
    private Spinner Year;
    private Spinner Branch;

   // private GestureDetectorCompat gestureDetectorCompat;

    private static final String REGISTER_URL = "http://orltest.96.lt/as/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        Year=(Spinner)findViewById(R.id.year);
        Branch=(Spinner)findViewById(R.id.branch);
        editTextHallticket = (EditText)findViewById(R.id.editTextHallticket);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        login = (TextView)findViewById(R.id.login);
      //  gestureDetectorCompat = new GestureDetectorCompat(this, new My2ndGestureListener());

        buttonRegister.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    /*
    //swipe

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class My2ndGestureListener extends GestureDetector.SimpleOnGestureListener {
        //handle 'swipe right' action only

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

         /*
         Toast.makeText(getBaseContext(),
          event1.toString() + "\n\n" +event2.toString(),
          Toast.LENGTH_SHORT).show();

            if(event2.getX() > event1.getX()){

                finish();
            }

            return true;
        }
    }
*/

    //register
    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
        if(v == login){

            Intent intent = new Intent(Register.this,MainActivity.class);
            startActivity(intent);
        }
    }

    private void registerUser() {
        String name = editTextName.getText().toString().trim().toLowerCase();
        String password = editTextPassword.getText().toString().trim().toLowerCase();
        String year = Year.getSelectedItem().toString();
        String branch = Branch.getSelectedItem().toString() ;
        String hallticketnum = editTextHallticket.getText().toString().trim().toLowerCase();
        String email = editTextEmail.getText().toString().trim().toLowerCase();
        register(name,password,year,branch,hallticketnum,email);
    }

    private void register(String name, String password,String year,String branch,String hallticketnum, String email) {
        class RegisterUser extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Register.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("name",params[0]);
                data.put("password",params[1]);
                data.put("year",params[2]);
                data.put("branch",params[3]);
                data.put("hallticketnum",params[4]);
                data.put("email",params[5]);


                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name,password,year,branch,hallticketnum,email);
    }
}