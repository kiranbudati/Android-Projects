package com.journaldev.loginphpmysql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class VialBoxAvtivity extends AppCompatActivity {

    String url="http://kiran.net16.net/login/getData.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vial_box_avtivity);

        final ListView lv= (ListView) findViewById(R.id.lv);
        final Downloader d=new Downloader(this,url,lv);
        Button btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(VialBoxAvtivity.this, Vial1.class);
                VialBoxAvtivity.this.startActivity(myIntent);
            }
        });

        Button btn2=(Button)findViewById(R.id.button2);

        btn2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(VialBoxAvtivity.this, Vial2.class);
                VialBoxAvtivity.this.startActivity(myIntent);
            }
        });

        Button btn3=(Button)findViewById(R.id.button3);

        btn3.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(VialBoxAvtivity.this, Vial3.class);
                VialBoxAvtivity.this.startActivity(myIntent);
            }
        });

        Button btn4=(Button)findViewById(R.id.button4);

        btn4.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(VialBoxAvtivity.this, Vial4.class);
                VialBoxAvtivity.this.startActivity(myIntent);
            }
        });

        Button btn5=(Button)findViewById(R.id.button10);

        btn5.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(VialBoxAvtivity.this, People.class);
                VialBoxAvtivity.this.startActivity(myIntent);
            }
        });


    }
}
