package it.k4ppaj.droidinfo.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import it.k4ppaj.droidinfo.R;
import it.k4ppaj.droidinfo.helper.DisplayHelper;
import it.k4ppaj.droidinfo.helper.SoCHelper;

public class SplashActivity extends AppCompatActivity implements GLSurfaceView.Renderer {

    private SharedPreferences sharedPreferences;
    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences("DroidInfo", MODE_PRIVATE);

        TextView textViewWelcomeTo = (TextView) findViewById(R.id.textViewWelcomeToSplash);

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        window.setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark));

        this.glSurfaceView = new GLSurfaceView(this);
        this.glSurfaceView.setRenderer(this);
        ((ViewGroup) textViewWelcomeTo.getParent()).addView(glSurfaceView);

        sharedPreferences
                .edit()
                .putString("SCREEN_INCHES", DisplayHelper.getScreenSize(SplashActivity.this))
                .putString("RESOLUTION", DisplayHelper.getResolution(SplashActivity.this))
                .apply();

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

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        sharedPreferences
                .edit()
                .putString("GPU_VENDOR", SoCHelper.getGPUVendor(gl10))
                .putString("GPU_RENDERER", SoCHelper.getGPURenderer(gl10))
                .apply();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                glSurfaceView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {

    }

    @Override
    public void onDrawFrame(GL10 gl10) {

    }
}