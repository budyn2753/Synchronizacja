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
    ArrayList<Integer> IDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_produkty_zamowienia);
        db = new DB(this);
        id_Zamowienia = getIntent().getIntExtra("IDZamowienia",0);
        IDs = db.getProductsIDFromSzczegoly(Integer.toString(id_Zamowienia));
        FillZamowienia();






        ListView chl = (ListView)findViewById(R.id.ZPlist);

        for(produkty x: Products){
            displayedk.add("ID \t: " + x.getId_baza() + " Nazwa: " +x.getNazwa());
        }


        ArrayAdapter<String> mHistory = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,displayedk);
        chl.setAdapter(mHistory);

    }

    public void Zakoncz(View v){
        Intent i = new Intent(ShowProduktyZamowienia.this, ShowMyOrders.class);
        startActivity(i);
    }

    private void FillZamowienia(){

        for (Integer i: IDs) {
            Products.add(i,db.getProductByID(Integer.toString(i)));
        }

    }
}
