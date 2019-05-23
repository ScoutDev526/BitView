package com.example.bitviewproject.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bitviewproject.Model.User;
import com.example.bitviewproject.R;

import io.realm.Realm;

public class LoginController extends AppCompatActivity {

    EditText username, password;
    Button loginButon;

    SharedPreferences sharedPreferences;
    Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        realm = Realm.getDefaultInstance();

        username = findViewById(R.id.loginUsername);
        password = findViewById(R.id.loginPassword);
        final Context context = getApplicationContext();
        sharedPreferences = getSharedPreferences("SharedPreferencesUserLogin", Context.MODE_PRIVATE);

        loginButon = findViewById(R.id.btnLogin);
        loginButon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                User userLog = realm.where(User.class)
                        .equalTo("username", username.getText().toString()).findFirst();

                if (userLog.getPassword().equals(password.getText().toString())) {

                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userId", Integer.toString(userLog.getId()));
                    editor.apply();

                    finish();

                } else {

                    Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show();

                    username.setText("");
                    username.setHint("Pruebe otro usuario");
                    password.setText("");
                    password.setHint("Pruebe otra password");

                }
            }
        });
    }
}
