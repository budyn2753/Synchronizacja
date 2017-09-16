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
    private DBmySQL mySQL = new DBmySQL();
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

        // Explicit Intent by specifying its class name
        Intent i = new Intent(Logon.this, ActivityZamowienia.class);
        i.putExtra("tekst",text);
        text = "";
        // Starts TargetActivity
        startActivity(i);

    }
    public void addingProductsFromRequest(String text){
        String[] temp =text.split(",");
        db.addProdukt(Integer.parseInt(temp[0]),temp[1],Double.parseDouble(temp[2]),Integer.parseInt(temp[3]));

    }

    public void onClick(View v){
        db.clearProdukty();
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
        addingProductsFromRequest(text);


    }


    @Override
    public void processFinish(String output) {
        text =output;
    }
}
