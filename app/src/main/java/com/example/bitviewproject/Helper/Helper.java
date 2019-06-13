package com.example.bitviewproject.Helper;

import com.example.bitviewproject.Model.CryptoCurrency;
import com.example.bitviewproject.Model.User;
import com.example.bitviewproject.Service.impl.CryptoCurrencyServiceImpl;
import com.example.bitviewproject.Service.impl.UserServiceImpl;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class Helper {

    CryptoCurrencyServiceImpl cryptoCurrencyServiceImpl;
    UserServiceImpl userServiceImpl;

    Realm realm;
    RealmResults<User> users;
    RealmResults<CryptoCurrency> cryptoCurrencies;

    /*public Helper(Realm realm) {
        this.realm = realm;
    }*/

    public void getCryptoCurreciesFromDB(){
        try {
            realm = Realm.getDefaultInstance();
            cryptoCurrencies = realm.where(CryptoCurrency.class).findAll();
        } finally {
            realm.close();
        }
    }

    public void getCryptoCurrenciesSortByValueFromDBLimit(){
        try {
            realm = Realm.getDefaultInstance();
            cryptoCurrencies = realm.where(CryptoCurrency.class).limit(5).sort("value", Sort.DESCENDING).findAll();
        } finally {
            realm.close();
        }
    }

    public void getCryptoCurrenciesSortByValueFromDB(){
        try {
            realm = Realm.getDefaultInstance();
            cryptoCurrencies = realm.where(CryptoCurrency.class).sort("value", Sort.DESCENDING).findAll();
        } finally {
            realm.close();
        }
    }

    public ArrayList<User> refreshUsers(){
        return new ArrayList<>(users);
    }

    public ArrayList<CryptoCurrency> refreshCryptoCurrencies(){
        return new ArrayList<>(cryptoCurrencies);

    }
}
