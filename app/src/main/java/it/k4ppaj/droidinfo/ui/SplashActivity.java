package it.k4ppaj.droidinfo.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import it.k4ppaj.droidinfo.R;
import it.k4ppaj.droidinfo.helper.AndroidHelper;
import it.k4ppaj.droidinfo.helper.SoCHelper;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences("DroidInfo", MODE_PRIVATE);

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        window.setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark));

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                boolean firstRun = sharedPreferences.getBoolean("FIRST_RUN", true);
                if (firstRun) {
                    startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }

            }
        }, 2000);
    }
}