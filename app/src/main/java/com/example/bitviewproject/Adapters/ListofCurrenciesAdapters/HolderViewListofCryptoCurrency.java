package com.example.bitviewproject.Adapters.ListofCurrenciesAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bitviewproject.R;

import io.realm.Realm;

public class HolderViewListofCryptoCurrency extends RecyclerView.ViewHolder {

    TextView txtNameCurrency, ID;
    ImageView iconCurrency;
    CardView cardView;
    Button button;
    Realm realm;

    public HolderViewListofCryptoCurrency(@NonNull View itemView) {
        super(itemView);
        ID = itemView.findViewById(R.id.cardID);
        txtNameCurrency = itemView.findViewById(R.id.nameCurrencyListof);
        iconCurrency = itemView.findViewById(R.id.iconCurrencyListof);
        cardView = itemView.findViewById(R.id.cardViewListof);
        button = itemView.findViewById(R.id.btnAddCurrency);
    }

}
