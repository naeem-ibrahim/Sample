package com.algorepublic.brandmaker.ui.splash;
import android.content.Intent;
import android.os.Bundle;

import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.databinding.ActivitySplashBinding;
import com.algorepublic.brandmaker.ui.login.LoginActivity;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class SplashActivity extends AppCompatActivity {
  ActivitySplashBinding b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b= DataBindingUtil.setContentView(this,R.layout.activity_splash);

        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .repeat(0)
                .playOn(b.cv);

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    runOnUiThread(() -> YoYo.with(Techniques.FadeOut)
                            .duration(1000)
                            .repeat(0)
                            .playOn(b.cv));
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                }
            }
        };
        timerThread.start();


    }
}
