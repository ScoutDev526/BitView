package com.example.bitviewproject.Service;

import com.example.bitviewproject.Model.CryptoCurrency;

import io.realm.RealmResults;

public interface CryptoCurrencyService {

    public void addCryptoCurrencyFirstTime();

    public RealmResults<CryptoCurrency> getAllCurrencies();

}
