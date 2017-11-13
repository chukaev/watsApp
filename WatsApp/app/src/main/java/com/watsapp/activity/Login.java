package com.watsapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.watsapp.App;
import com.watsapp.R;
import com.watsapp.UserDetails;
import com.watsapp.prelude.BSet;

public class Login extends AppCompatActivity {
    EditText username;
    Button loginButton;
    Integer user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (username.getText().toString().equals("")) {
                    username.setError("can't be blank");
                } else {

                    user = Integer.parseInt(username.getText().toString());

                    if (!App.machine.get_user().contains(user)) {
                        App.machine.set_user(App.machine.get_user().union(new BSet<>(user)));
                    }
                    UserDetails.user = user;
                    startActivity(new Intent(Login.this, Sessions.class));
                    Login.this.finish();
                }
            }
        });
    }
}
