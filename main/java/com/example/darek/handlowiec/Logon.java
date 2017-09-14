package com.example.darek.handlowiec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;


public class Logon extends AppCompatActivity  {

    private EditText usernameField, passwordField;
    private TextView status;
    public int idHandlowca;
    private DBmySQL mySQL = new DBmySQL();
    DB db;
    String linew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);
        db = new DB(this);


        usernameField = (EditText)findViewById(R.id.loginField);
        passwordField = (EditText)findViewById(R.id.passwordField);
        status =(TextView)findViewById(R.id.textViewPowitanie);

    }

    public void login (View view){
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        SigninActivity xt = new SigninActivity(this,status,idHandlowca);
        xt.execute(username, password);

        linew = xt.line;

        //mySQL = new DBmySQL();
        //mySQL.syncProdukty();
        //String line = mySQL.syncProdukty();

        Toast.makeText(this,linew, Toast.LENGTH_LONG).show();
        //String[] temps = line.split(",");
        //db.addProdukt(Integer.parseInt(temps[0]), temps[1], Double.parseDouble(temps[2]), Integer.parseInt(temps[3]));


       Intent StartNewActivity = new Intent(this, ActivityZamowienia.class);
       startActivity(StartNewActivity);

    }

    public void onClick(View v){
        String line = mySQL.getProduktyFromMySQL();
        Toast.makeText(this,line, Toast.LENGTH_LONG).show();

    }
}
