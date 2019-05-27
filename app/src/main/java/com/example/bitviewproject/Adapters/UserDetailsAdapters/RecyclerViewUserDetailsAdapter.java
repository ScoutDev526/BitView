package com.example.bitviewproject.Adapters.UserDetailsAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bitviewproject.Model.CryptoCurrency;
import com.example.bitviewproject.R;

import java.util.ArrayList;

import io.realm.Realm;

public class RecyclerViewUserDetailsAdapter extends RecyclerView.Adapter<HolderViewUserDetails> {

    final Realm realm;
    final Context context;
    ArrayList <CryptoCurrency> cryptoCurrencies;

    public RecyclerViewUserDetailsAdapter(Realm realm, Context context, ArrayList<CryptoCurrency> cryptoCurrencies) {
        this.realm = realm;
        this.context = context;
        this.cryptoCurrencies = cryptoCurrencies;
    }

    @NonNull
    @Override
    public HolderViewUserDetails onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.userdetails_cardviews, viewGroup, false);
        return new HolderViewUserDetails(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderViewUserDetails holder, int i) {
        if (cryptoCurrencies != null && cryptoCurrencies.get(0).getId() != 0) {
            final CryptoCurrency currency = cryptoCurrencies.get(i);
            holder.idCurrency.setText(Integer.toString(currency.getId()));
            holder.nameCurrency.setText(currency.getName());
            holder.valueCurrency.setText(Integer.toString(currency.getValue()));
        }else {
            holder.idCurrency.setText("1234");
            holder.nameCurrency.setText("BitView");
            holder.valueCurrency.setText("979796");
            holder.imageView.setImageResource(R.drawable.bit);
        }

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
