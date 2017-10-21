package orl.amritasai.orlthingsspeak;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.os.Handler;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

import static orl.amritasai.orlthingsspeak.R.id.field1;
import static orl.amritasai.orlthingsspeak.R.id.mode;
import static orl.amritasai.orlthingsspeak.R.id.text;

public class MainActivity extends AppCompatActivity{

    Handler mHandler;
    private ListAdapter adapter;
    private final Handler handler = new Handler();
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private TextView mode;
    private TextView up_at;

    private ListView lv;
    private Button button_on;
    private Button button_off;

    private static String field1="";
    private static String on="ON";
    private static String off="OFF";
    private static String updated_at="";

    final Handler h = new Handler();

    int delay = 3000; //milliseconds

    // URL to get contacts JSON
    private static String url = "https://api.thingspeak.com/channels/221295/fields/1.json?results=1";

    //private static String on_url="https://api.thingspeak.com/update?api_key=ICCEJCSBW4N5PY9H&field2=ON";
    //private static String off_url="https://api.thingspeak.com/update?api_key=ICCEJCSBW4N5PY9H&field2=OFF";

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);

        button_on = (Button)findViewById(R.id.button_on);
        button_off = (Button)findViewById(R.id.button_off) ;
        mode = (TextView) findViewById(R.id.mode);
        up_at = (TextView) findViewById(R.id.up_at);


        h.postDelayed(new Runnable(){
            public void run(){
                mode.setText(field1);
                up_at.setText(updated_at);
                h.postDelayed(this, delay);
                new GetContacts().execute();
            }
        }, delay);
        button_on.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("https://api.thingspeak.com/update");

                try {
                    // Add your data
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("api_key", "ICCEJCSBW4N5PY9H"));
                    nameValuePairs.add(new BasicNameValuePair("field2", "ON"));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);

                    if(response!=null)
                    {
                        Toast.makeText(getApplicationContext(), "ON", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "ERROR ", Toast.LENGTH_LONG).show();
                    }

                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                }

            }
        });

        button_off.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("https://api.thingspeak.com/update");

                try {
                    // Add your data
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("api_key", "ICCEJCSBW4N5PY9H"));
                    nameValuePairs.add(new BasicNameValuePair("field2", "OFF"));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);

                    if(response!=null)
                    {
                        Toast.makeText(getApplicationContext(), "OFF", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "ERROR ", Toast.LENGTH_LONG).show();
                    }
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                }

            }
        });


        new GetContacts().execute();

    }



    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

            if(field1.equals(on)){
                button_on.setEnabled(false);
            }
            else if(field1.equals(off)){
                button_off.setEnabled(false);
            }
            else {
                button_on.setEnabled(false);
                button_off.setEnabled(false);
            }

        }

        @Override
        protected Void doInBackground(Void... arg0) {


            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);


                    // Getting JSON Array node
                    JSONArray feeds = jsonObj.getJSONArray("feeds");

                    // looping through All feeds
                    for (int i = 0; i < feeds.length(); i++) {

                        JSONObject c = feeds.getJSONObject(i);

                        JSONObject chaObj=(new JSONObject(jsonStr)).getJSONObject("channel");

                        //String created_at = c.getString("created_at");
                        //String entry_id = c.getString("entry_id");
                        field1 = c.getString("field1");
                        updated_at = chaObj.getString("updated_at");


                        /**
                         String address = c.getString("address");
                         String gender = c.getString("gender");

                         // Phone node is JSON Object
                         JSONObject phone = c.getJSONObject("phone");
                         String mobile = phone.getString("mobile");
                         String home = phone.getString("home");
                         String office = phone.getString("office");
                         */
                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        //contact.put("created_at", created_at);
                        //contact.put("entry_id", entry_id);
                        contact.put("field1", field1);
                        contact.put("updated_at",updated_at);
                        //contact.put("mobile", mobile);

                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            /**
             * Updating parsed JSON data into ListView
             * */


            mode.setText(field1);
            up_at.setText(updated_at);
            //adapter = new SimpleAdapter(MainActivity.this, contactList, R.layout.list_item,new String[]{"field1","updated_at"}, new int[]{ R.id.field1,R.id.updated_at});


            //lv.setAdapter(adapter);

        }

    }

}
