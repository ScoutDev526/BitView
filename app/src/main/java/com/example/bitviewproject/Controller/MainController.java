package com.example.bitviewproject.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bitviewproject.Adapters.MainAdapters.RecyclerViewPrincipalAdapter;
import com.example.bitviewproject.Helper.Helper;
import com.example.bitviewproject.R;
import com.example.bitviewproject.Service.impl.CryptoCurrencyService;
import com.example.bitviewproject.Service.impl.UserService;

import io.realm.Realm;
import io.realm.RealmChangeListener;

public class MainController extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        //Toolbar toolbar = findViewById(R.id.toolBar);
        //setSupportActionBar(toolbar);
        //FloatingActionButton fab = findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "ACTION", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });*/
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        //        this, drawerLayout, toolbar, R.string.open, R.string.close);
        //drawerLayout.addDrawerListener(toggle);
        //toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        realm = Realm.getDefaultInstance();

        userService = new UserService(realm);
        cryptoCurrencyService = new CryptoCurrencyService(realm);


        userService.addUserFirstTime();
        cryptoCurrencyService.addCryptoCurrencyFirstTime();

        recyclerView = findViewById(R.id.recycler);

        helper = new Helper(realm);
        helper.getCryptoCurreciesFromDB();

        RecyclerViewPrincipalAdapter controller = new
                RecyclerViewPrincipalAdapter(this, helper.refreshCryptoCurrencies(), realm);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(controller);

        refresh();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
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

        if(id == R.id.action_settings) {
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

    private void refresh() {

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
