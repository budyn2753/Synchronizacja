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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showallzamowienia);
        db = new DB(this);

        FillZamowienia();

        Toast.makeText(getParent(), "Zaznaczyłeś\n" + Orders.get(1).getCustomerID() + " " + Orders.get(1).getUserID(), Toast.LENGTH_LONG).show();

        ListView chl = (ListView)findViewById(R.id.NSlist);

        for(Zamowienia x: Orders){
            displayedk.add("ID \t: " + x.getID_zBazy()+ " Klient: " +x.getCustomerID() );
        }
        ArrayAdapter<String> mHistory = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,displayedk);
        chl.setAdapter(mHistory);
        chl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Zamowienia tmp = Orders.get(Orders.indexOf(new Zamowienia((int)id)));

                Toast.makeText(getParent(), "Zaznaczyłeś\n" + tmp.getCustomerID() + " " + tmp.getUserID(), Toast.LENGTH_LONG).show();

                //Intent i = new Intent(KlienciActivity.this, ActivityZamowienia.class);
                //startActivity(i);
            }
        });
    }

    private void FillZamowienia(){
        Orders = db.getNotSynchronizedOrders();
    }
}
