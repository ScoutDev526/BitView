package com.example.bitviewproject.Model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class User extends RealmObject {

    @Index
    @PrimaryKey
    int id;

    String name;
    String lastName;

    @Required
    String username;

    @Required
    String password;

    @Required
    String email;

    RealmList<CryptoCurrency> cryptoCurrencies;

    public RealmList<CryptoCurrency> getCryptoCurrencies() {
        return cryptoCurrencies;
    }

    public void setCryptoCurrencies(RealmList<CryptoCurrency> cryptoCurrencies) {
        this.cryptoCurrencies = cryptoCurrencies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
