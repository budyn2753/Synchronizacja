package com.example.darek.handlowiec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class Temp extends AppCompatActivity implements AsyncResponse {
    String logedUser;
    DB db;
    getIdZamowienia getIDz = new getIdZamowienia();
    String id_Zamowienia ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        logedUser = getIntent().getStringExtra("logedUser");
        db = new DB(this);
    }

    public void onClick2(View v){
        String id_Zamowienia="";
        String[] x = db.getLastUnsyncID();

        Toast.makeText(this,x[1]+x[0]+logedUser,Toast.LENGTH_SHORT).show();

        new AddZamowienie(this).execute(logedUser, x[1],x[0]);
        getIDz.delegate = this;
        getIDz.execute(logedUser, x[0]);
        try {
            id_Zamowienia = getIDz.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        db.updateZamowienia(x[0], id_Zamowienia);

    }

    @Override
    public void processFinish(String output) {
        id_Zamowienia = output;
    }
}
