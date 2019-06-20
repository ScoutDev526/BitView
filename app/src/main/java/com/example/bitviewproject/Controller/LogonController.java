package com.example.bitviewproject.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bitviewproject.Model.User;
import com.example.bitviewproject.R;

import io.realm.Realm;

public class LogonController extends AppCompatActivity {

    EditText username, password, name, lastName, email;
    Button logon;
    Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logon_activity);

        username = findViewById(R.id.logonUsername);
        password = findViewById(R.id.logonPassword);
        name = findViewById(R.id.logonName);
        lastName = findViewById(R.id.logonApellido);
        email = findViewById(R.id.logonEmail);

        logon = findViewById(R.id.btnCreateUser);
        logon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    realm = Realm.getDefaultInstance();

                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm bgRealm) {
                            Number maxId = bgRealm.where(User.class).max("id");
                            int newKey = (maxId == null) ? 1 : maxId.intValue() + 1;

                            User newUser = bgRealm.createObject(User.class, newKey);
                            newUser.setUsername(username.getText().toString());
                            newUser.setPassword(password.getText().toString());
                            newUser.setName(name.getText().toString());
                            newUser.setLastName(lastName.getText().toString());
                            newUser.setEmail(email.getText().toString());

                            final SharedPreferences sharedPreferences = getSharedPreferences("SharedPreferencesUserLogin", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userId", Integer.toString(newUser.getId()));
                            editor.apply();

                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            finish();
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            Toast.makeText(getApplicationContext(), "Error al verificar. Pruebe otra vez.", Toast.LENGTH_SHORT).show();
                            username.setText("");
                            password.setText("");
                            name.setText("");
                            lastName.setText("");
                            email.setText("");

                            error.printStackTrace();
                        }
                    });
                } finally {
                    realm.close();
                }
            }
        });

    }
}
