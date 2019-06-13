package com.example.bitviewproject.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bitviewproject.Adapters.MainAdapters.RecyclerViewPrincipalAdapter;
import com.example.bitviewproject.Helper.Helper;
import com.example.bitviewproject.Helper.HelperBitCoinAPI;
import com.example.bitviewproject.Model.CryptoCurrency;
import com.example.bitviewproject.R;
import com.example.bitviewproject.Service.impl.CryptoCurrencyServiceImpl;
import com.example.bitviewproject.Service.impl.UserServiceImpl;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import okhttp3.OkHttpClient;

public class MainController extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;

    RealmChangeListener realmChangeListener;

    UserServiceImpl userServiceImpl;
    CryptoCurrencyServiceImpl cryptoCurrencyServiceImpl;

    Realm realm;
    Helper helper;
    HelperBitCoinAPI bitCoinAPI;

    //Button changeLayout, changeLogin, actionLogout, changeUserDetails;

    public static final String JSON_LIST_CURRENCIES = "https://api.cryptonator.com/api/full/btc-usd";
    private OkHttpClient okHttpClient = new OkHttpClient();

    TextView textView;
    String value;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        realm = Realm.getDefaultInstance();
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        userServiceImpl = new UserServiceImpl();
        cryptoCurrencyServiceImpl = new CryptoCurrencyServiceImpl(getApplicationContext());
        userServiceImpl.addUserFirstTime();
        cryptoCurrencyServiceImpl.addCryptoCurrencyFirstTime();
        recyclerView = findViewById(R.id.recycler);
        helper = new Helper();
        helper.getCryptoCurreciesFromDB();
        RecyclerViewPrincipalAdapter controller = new
                RecyclerViewPrincipalAdapter(this, helper.refreshCryptoCurrencies());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(controller);
        textView = findViewById(R.id.textView2);
        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                RecyclerViewPrincipalAdapter recyclerViewPrincipalAdapter =
                        new RecyclerViewPrincipalAdapter(MainController.this, helper.refreshCryptoCurrencies());
            }
        };
        realm.addChangeListener(realmChangeListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_listCurrencies:
                Intent intentList = new Intent(getApplicationContext(), ListofCryptoCurrenciesActivity.class);
                startActivity(intentList);
                Toast.makeText(this, "Lista", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_profile:
                Intent intentProfile = new Intent(getApplicationContext(), DetailsUsersController.class);
                startActivity(intentProfile);
                Toast.makeText(this, "GALLERY", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logIn:
                Intent intentLogin = new Intent(getApplicationContext(), LoginController.class);
                startActivity(intentLogin);
                Toast.makeText(this, "SHARE", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logOut:
                SharedPreferences preferences = getSharedPreferences("SharedPreferencesUserLogin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                //editor.remove("userId");
                editor.clear();
                editor.apply();
                Toast.makeText(this, "SEND", Toast.LENGTH_SHORT).show();
                break;
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    private void refresh(final Realm realm) {
        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                RecyclerViewPrincipalAdapter recyclerViewPrincipalAdapter =
                        new RecyclerViewPrincipalAdapter(MainController.this, helper.refreshCryptoCurrencies());
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

    private void updateCurren() {
        System.out.println("UPDATECURREN");
        System.out.println(value);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CryptoCurrency currency = realm.where(CryptoCurrency.class).findFirst();
                System.out.println(currency.getShortName());
                currency.setShortName("BitCoin");
                currency.setValue(0);
                currency.setImgName("cr1");
            }
        });
    }
}
