package com.journaldev.loginphpmysql;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class People extends Activity {

    EditText name;
    EditText age;
    EditText vial_id;

    String GetName, GetAge, GetVail_id;

    Button buttonSubmit ;

    String DataParseUrl = "http://kiran.net16.net/people.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        name = (EditText)findViewById(R.id.name);
        age = (EditText)findViewById(R.id.age);
        vial_id = (EditText)findViewById(R.id.vial_id);

        buttonSubmit = (Button)findViewById(R.id.buttonRegister);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                GetDataFromEditText();

                SendDataToServer(GetName, GetAge, GetVail_id);

            }
        });
    }


    public void GetDataFromEditText(){

        GetName = name.getText().toString();
        GetAge = age.getText().toString();
        GetVail_id = vial_id.getText().toString();

    }


    public void SendDataToServer(final String name, final String age, final String vial_id){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String QuickName = name ;
                String QuickAge = age;
                String Quickvial_id = vial_id;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("name", QuickName));
                nameValuePairs.add(new BasicNameValuePair("age", QuickAge));
                nameValuePairs.add(new BasicNameValuePair("vial_id", Quickvial_id));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(DataParseUrl);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Submit Successfully";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Toast.makeText(People.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(name, age, vial_id);
    }

}