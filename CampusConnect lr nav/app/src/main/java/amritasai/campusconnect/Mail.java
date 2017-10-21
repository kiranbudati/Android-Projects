package amritasai.campusconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Mail extends AppCompatActivity implements View.OnClickListener{

    private Spinner hie;
    private EditText subject;
    private EditText message;
    private Button send;
    private EditText hall;

    private static final String REGISTER_URL = "http://orltest.96.lt/as/mail.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        hie =(Spinner)findViewById(R.id.hie);
        subject = (EditText)findViewById(R.id.subject);
        message = (EditText)findViewById(R.id.message);
        send = (Button) findViewById(R.id.send);
        hall = (EditText) findViewById(R.id.halltick);

        Intent intent = getIntent();
        String hallticketnum = intent.getStringExtra(MainActivity.hallTicket);

        TextView textView = (TextView) findViewById(R.id.hallticket);
        textView.setText("Welcome "+hallticketnum);
        send.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == send){
            sendMail();
        }
    }

    private void sendMail() {

        String hier = hie.getSelectedItem().toString();
        String sub = subject.getText().toString().trim().toLowerCase();
        String msg = message.getText().toString().trim().toLowerCase();
        String hno = hall.getText().toString();
        mail(hier,sub,msg,hno);
    }

    private void mail(String hier, String sub,String msg,String hno) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Mail.this, "Please Wait",null, true, true);
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
                data.put("hier",params[0]);
                data.put("subject",params[1]);
                data.put("message",params[2]);
                data.put("hno",params[3]);


                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(hier,sub,msg,hno);
    }
}
