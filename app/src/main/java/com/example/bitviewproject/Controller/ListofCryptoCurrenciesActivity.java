package com.example.bitviewproject.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.bitviewproject.Adapters.ListofCurrenciesAdapters.RecyclerViewListofCryptoCurrencyAdapter;
import com.example.bitviewproject.Helper.Helper;
import com.example.bitviewproject.R;

import io.realm.Realm;

public class ListofCryptoCurrenciesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Helper helper;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listof_cryptocurrencies);

        recyclerView = findViewById(R.id.recyclerViewListCurrencies);
        try {
            realm = Realm.getDefaultInstance();
            helper = new Helper();
            helper.getCryptoCurrenciesSortByValueFromDB();
            RecyclerViewListofCryptoCurrencyAdapter adapter =
                    new RecyclerViewListofCryptoCurrencyAdapter(this, helper.refreshCryptoCurrencies());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        } finally {
            realm.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
