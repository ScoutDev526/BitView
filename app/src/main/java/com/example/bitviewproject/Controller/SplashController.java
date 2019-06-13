package com.example.bitviewproject.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.bitviewproject.R;
import com.example.bitviewproject.Service.impl.CryptoCurrencyServiceImpl;
import com.example.bitviewproject.Service.impl.UserServiceImpl;

public class SplashController extends AppCompatActivity {

    private static final int DURATION_SPLASH = 2500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        UserServiceImpl userService = new UserServiceImpl();
        CryptoCurrencyServiceImpl currencyService = new CryptoCurrencyServiceImpl(getApplicationContext());
        userService.addUserFirstTime();
        currencyService.addCryptoCurrencyFirstTime();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashController.this, MainController.class);
                startActivity(intent);
                finish();
            }
        }, DURATION_SPLASH);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
