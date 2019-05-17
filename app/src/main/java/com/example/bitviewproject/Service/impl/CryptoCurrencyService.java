package com.example.bitviewproject.Service.impl;

import android.util.Log;

import com.example.bitviewproject.Model.CryptoCurrency;

import io.realm.Realm;
import io.realm.RealmResults;

public class CryptoCurrencyService {

    Realm realm;

    public CryptoCurrencyService(Realm realm) {
        this.realm = realm;
    }

    public void addCryptoCurrencyFirstTime() {

        if (isCurrenciesEmpty()) {
            for(int i = 0; i<10; i++) {
                try {
                    realm = Realm.getDefaultInstance();

                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm bgRealm) {
                            Number maxId = bgRealm.where(CryptoCurrency.class).max("id");
                            int newKey = (maxId == null) ? 1 : maxId.intValue() + 1;

                            CryptoCurrency currency = bgRealm.createObject(CryptoCurrency.class, newKey);
                            currency.setName("MONEDA" + newKey);
                            currency.setValue(newKey);

                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            Log.v("BITVIEW", "AÑADE LA MONEDA");

                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            Log.v("BITVIEW", "ERROR CREANDO MONEDAS");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    realm.close();
                }
            }
        } else {
            Log.v("BITVIEW", "YA EXISTIAN DATOS CURRENCIES");
        }

    }

    public void addCryptoCurrency(int id, String name, int value) {
        return ;
    }

    public RealmResults<CryptoCurrency> getAllCurrencies() {
        return realm.where(CryptoCurrency.class).findAll();
    }

    private Boolean isCurrenciesEmpty(){
        RealmResults<CryptoCurrency> results = realm.where(CryptoCurrency.class).findAll();
        if (results.isEmpty()){
            return true;
        }
        return false;
    }
}
