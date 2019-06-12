package com.example.bitviewproject.Adapters.MainAdapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bitviewproject.Controller.CryptoCurrencyController;
import com.example.bitviewproject.Model.CryptoCurrency;
import com.example.bitviewproject.R;

import io.realm.Realm;
import io.realm.RealmResults;

public class RecyclerViewPrincipalAdapter extends RecyclerView.Adapter<HolderViewPrincipalAdapter> {

    Realm realm;

    private Context context;

    public RecyclerViewPrincipalAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HolderViewPrincipalAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.principal_cardviews, viewGroup, false);

        return new HolderViewPrincipalAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderViewPrincipalAdapter holder, int i) {
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<CryptoCurrency> currencies = realm.where(CryptoCurrency.class).findAll();
            final CryptoCurrency currency = currencies.get(i);

            holder.txtNameCurrency.setText(currency.getName());
            holder.txtValue.setText(currency.getValue() + "€");

            //holder.iconCurrency.setImageResource(R.drawable + "");
            Resources resources = context.getResources();
            final int resourceId = resources.getIdentifier(currency.getImgName() != null ? currency.getImgName() : "flip",
                    "drawable", context.getPackageName());
            holder.iconCurrency.setImageResource(resourceId);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CryptoCurrencyController.class);
                    intent.putExtra("currencyId", currency.getId());
                    context.startActivity(intent);
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

}
