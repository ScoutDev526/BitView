package com.example.bitviewproject.Service.impl;

import com.example.bitviewproject.Model.User;
import com.example.bitviewproject.Service.UserService;

import io.realm.Realm;
import io.realm.RealmResults;

public class UserServiceImpl implements UserService {

    Realm realm;


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
    }

    public RealmResults<User> getAllUser() {
        try {
            realm = Realm.getDefaultInstance();
            return realm.where(User.class).findAll();
        }finally {
            realm.close();
        }
    }

    private Boolean isUserEmpty(){
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<User> results = realm.where(User.class).findAll();
            if (results.isEmpty()){
                return true;
            }
            return false;
        }finally {
            realm.close();
        }

    }
}
