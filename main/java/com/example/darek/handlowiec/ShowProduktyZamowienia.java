package com.example.darek.handlowiec;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowProduktyZamowienia extends AppCompatActivity {

    ArrayList<produkty> Products = new ArrayList<produkty>();
    ArrayList<String> displayedk = new ArrayList<String>();
    DB db;
    int id_Zamowienia;
    int idZamTemp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_produkty_zamowienia);
        db = new DB(this);
        id_Zamowienia = getIntent().getIntExtra("IDZamowienia",0);
       idZamTemp = getIntent().getIntExtra("IDZamTemp", 0);

        FillZamowienia();






        ListView chl = (ListView)findViewById(R.id.ZPlist);

        for(produkty x: Products){
            displayedk.add(x.getNazwa()+"\t\tIlosć: "+x.getIlosc());
        }


        ArrayAdapter<String> mHistory = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,displayedk);
        chl.setAdapter(mHistory);

    }

    public void DodajDoZamowienia(View v){
        Intent i = new Intent(ShowProduktyZamowienia.this, ActivityProdukty.class);
        i.putExtra("IDZamowienia",idZamTemp);
        startActivity(i);
    }

    private void FillZamowienia(){


        Products = db.getProductByID(Integer.toString(id_Zamowienia));


    }
}
