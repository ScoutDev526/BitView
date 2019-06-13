package com.example.bitviewproject.Adapters.MainAdapters;

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
    TextView txtUpdate;
    ImageView iconCurrency;
    CardView cardView;

    public HolderViewPrincipalAdapter(@NonNull View itemView) {
        super(itemView);

        txtNameCurrency = itemView.findViewById(R.id.nameCurrency);
        txtValue = itemView.findViewById(R.id.price);
        txtUpdate = itemView.findViewById(R.id.update);
        iconCurrency = itemView.findViewById(R.id.iconCurrency);
        cardView = itemView.findViewById(R.id.cardView);
    }
}
