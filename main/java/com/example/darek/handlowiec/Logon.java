package com.example.darek.handlowiec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;


public class Logon extends AppCompatActivity {

    private EditText usernameField, passwordField;
    private TextView status;
    public int idHandlowca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);

        usernameField = (EditText)findViewById(R.id.loginField);
        passwordField = (EditText)findViewById(R.id.passwordField);
        status =(TextView)findViewById(R.id.textViewPowitanie);

    }

    public void login (View view){
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        new SigninActivity(this,status,idHandlowca).execute(username, password);


        Intent StartNewActivity = new Intent(this, ActivityZamowienia.class);
        startActivity(StartNewActivity);
    }
}
