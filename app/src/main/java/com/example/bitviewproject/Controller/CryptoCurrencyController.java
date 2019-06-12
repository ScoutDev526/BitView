package com.example.bitviewproject.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.bitviewproject.Model.CryptoCurrency;
import com.example.bitviewproject.Model.User;
import com.example.bitviewproject.R;
import com.example.bitviewproject.Service.impl.CryptoCurrencyServiceImpl;
import com.example.bitviewproject.Service.impl.UserServiceImpl;

import io.realm.Realm;

public class CryptoCurrencyController extends AppCompatActivity {

    TextView nameCurrencyDetails;
    TextView txtValueDetails;
    TextView valueCurrencyDetails;
    TextView txtInfoCurrencyDetails;
    TextView txtOtherDataNameDetails;
    TextView otherDataNameDetails;
    TextView TxtOtherDataCreatorDetails;
    TextView otherDataCreatorDetails;
    TextView TxtOtherDataDateDetails;
    TextView otherDataDateDetails;

    UserServiceImpl userServiceImpl;
    CryptoCurrencyServiceImpl cryptoCurrencyServiceImpl;
    User user;
    CryptoCurrency cryptoCurrency;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cryptocurrency);

        nameCurrencyDetails = findViewById(R.id.nameCurrencyDetails);
        valueCurrencyDetails = findViewById(R.id.valueCurrencyDetails);
        otherDataNameDetails = findViewById(R.id.otherDataNameDetails);
        otherDataCreatorDetails = findViewById(R.id.otherDataCreatorDetails);
        otherDataDateDetails = findViewById(R.id.otherDataDateDetails);

        try {
            realm = Realm.getDefaultInstance();

            userServiceImpl = new UserServiceImpl();
            cryptoCurrencyServiceImpl = new CryptoCurrencyServiceImpl(getApplicationContext());

            Intent intent = getIntent();
            int currencyId = intent.getIntExtra("currencyId", 0);

            cryptoCurrency = realm.where(CryptoCurrency.class)
                    .equalTo("id", currencyId).findFirst();

            nameCurrencyDetails.setText(cryptoCurrency.getName());
            valueCurrencyDetails.setText("Valor: " + cryptoCurrency.getValue());
            otherDataNameDetails.setText("Ya pondra algo aqui");
            otherDataDateDetails.setText("Ya pondra algo aqui");
            otherDataCreatorDetails.setText("Ya pondra algo aqui");

        } finally {
            realm.close();
        }

    }


}
