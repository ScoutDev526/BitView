package com.example.bitviewproject.Adapters.UserDetailsAdapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bitviewproject.Model.CryptoCurrency;
import com.example.bitviewproject.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import io.realm.Realm;

public class RecyclerViewUserDetailsAdapter extends RecyclerView.Adapter<HolderViewUserDetails> {

    Realm realm;
    final Context context;
    ArrayList <CryptoCurrency> cryptoCurrencies;

    public RecyclerViewUserDetailsAdapter(Realm realm, Context context, ArrayList<CryptoCurrency> cryptoCurrencies) {
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
            String doubleValue = new DecimalFormat("#.##").format(currency.getValue());
            holder.nameCurrency.setText(currency.getFullName());
            holder.idCurrency.setText(Integer.toString(currency.getId()));
            holder.shortNameCurrency.setText(currency.getShortName());
            holder.valueCurrency.setText(doubleValue + " €");
            Resources resources = context.getResources();
            final int resourceId = resources.getIdentifier(currency.getFullName().toLowerCase().replace(" ", ""),
                    "drawable", context.getPackageName());
            holder.imageView.setImageResource(resourceId);
        }else {
            holder.idCurrency.setText("1234");
            holder.shortNameCurrency.setText("BVW");
            holder.valueCurrency.setText("979796 €");
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
