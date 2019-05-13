package com.example.bitviewproject.Model;

public class CryptoCurrencies {

    String currency;
    int value;

    public CryptoCurrencies(String currency, int value) {
        this.currency = currency;
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
