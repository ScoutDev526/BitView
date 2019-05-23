package com.example.bitviewproject.Repository;

import com.example.bitviewproject.Model.CryptoCurrency;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class CryptoCurrencyRepository {

    Realm realm = Realm.getDefaultInstance();

    RealmResults<CryptoCurrency> findAll(){
        return realm.where(CryptoCurrency.class).findAll();
    }

    CryptoCurrency findById(int id){
        return realm.where(CryptoCurrency.class).equalTo("id", id).findFirst();
    }

    RealmResults<CryptoCurrency> findAllShortedByValue(){
        return realm.where(CryptoCurrency.class).sort("value", Sort.DESCENDING).findAll();
    }
}
