package com.example.bitviewproject.Adapters.UserDetailsAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bitviewproject.R;

import io.realm.Realm;

public class HolderViewUserDetails  extends RecyclerView.ViewHolder {

    TextView idCurrency;
    TextView nameCurrency;
    TextView valueCurrency;
    ImageView imageView;
    Realm realm;

    public HolderViewUserDetails(@NonNull View itemView) {
        super(itemView);

        realm = Realm.getDefaultInstance();
        idCurrency = itemView.findViewById(R.id.idCurrencyUserDetails);
        nameCurrency = itemView.findViewById(R.id.nameCurrencyUserDetails);
        valueCurrency = itemView.findViewById(R.id.valueCurrencyUserDetails);
        imageView = itemView.findViewById(R.id.iconCurrencyListof);
    }
}
