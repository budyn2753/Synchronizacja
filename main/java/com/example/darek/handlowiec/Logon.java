package com.example.darek.handlowiec;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class Logon extends AppCompatActivity implements AsyncResponse {

    private EditText usernameField, passwordField;
    private TextView status;
    public int idHandlowca;
    String logedUser;

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

        File path = this.getDatabasePath("android");

        path.toString();
        File f=new File(path.toString());
        FileInputStream fis=null;
        FileOutputStream fos=null;

        try
        {
            fis=new FileInputStream(f);
            fos=new FileOutputStream("/mnt/sdcard/db_dump.db");
            while(true)
            {
                int i=fis.read();
                if(i!=-1)
                {fos.write(i);}
                else
                {break;}
            }
            fos.flush();
            Toast.makeText(this, "DB dump OK", Toast.LENGTH_LONG).show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "DB dump ERROR" + e.getMessage(), Toast.LENGTH_LONG).show();
        }


        //usernameField = (EditText)findViewById(R.id.loginField);
        //passwordField = (EditText)findViewById(R.id.passwordField);
        status =(TextView)findViewById(R.id.textViewPowitanie);
        gk.execute();

    }




    public void login (View view) {
        //String username = usernameField.getText().toString();
        //String password = passwordField.getText().toString();
        //new SigninActivity(this, status, idHandlowca).execute(username, password);
        // Create Object of Dialog class
        final Dialog login = new Dialog(this);
        // Set GUI of login screen
        login.setContentView(R.layout.logon_dialog);
        login.setTitle("Login");

        // Init button of login GUI
        Button btnLogin = (Button) login.findViewById(R.id.btnLogin);
        Button btnCancel = (Button) login.findViewById(R.id.btnCancel);
        final EditText txtUsername = (EditText)login.findViewById(R.id.txtUsername);
        final EditText txtPassword = (EditText)login.findViewById(R.id.txtPassword);

        // Attached listener for login GUI button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUsername.getText().toString().trim().length() > 0 && txtPassword.getText().toString().trim().length() > 0)
                {

                    new SigninActivity(Logon.this, status).execute(txtUsername.getText().toString(), txtUsername.getText().toString());
                    //Redirect to dashboard / home screen.
                    login.dismiss();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please enter Username and Password", Toast.LENGTH_LONG).show();

                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.dismiss();
            }
        });

        // Make dialog box visible.
        login.show();


    }


    public void addingClientFromRequest(String text) {

        db.clearKlienci();

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
        String txt = status.getText().toString();

        String [] tmp = txt.split(" ");

        logedUser = tmp[0];
        if(logedUser.isEmpty()|| logedUser == "0"){
        Intent i = new Intent(Logon.this, KlienciActivity.class);
        i.putExtra("logedUser", "0");
        startActivity(i);
        }else{
            addingClientFromRequest(text);
            Intent i = new Intent(Logon.this, KlienciActivity.class);
            i.putExtra("logedUser", logedUser);
            startActivity(i);
        }

    }
    public void pokazNS(View v){
        String txt = status.getText().toString();

        String [] tmp = txt.split(" ");

        logedUser = tmp[0];
        if(logedUser.isEmpty()){
            Intent i = new Intent(Logon.this, ShowNotSynchronizedOrders.class);
            i.putExtra("logedUser", "0");
            startActivity(i);
        }else{
            addingClientFromRequest(text);
            Intent i = new Intent(Logon.this, ShowNotSynchronizedOrders.class);
            i.putExtra("logedUser", logedUser);
            startActivity(i);
        }
    }
    public void dodajDoZamowienia(View v){

            Intent i = new Intent(Logon.this, ShowMyOrders.class);

            startActivity(i);


    }

    public void PokazSpotkania(View v){
      Intent i =  new Intent (Logon.this, MeetingsActivity.class);
      startActivity(i);


    }

    @Override
    public void processFinish(String output) {
        text =output;
    }
}
