package com.example.bitviewproject.Service;

import com.example.bitviewproject.Model.User;

import io.realm.RealmResults;

public interface UserService {

    public void addUserFirstTime();

    public RealmResults<User> getAllUser();

}
