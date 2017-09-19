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
    GetProduktyAcivity sm = new GetProduktyAcivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sm.delegate = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);
        db = new DB(this);


        usernameField = (EditText)findViewById(R.id.loginField);
        passwordField = (EditText)findViewById(R.id.passwordField);
        status =(TextView)findViewById(R.id.textViewPowitanie);
        sm.execute();

    }


    public void login (View view){
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        new SigninActivity(this,status,idHandlowca).execute(username, password);
    }


    public void addingProductsFromRequest(String text) {

        if (text == "")
            Toast.makeText(this, "Brak Połączenia", Toast.LENGTH_SHORT).show();
        else {
            int IloscKolumnWBazie = 4;
            String[] temp = text.split(",");
            text = "";
            int iter = temp.length / IloscKolumnWBazie;
            for (int i = 0; i < iter; i++) {
                if (i == 0)
                    db.addProdukt(Integer.parseInt(temp[0]), temp[1], Double.parseDouble(temp[2]), Integer.parseInt(temp[3]));
                else
                    db.addProdukt(Integer.parseInt(temp[i * IloscKolumnWBazie]), temp[(i * IloscKolumnWBazie) + 1], Double.parseDouble(temp[(i * IloscKolumnWBazie) + 2]), Integer.parseInt(temp[(i * IloscKolumnWBazie + 3)]));
            }

        }
    }

    public void onClick(View v){
        db.clearProdukty();
        //Toast.makeText(this,text,Toast.LENGTH_LONG).show();
        addingProductsFromRequest(text);

        //i.putExtra("tekst",text);
        //text = "";
        // Starts TargetActivity
        Intent i = new Intent(Logon.this, KlienciActivity.class);
        startActivity(i);


    }


    @Override
    public void processFinish(String output) {
        text =output;
    }
}
