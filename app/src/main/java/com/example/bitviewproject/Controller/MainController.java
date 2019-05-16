package com.example.bitviewproject.Controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.bitviewproject.Controller.RecyclerViewControllers.RecyclerViewPrincipalController;
import com.example.bitviewproject.Helper.Helper;
import com.example.bitviewproject.Model.CryptoCurrency;
import com.example.bitviewproject.Model.User;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        realm = Realm.getDefaultInstance();

        userService = new UserService(realm);
        cryptoCurrencyService = new CryptoCurrencyService(realm);

        userService.addUserFirstTime();
        if (realm.isClosed()){
            realm = Realm.getDefaultInstance();
        }
        cryptoCurrencyService.addCryptoCurrencyFirstTime();


        recyclerView = findViewById(R.id.recycler);

        helper = new Helper(realm);
        helper.getCryptoCurreciesFromDB();

        RecyclerViewPrincipalController controller = new
                RecyclerViewPrincipalController(this, helper.refreshCryptoCurrencies(), realm);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(controller);

        refresh();

    }

    private void refresh(){

        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                RecyclerViewPrincipalController recyclerViewPrincipalController =
                        new RecyclerViewPrincipalController(MainController.this, helper.refreshCryptoCurrencies(), realm);
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
