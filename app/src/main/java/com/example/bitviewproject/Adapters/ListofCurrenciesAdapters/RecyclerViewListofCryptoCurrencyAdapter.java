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

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class RecyclerViewListofCryptoCurrencyAdapter extends RecyclerView.Adapter<HolderViewListofCryptoCurrency> {

    static final String TAG = "ADAPTERLISTA";

    Realm realm;
    final Context context;

    public RecyclerViewListofCryptoCurrencyAdapter(Context context) {
        this.context = context;
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
        //final CryptoCurrency currency = cryptoCurrencies.get(i);
        //final int currenId = currency.getId();
        User user;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<CryptoCurrency> cryptoCurrencies = realm.where(CryptoCurrency.class).sort("value", Sort.DESCENDING).findAll();
            final CryptoCurrency currency = cryptoCurrencies.get(i);
            final int currenId = currency.getId();

            SharedPreferences preferences = context.getSharedPreferences("SharedPreferencesUserLogin", Context.MODE_PRIVATE);
            String id = preferences.getString("userId", "0");

            user = realm.where(User.class).equalTo("id", Integer.parseInt(id)).findFirst();

            Log.e(TAG, "-----------------------------------");
            Log.e(TAG, Integer.toString(currenId));

            holder.txtNameCurrency.setText(currency.getName());
            holder.ID.setText(Integer.toString(currency.getId()));
            Log.e(TAG, "----> currencyID = " + currency.getId());
            holder.iconCurrency.setImageResource(R.drawable.flip);

            boolean used = false;
            try {
                if (user != null) {
                    for (CryptoCurrency c : user.getCryptoCurrencies()) {
                        Log.i(TAG, "-----> " + c.getId());
                        if (c.getId() == currency.getId()) {
                            used = true;
                            Log.e(TAG, "-------> used true");
                            break;
                        }
                    }
                } else {
                    holder.button.setVisibility(View.INVISIBLE);
                }

            } catch (Exception e) {
                Log.e("ERROR", String.valueOf(e));
            }
            if (used) {
                holder.button.setEnabled(false);
                holder.button.setVisibility(View.INVISIBLE);
            }

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
                            //holder.button.setVisibility(View.INVISIBLE);
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            error.printStackTrace();
                        }
                    });
                }
            });
        } finally {
            realm.close();
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void goToCryptoCurrencyDetails(CryptoCurrency currency) {
        Intent intent = new Intent(context, CryptoCurrencyController.class);
        intent.putExtra("currencyId", currency.getId());
        context.startActivity(intent);
    }
}
