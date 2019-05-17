package com.example.bitviewproject.Adapters.MainAdapters;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bitviewproject.R;

public class HolderViewPrincipalAdapter extends RecyclerView.ViewHolder {

    TextView txtNameCurrency;
    TextView txtValue;
    ImageView iconCurrency;
    CardView cardView;

    public HolderViewPrincipalAdapter(@NonNull View itemView) {
        super(itemView);

        txtNameCurrency = itemView.findViewById(R.id.nameCurrency);
        txtValue = itemView.findViewById(R.id.price);
        iconCurrency = itemView.findViewById(R.id.iconCurrency);
        cardView = itemView.findViewById(R.id.cardView);
    }
}
