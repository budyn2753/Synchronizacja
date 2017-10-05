package com.example.darek.handlowiec;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowNotSynchronizedOrders extends AppCompatActivity {

    ArrayList<Zamowienia> Orders = new ArrayList<Zamowienia>();
    ArrayList<String> displayedk = new ArrayList<String>();
    DB db;
    String logedUser;
    int idlocal;
    int idKlienta;
    getIdZamowienia getIDz = new getIdZamowienia();
    String id_Zamowienia ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_not_synchronized_orders);
        db = new DB(this);
        logedUser = getIntent().getStringExtra("logedUser");
        FillZamowienia();

        if(Orders.isEmpty()){
            Intent i = new Intent(ShowNotSynchronizedOrders.this, Logon.class);
            Toast.makeText(this, "Brak elementow do synchronizcji",Toast.LENGTH_SHORT).show();
            startActivity(i);
        }




        ListView chl = (ListView)findViewById(R.id.NSslist);

        for(Zamowienia x: Orders){
            displayedk.add("ID \t: " + x.getID_zBazy()+ " Klient: " +x.getCustomerName() );
        }


        ArrayAdapter<String> mHistory2 = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,displayedk);
        chl.setAdapter(mHistory2);
        chl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Zamowienia tmp = Orders.get(Orders.indexOf(new Zamowienia((int)id)));
                 idlocal= tmp.getIDLocal();
                idKlienta = tmp.getCustomerID();
                Intent i = new Intent(ShowNotSynchronizedOrders.this, Synchronizuj.class);
                i.putExtra("IDZamowienia",idlocal);
                i.putExtra("IDKlienta",idKlienta);
                i.putExtra("logedUser", logedUser);


                startActivity(i);
            }

        });

    }

    private void FillZamowienia(){
        Orders = db.getNotSynchronizedOrders();
    }
}
