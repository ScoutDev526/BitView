package com.example.bitviewproject.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.bitviewproject.Model.CryptoCurrency;
import com.example.bitviewproject.Model.User;
import com.example.bitviewproject.R;

import io.realm.Realm;

public class DetailsUsersController extends AppCompatActivity {

    TextView test;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsuserscontroller);

        test = findViewById(R.id.mostrar);

        realm = Realm.getDefaultInstance();

        SharedPreferences preferences = getSharedPreferences("SharedPreferencesUserLogin", Context.MODE_PRIVATE);

        String id = preferences.getString("userId", "0");
        User user = realm.where(User.class).equalTo("id", Integer.parseInt(id)).findFirst();
        String result = "";
        for(CryptoCurrency c : user.getCryptoCurrencies()){
            result = result + c.getName() + " -- ";
        }
        test.setText(result);
    }
}
