package com.example.bitviewproject.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bitviewproject.Adapters.UserDetailsAdapters.RecyclerViewUserDetailsAdapter;
import com.example.bitviewproject.Model.CryptoCurrency;
import com.example.bitviewproject.Model.User;
import com.example.bitviewproject.R;

import java.util.ArrayList;

import io.realm.Realm;

public class DetailsUsersController extends AppCompatActivity {

    RecyclerView recyclerView;
    Realm realm;
    private Short totalCount = 0;

    TextView total, username;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsusers);


        recyclerView = findViewById(R.id.recyclerViewUserDetails);
        total = findViewById(R.id.txtTotal);
        imageView = findViewById(R.id.userImage);
        username = findViewById(R.id.txtUsername);

        SharedPreferences preferences = getSharedPreferences("SharedPreferencesUserLogin", Context.MODE_PRIVATE);
        String id = preferences.getString("userId", "0");
        ArrayList<CryptoCurrency> arrayList = new ArrayList<>();

        try {
            realm = Realm.getDefaultInstance();

            Log.e("DETAILS", "------>" + id);
            User user = realm.where(User.class).equalTo("id", Integer.parseInt(id)).findFirst();

            if (user == null){
                Log.e("DETAILS", "EL USUARIO ES NULO GUEY");
                total.setText("");
                //imageView.setImageResource(R.drawable.logo);
                imageView.setBackground(getDrawable(R.drawable.logo));
                username.setText("");
                arrayList.add(new CryptoCurrency());
            } else {
                for (CryptoCurrency c : user.getCryptoCurrencies()) {
                    Log.i("DETAILS", "------" + c.getShortName() + "-------");
                    totalCount ++;
                }
                total.setText(Short.toString(totalCount));
                imageView.setImageResource(R.drawable.flip);
                if (user.getName() != null){
                    username.setText(user.getName() + "\n" + user.getUsername());
                } else {
                    username.setText(user.getUsername().toUpperCase());
                }
                for (CryptoCurrency c : realm.where(User.class).equalTo("id", Integer.parseInt(id)).findFirst().getCryptoCurrencies()){
                    arrayList.add(c);
                    Log.e("ADDTOARRAYLIST", "-------->" + c.getShortName());
                }
            }

            RecyclerViewUserDetailsAdapter controller = new
                    RecyclerViewUserDetailsAdapter(realm, this, arrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(controller);

        } finally {
            realm.close();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
