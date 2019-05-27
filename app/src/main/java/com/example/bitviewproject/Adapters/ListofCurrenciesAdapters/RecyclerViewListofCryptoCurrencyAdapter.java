package com.example.bitviewproject.Adapters.ListofCurrenciesAdapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bitviewproject.Controller.CryptoCurrencyController;
import com.example.bitviewproject.Model.CryptoCurrency;
import com.example.bitviewproject.Model.User;
import com.example.bitviewproject.R;

import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;

public class RecyclerViewListofCryptoCurrencyAdapter extends RecyclerView.Adapter<HolderViewListofCryptoCurrency> {

    static final String TAG = "ADAPTERLISTA";

    final Realm realm;
    final Context context;
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
    public void onBindViewHolder(@NonNull final HolderViewListofCryptoCurrency holder, int i) {
        final CryptoCurrency currency = cryptoCurrencies.get(i);
        final int currenId = currency.getId();

        Log.e(TAG, "-----------------------------------");
        Log.e(TAG, Integer.toString(currenId));

        holder.txtNameCurrency.setText(currency.getName());
        holder.ID.setText(Integer.toString(currency.getId()));
        Log.e(TAG, "----> currencyID = " + currency.getId());
        holder.iconCurrency.setImageResource(R.drawable.flip);

        SharedPreferences preferences = context.getSharedPreferences("SharedPreferencesUserLogin", Context.MODE_PRIVATE);
        String id = preferences.getString("userId", "0");
        User user = realm.where(User.class).equalTo("id", Integer.parseInt(id)).findFirst();
        boolean used = false;
        try {
            if(user != null) {
                for (CryptoCurrency c: user.getCryptoCurrencies()) {
                    Log.i(TAG, "-----> " + c.getId());
                    if (c.getId() == currency.getId()){
                        used = true; Log.e(TAG, "-------> used true");
                        break;
                    }
                }
            }

        }catch (Exception e) {
            Log.e("ERROR", String.valueOf(e));
        }
        if (used) holder.button.setEnabled(false);

        // TODO: EVENTOS
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCryptoCurrencyDetails(currency);
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        SharedPreferences preferences = context.getSharedPreferences("SharedPreferencesUserLogin", Context.MODE_PRIVATE);
                        String id = preferences.getString("userId", "0");
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("ID" + currenId, String.valueOf(currenId));
                        editor.apply();

                        User user = realm.where(User.class).equalTo("id", Integer.parseInt(id)).findFirst();
                        CryptoCurrency cryptoCurrency = realm.where(CryptoCurrency.class).equalTo("id", Integer.parseInt(holder.ID.getText().toString())).findFirst();
                        Log.i(TAG, "Se ha guardado en el usuario: " + user.getId());

                        user.getCryptoCurrencies().add(cryptoCurrency);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Ha funcionado xd");
                        Log.i(TAG, "La i tambien funsiona");
                        holder.button.setEnabled(false);
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        error.printStackTrace();
                    }
                });
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

    private void goToCryptoCurrencyDetails(CryptoCurrency currency){
        Intent intent = new Intent(context, CryptoCurrencyController.class);
        intent.putExtra("currencyId", currency.getId());
        context.startActivity(intent);
    }
}
