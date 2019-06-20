package com.example.bitviewproject.Service.impl;

import android.content.Context;

import com.example.bitviewproject.Helper.HelperCoinAPI;
import com.example.bitviewproject.Model.CryptoCurrency;
import com.example.bitviewproject.Service.CryptoCurrencyService;

import io.realm.Realm;
import io.realm.RealmResults;

public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {

    Realm realm;
    Context context;

    public CryptoCurrencyServiceImpl(Context context) {
        this.context = context;
    }

    public void addCryptoCurrencyFirstTime() {
        if (isCurrenciesEmpty()) {
            for(int i = 0; i<17; i++) {
                try {
                    realm = Realm.getDefaultInstance();

                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm bgRealm) {
                            Number maxId = bgRealm.where(CryptoCurrency.class).max("id");
                            int newKey = (maxId == null) ? 1 : maxId.intValue() + 1;
                            CryptoCurrency currency = bgRealm.createObject(CryptoCurrency.class, newKey);
                            currency.setShortName(newKey + "MONEDA");
                            currency.setValue(newKey);
                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    realm.close();
                }
            }
            HelperCoinAPI coinAPI = new HelperCoinAPI(context.getApplicationContext(), 1);
            coinAPI.load();
        }
    }

    public RealmResults<CryptoCurrency> getAllCurrencies() {
        try {
            realm = Realm.getDefaultInstance();
            return realm.where(CryptoCurrency.class).findAll();
        } finally {
            realm.close();
        }
    }

    public Boolean isCurrenciesEmpty(){
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<CryptoCurrency> results = realm.where(CryptoCurrency.class).findAll();
            if (results.isEmpty()){
                return true;
            }
            return false;
        } finally {
            realm.close();
        }
    }
}
