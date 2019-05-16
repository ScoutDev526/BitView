package com.example.bitviewproject.Controller.RecyclerViewControllers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bitviewproject.R;

public class HolderViewPrincipalController extends RecyclerView.ViewHolder {

    TextView txtNameCurrency;
    TextView txtValue;

    public HolderViewPrincipalController(@NonNull View itemView) {
        super(itemView);

        txtNameCurrency = itemView.findViewById(R.id.nameCurrency);
        txtValue = itemView.findViewById(R.id.price);
    }
}
