package com.example.bitviewproject.Controller;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bitviewproject.Model.CryptoCurrency;
import com.example.bitviewproject.Model.User;
import com.example.bitviewproject.R;
import com.example.bitviewproject.Service.impl.CryptoCurrencyServiceImpl;
import com.example.bitviewproject.Service.impl.UserServiceImpl;

import java.text.DecimalFormat;

import io.realm.Realm;

public class CryptoCurrencyController extends AppCompatActivity {

    TextView nameCurrencyDetails;
    TextView valueCurrencyDetails;
    TextView otherDataShortNameDetails;
    TextView otherDataNameDetails;
    TextView otherDataValueDetails;
    TextView otherDataBalanceDetails;
    ImageView logoIcon;

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
        otherDataShortNameDetails = findViewById(R.id.otherDataShortNameDetails);
        otherDataNameDetails = findViewById(R.id.otherDataNameDetails);
        otherDataValueDetails = findViewById(R.id.otherDataValueDetails);
        otherDataBalanceDetails = findViewById(R.id.otherDataBalanceDetails);
        logoIcon = findViewById(R.id.logoCurrencyDetails);

        try {
            realm = Realm.getDefaultInstance();

            userServiceImpl = new UserServiceImpl();
            cryptoCurrencyServiceImpl = new CryptoCurrencyServiceImpl(getApplicationContext());

            Intent intent = getIntent();
            int currencyId = intent.getIntExtra("currencyId", 0);

            cryptoCurrency = realm.where(CryptoCurrency.class)
                    .equalTo("id", currencyId).findFirst();

            double USDValue = cryptoCurrency.getValue() / 0.89;

            nameCurrencyDetails.setText(cryptoCurrency.getFullName());
            valueCurrencyDetails.setText(cryptoCurrency.getValue() + " €");
            otherDataShortNameDetails.setText(cryptoCurrency.getShortName());
            otherDataValueDetails.setText(cryptoCurrency.getValue() + " €");
            otherDataNameDetails.setText(cryptoCurrency.getFullName());
            otherDataBalanceDetails.setText(new DecimalFormat("#.####").format(cryptoCurrency.getUpdate()));

            Resources resources = this.getResources();
            final int resourceId = resources.getIdentifier(cryptoCurrency.getFullName().toLowerCase().replace(" ", ""),
                    "drawable", this.getPackageName());
            logoIcon.setImageResource(resourceId);

        } finally {
            realm.close();
        }

    }


}
