package com.example.darek.handlowiec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class Logon extends AppCompatActivity implements AsyncResponse {

    private EditText usernameField, passwordField;
    private TextView status;
    public int idHandlowca;


    public String text = "";
   // private DBmySQL mySQL = new DBmySQL();

    DB db;
    GetKlienci gk = new GetKlienci();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gk.delegate = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);
        db = new DB(this);


        usernameField = (EditText)findViewById(R.id.loginField);
        passwordField = (EditText)findViewById(R.id.passwordField);
        status =(TextView)findViewById(R.id.textViewPowitanie);
        gk.execute();

    }


    public void login (View view) {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        new SigninActivity(this, status, idHandlowca).execute(username, password);
    }


    public void addingClientFromRequest(String text) {

        if (text == "")
            Toast.makeText(this, "Brak Połączenia", Toast.LENGTH_SHORT).show();
        else {
            int IloscKolumnWBazie = 3;
            String[] temp = text.split(",");
            text = "";
            int iter = temp.length / IloscKolumnWBazie;
            for (int i = 0; i < iter; i++) {
                if (i == 0)
                    db.addKlient(temp[0], Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
                else

                    db.addKlient(temp[i * IloscKolumnWBazie], Integer.parseInt(temp[(i * IloscKolumnWBazie) + 1]), Integer.parseInt(temp[(i * IloscKolumnWBazie) + 2]));
            }

        }
    }


    public void onClick(View v){

        addingClientFromRequest(text);
        Intent i = new Intent(Logon.this, KlienciActivity.class);
        startActivity(i);


    }


    @Override
    public void processFinish(String output) {
        text =output;
    }
}
