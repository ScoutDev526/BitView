package com.example.bitviewproject.Service.impl;

import android.util.Log;

import com.example.bitviewproject.Controller.MainController;
import com.example.bitviewproject.Model.User;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class UserService {

    Realm realm;
    MainController mainController;

    public UserService(Realm realm){
        this.realm = realm;
    }


    public void addUserFirstTime() {

        if(isUserEmpty()) {
                try {
                    realm = Realm.getDefaultInstance();

                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm bgRealm) {
                            Number maxId = bgRealm.where(User.class).max("id");
                            int newKey = (maxId == null) ? 1 : maxId.intValue() + 1;

                            User user = bgRealm.createObject(User.class, newKey);
                            user.setUsername("admin");
                            user.setPassword("admin");
                            user.setEmail("ferrerasdiazcesar@gmail.com");
                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            Log.v("BITVIEW", "AÑADE LOS USUARIOS");
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            Log.v("BITVIEW", "ERROR AL CREAR USUARIOS");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    realm.close();
                }
        } else {
            Log.v("BITVIEW", "YA EXISTIAN DATOS USERS");
        }

    }

    public RealmResults<User> getAllUser() {
        return realm.where(User.class).findAll();
    }

    private Boolean isUserEmpty(){
        RealmResults<User> results = realm.where(User.class).findAll();
        if (results.isEmpty()){
            return true;
        }
        return false;
    }
}
