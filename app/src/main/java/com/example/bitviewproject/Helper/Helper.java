package com.example.bitviewproject.Helper;

import com.example.bitviewproject.Model.CryptoCurrency;
import com.example.bitviewproject.Model.User;
import com.example.bitviewproject.Service.impl.CryptoCurrencyService;
import com.example.bitviewproject.Service.impl.UserService;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class Helper {

    CryptoCurrencyService cryptoCurrencyService;
    UserService userService;

    Realm realm;
    RealmResults<User> users;
    RealmResults<CryptoCurrency> cryptoCurrencies;

    public Helper(Realm realm) {
        this.realm = realm;
    }

    public void getUserFromDB(){

        users = userService.getAllUser();

    }
    public void getCryptoCurreciesFromDB(){

        cryptoCurrencies = realm.where(CryptoCurrency.class).findAll();

    }

    public ArrayList<User> refreshUsers(){

        ArrayList<User> userList = new ArrayList<>();
        for (User u : users){
            userList.add(u);
        }

        return userList;

    }

    public ArrayList<CryptoCurrency> refreshCryptoCurrencies(){

        ArrayList<CryptoCurrency> currenciesList = new ArrayList<>();
        for(CryptoCurrency cc : cryptoCurrencies){
            currenciesList.add(cc);
        }

        return currenciesList;

    }
}
