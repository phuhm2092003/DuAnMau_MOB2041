package fpt.edu.projectlibmanna.ui.welcome;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.ui.login.LoginActivity;


public class WelcomeActivity extends AppCompatActivity {

    public static final int TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @SuppressLint("PrivateResource")
            @Override
            public void run() {
                startScreenLogin();
            }
        }, TIME_OUT);

    }

    private void startScreenLogin() {
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
    }


}