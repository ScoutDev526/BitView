package com.example.bitviewproject.Adapters.ListofCurrenciesAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bitviewproject.R;

public class HolderViewListofCryptoCurrency extends RecyclerView.ViewHolder {

    TextView txtNameCurrency;
    ImageView iconCurrency;
    CardView cardView;

    public HolderViewListofCryptoCurrency(@NonNull View itemView) {
        super(itemView);

        txtNameCurrency = itemView.findViewById(R.id.nameCurrencyListof);
        iconCurrency = itemView.findViewById(R.id.iconCurrencyListof);
        cardView = itemView.findViewById(R.id.cardViewListof);
    }
}
