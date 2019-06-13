package com.example.bitviewproject.Adapters.UserDetailsAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bitviewproject.R;

public class HolderViewUserDetails  extends RecyclerView.ViewHolder {

    TextView nameCurrency;
    TextView idCurrency;
    TextView shortNameCurrency;
    TextView valueCurrency;
    ImageView imageView;

    public HolderViewUserDetails(@NonNull View itemView) {
        super(itemView);
        idCurrency = itemView.findViewById(R.id.idCurrencyUserDetails);
        shortNameCurrency = itemView.findViewById(R.id.nameCurrencyShortUserDetails);
        valueCurrency = itemView.findViewById(R.id.valueCurrencyUserDetails);
        imageView = itemView.findViewById(R.id.iconCurrencyListof);
        nameCurrency = itemView.findViewById(R.id.nameCurrencyUserDetails);
    }
}
