package com.example.bitviewproject.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.bitviewproject.Adapters.MainAdapters.RecyclerViewPrincipalAdapter;
import com.example.bitviewproject.Adapters.UserDetailsAdapters.RecyclerViewUserDetailsAdapter;
import com.example.bitviewproject.Model.CryptoCurrency;
import com.example.bitviewproject.Model.User;
import com.example.bitviewproject.R;

import java.util.ArrayList;

import io.realm.Realm;

public class DetailsUsersController extends AppCompatActivity {

    RecyclerView recyclerView;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsusers);

        realm = Realm.getDefaultInstance();
        recyclerView = findViewById(R.id.recyclerViewUserDetails);

        SharedPreferences preferences = getSharedPreferences("SharedPreferencesUserLogin", Context.MODE_PRIVATE);
        String id = preferences.getString("userId", "0");

        Log.e("DETAILS", "------>" + id);

        User user = realm.where(User.class).equalTo("id", Integer.parseInt(id)).findFirst();
        if (user == null){
            Log.e("DETAILS", "EL USUARIO ES NULO GUEY");
        }

        for (CryptoCurrency c : user.getCryptoCurrencies()) {
            Log.i("DETAILS", "------" + c.getName() + "-------");
        }

        ArrayList<CryptoCurrency> arrayList = new ArrayList<>();
        for (CryptoCurrency c : realm.where(User.class).equalTo("id", Integer.parseInt(id)).findFirst().getCryptoCurrencies()){
            arrayList.add(c);
            Log.e("ADDTOARRAYLIST", "-------->" + c.getName());
        }

        RecyclerViewUserDetailsAdapter controller = new
                RecyclerViewUserDetailsAdapter(realm, this, arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(controller);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
