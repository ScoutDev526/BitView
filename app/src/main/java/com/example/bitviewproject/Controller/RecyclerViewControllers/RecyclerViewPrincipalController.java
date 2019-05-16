package com.example.bitviewproject.Controller.RecyclerViewControllers;

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

public class RecyclerViewPrincipalController extends RecyclerView.Adapter<HolderViewPrincipalController> {

    Realm realm;

    Context context;
    ArrayList<CryptoCurrency> cryptoCurrencies;

    public RecyclerViewPrincipalController(Context context, ArrayList<CryptoCurrency> cryptoCurrencies, Realm realm) {
        this.context = context;
        this.cryptoCurrencies = cryptoCurrencies;
        this.realm = realm;
    }

    @NonNull
    @Override
    public HolderViewPrincipalController onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.principal_cardviews, viewGroup, false);

        return new HolderViewPrincipalController(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderViewPrincipalController holder, int i) {
        CryptoCurrency currency = cryptoCurrencies.get(i);

        String cad1 = " ", cad2= " ";

        /*try {
            realm = Realm.getDefaultInstance();
            cad1 = realm.where(CryptoCurrency.class).equalTo("id", i).findFirst().getName();
            cad2 = Integer.toString(realm.where(CryptoCurrency.class).equalTo("id", i).findFirst().getValue());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            realm.close();
        }*/

        //holder.txtNameCurrency.setText("Provemos con esto");
        //holder.txtValue.setText("Y esto otro");
        holder.txtNameCurrency.setText(currency.getName());
        holder.txtValue.setText(Integer.toString(currency.getValue()));

    }

    @Override
    public int getItemCount() {
        return cryptoCurrencies.size();
    }
}
