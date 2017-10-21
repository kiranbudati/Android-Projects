package amritasai.campusconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView account;
    private ImageView chat;
    private ImageView mail;
    private ImageView logout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        account = (ImageView)findViewById(R.id.account);
        chat = (ImageView)findViewById(R.id.chat);
        mail = (ImageView)findViewById(R.id.mail);
        logout = (ImageView)findViewById(R.id.mail);

        account.setOnClickListener(this);
        chat.setOnClickListener(this);
        mail.setOnClickListener(this);
        logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == account){
            Intent intent = new Intent(UserActivity.this,Mail.class);
            startActivity(intent);
        }
        if(v==chat){
            Intent intent = new Intent(UserActivity.this,Chat.class);
            startActivity(intent);
        }
        if(v==mail){
            Intent intent = new Intent(UserActivity.this,Mail.class);
            startActivity(intent);
        }
        if(v==logout){
            Intent intent = new Intent(UserActivity.this,Logout.class);
            startActivity(intent);
        }
    }
}
