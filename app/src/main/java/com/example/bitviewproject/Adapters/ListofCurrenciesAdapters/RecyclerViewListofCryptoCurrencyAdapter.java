package com.example.bitviewproject.Adapters.ListofCurrenciesAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bitviewproject.Controller.CryptoCurrencyController;
import com.example.bitviewproject.Model.CryptoCurrency;
import com.example.bitviewproject.R;

import java.util.ArrayList;

import io.realm.Realm;

public class RecyclerViewListofCryptoCurrencyAdapter extends RecyclerView.Adapter<HolderViewListofCryptoCurrency> {

    Realm realm;
    Context context;
    ArrayList <CryptoCurrency> cryptoCurrencies;

    public RecyclerViewListofCryptoCurrencyAdapter(Realm realm, Context context, ArrayList<CryptoCurrency> cryptoCurrencies) {
        this.realm = realm;
        this.context = context;
        this.cryptoCurrencies = cryptoCurrencies;
    }

    @NonNull
    @Override
    public HolderViewListofCryptoCurrency onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.listof_cardviews, viewGroup, false);
        return new HolderViewListofCryptoCurrency(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderViewListofCryptoCurrency holder, int i) {
        final CryptoCurrency currency = cryptoCurrencies.get(i);

        holder.txtNameCurrency.setText(currency.getName());
        //holder.txtValue.setText(Integer.toString(currency.getValue()));

        holder.iconCurrency.setImageResource(R.drawable.flip);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CryptoCurrencyController.class);
                intent.putExtra("currencyId", currency.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cryptoCurrencies.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
