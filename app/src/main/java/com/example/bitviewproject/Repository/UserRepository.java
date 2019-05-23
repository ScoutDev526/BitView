package com.example.bitviewproject.Repository;

import com.example.bitviewproject.Model.User;

import io.realm.Realm;
import io.realm.RealmResults;

public class UserRepository {

    public User findById(Realm realm, int id){
        return realm.where(User.class).equalTo("id", id).findFirst();
    }

    public RealmResults<User> findAllUsers(Realm realm){
        return realm.where(User.class).findAll();
    }

    public User findFirst(Realm realm){
        return realm.where(User.class).findFirst();
    }

    public Boolean isAuthenticate(Realm realm, String username, String password){
        return realm.where(User.class)
                .equalTo("username", username)
                .and()
                .equalTo("password", password)
                .findFirst().isLoaded();
    }

}
