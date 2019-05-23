package com.example.bitviewproject.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.bitviewproject.Adapters.MainAdapters.RecyclerViewPrincipalAdapter;
import com.example.bitviewproject.Helper.Helper;
import com.example.bitviewproject.R;
import com.example.bitviewproject.Service.impl.CryptoCurrencyService;
import com.example.bitviewproject.Service.impl.UserService;

import io.realm.Realm;
import io.realm.RealmChangeListener;

public class MainController extends AppCompatActivity {

    RecyclerView recyclerView;

    RealmChangeListener realmChangeListener;

    UserService userService;
    CryptoCurrencyService cryptoCurrencyService;

    Realm realm;
    Helper helper;

    Button changeLayout, changeLogin, actionLogout, changeUserDetails;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        realm = Realm.getDefaultInstance();

        userService = new UserService(realm);
        cryptoCurrencyService = new CryptoCurrencyService(realm);


        userService.addUserFirstTime();
        cryptoCurrencyService.addCryptoCurrencyFirstTime();

        changeLayout = findViewById(R.id.btnChangeLayout);
        changeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListofCryptoCurrenciesActivity.class);
                startActivity(intent);
            }
        });
        changeLogin = findViewById(R.id.btnLoginActivity);
        changeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginController.class);
                startActivity(intent);
            }
        });

        actionLogout = findViewById(R.id.btnLogOut);
        actionLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("SharedPreferencesUserLogin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("userId");
                editor.apply();
            }
        });

        changeUserDetails = findViewById(R.id.btnProfile);
        changeUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailsUsersController.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recycler);

        helper = new Helper(realm);
        helper.getCryptoCurreciesFromDB();

        RecyclerViewPrincipalAdapter controller = new
                RecyclerViewPrincipalAdapter(this, helper.refreshCryptoCurrencies(), realm);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(controller);

        refresh();

    }

    private void refresh(){

        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                RecyclerViewPrincipalAdapter recyclerViewPrincipalAdapter =
                        new RecyclerViewPrincipalAdapter(MainController.this, helper.refreshCryptoCurrencies(), realm);
            }
        };
        realm.addChangeListener(realmChangeListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.removeAllChangeListeners();
        realm.close();
    }
}
