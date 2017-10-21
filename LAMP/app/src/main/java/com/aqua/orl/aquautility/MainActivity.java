package com.aqua.orl.aquautility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class MainActivity extends Activity {
    //private String TAG = "WifiAPActivity";

    private final Handler handler = new Handler();
    EditText editUsername, editPassword;
    Spinner  editNumber_of_alarms,editNumer_of_fishes;
    static TextView ipadd,hotspot_username;
    Button btnSubmit;
    static RelativeLayout rt;
    private static final int WRITE_PERMISSION_REQUEST = 5000;

    public String URL;
    JSONParser jsonParser=new JSONParser();

    int i=0;

    boolean wasAPEnabled = false;
    static WifiAP wifiAp;
    WifiApManager wifiApManager;
    private WifiManager wifi;
    static Button btnWifiToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi);



        rt = (RelativeLayout) findViewById(R.id.rl);
        editUsername = (EditText) findViewById(R.id.username);
        String uname = editUsername.getText().toString();
        if (TextUtils.isEmpty(uname)) {
            editUsername.setError("Please Enter User Name");
        }
        editPassword = (EditText) findViewById(R.id.password);
        String pass = editPassword.getText().toString();
        if (TextUtils.isEmpty(pass)) {
            editPassword.setError("Please Enter Password");
        }
        editNumber_of_alarms = (Spinner) findViewById(R.id.no_of_alarms);
        editNumer_of_fishes = (Spinner) findViewById(R.id.no_of_fishes);
        btnSubmit = (Button) findViewById(R.id.submit);

        ipadd = (TextView) findViewById(R.id.ipadd);
        hotspot_username=(TextView)findViewById(R.id.hotspot_username);

        if (Build.VERSION.SDK_INT <= 22){
            doTheAutoRefresh();
        }

        //getting hotspot clients ssid

        wifiApManager = new WifiApManager(this);
        if (wifiApManager.getWifiApState() == WIFI_AP_STATE.WIFI_AP_STATE_ENABLED) {
            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AttemptSubmit attemptSubmit= new AttemptSubmit();
                attemptSubmit.execute(editUsername.getText().toString(),editPassword.getText().toString(),String.valueOf(editNumber_of_alarms.getSelectedItem()),String.valueOf(editNumer_of_fishes.getSelectedItem()),"");
            }
        });


        wifiAp = new WifiAP();
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiAp.toggleWiFiAP(wifi, MainActivity.this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_SETTINGS)
                != PackageManager.PERMISSION_GRANTED) {
            updateStatusDisplay();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WRITE_PERMISSION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    updateStatusDisplay();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getClientList(); // this is where you put your refresh code
                doTheAutoRefresh();
            }
        }, 1000);
    }



    private class AttemptSubmit extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override

        protected JSONObject doInBackground(String... args) {


            String no_of_fishes = args[3];
            String no_of_alarms = args[2];
            String password = args[1];
            String username= args[0];

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("no_of_alarms", no_of_alarms));
            params.add(new BasicNameValuePair("no_of_fishes", no_of_fishes));

            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader("/proc/net/arp"));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] clientInfo = line.split(" +");
                    String URL = clientInfo[3].toString();
                    if (URL.matches("..:..:..:..:..:..")) {
                        Toast.makeText(getBaseContext(),"details sending to"+URL,Toast.LENGTH_LONG).show();
                    }
                }

                return new JSONObject(URL);
            } catch (java.io.IOException aE) {
                aE.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);


            return json;

        }


        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();


            if (result != null) {
                Toast.makeText(getApplicationContext(),"Data "+editUsername.getText()+"Successfully sent",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
            }


        }

    }


    @Override
    public void onResume() {
        super.onResume();
        if (wasAPEnabled) {
            if (wifiAp.getWifiAPState()!=wifiAp.WIFI_AP_STATE_ENABLED && wifiAp.getWifiAPState()!=wifiAp.WIFI_AP_STATE_ENABLING){
                wifiAp.toggleWiFiAP(wifi, MainActivity.this);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        boolean wifiApIsOn = wifiAp.getWifiAPState()==wifiAp.WIFI_AP_STATE_ENABLED || wifiAp.getWifiAPState()==wifiAp.WIFI_AP_STATE_ENABLING;
        if (wifiApIsOn) {
            wasAPEnabled = true;
            wifiAp.toggleWiFiAP(wifi, MainActivity.this);
        } else {
            wasAPEnabled = false;
        }
    }


    public  static void updateStatusDisplay(){
        if (wifiAp.getWifiAPState()==wifiAp.WIFI_AP_STATE_ENABLED || wifiAp.getWifiAPState()==wifiAp.WIFI_AP_STATE_ENABLING || rt.getVisibility()==View.INVISIBLE) {

            return;
            //findViewById(R.id.bg).setBackgroundResource(R.drawable.bg_wifi_on);
        } else {

            //findViewById(R.id.bg).setBackgroundResource(R.drawable.bg_wifi_off);

        }
        getClientList();

    }



    public static ArrayList<String> getClientList() {
        ArrayList<String> clientList = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] clientInfo = line.split(" +");
                String mac = clientInfo[3];
                if (mac.matches("..:..:..:..:..:..")) {
                    clientList.add(clientInfo[0]);
                    ipadd.setText("Client Ip:"+clientInfo[0]);
                }
            }
        } catch (java.io.IOException aE) {
            aE.printStackTrace();
            return null;
        }

        return clientList;
    }

}
