package com.example.darek.handlowiec;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PodgladProduktowc extends AppCompatActivity {
    private DB db;
    private ListView listViewProdukty;
    private List<produkty> produkt;

    private ProduktAdapter produktAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new DB(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podglad_produktowc);
        produkt = new ArrayList<>();
        listViewProdukty = (ListView) findViewById(R.id.listViewProdukt);

        loadProdukty();
    }

    private void loadProdukty() {

            produkt.clear();
            Cursor cursor = db.getProdukt();
            if (cursor.moveToFirst()) {
                do {
                    produkty klient = new produkty(
                            cursor.getInt(cursor.getColumnIndex(DB.KOLUMNA_ID_Produktu)),
                            cursor.getInt(cursor.getColumnIndex(DB.KOLUMNA_ID_BAZA)),
                            cursor.getString(cursor.getColumnIndex(DB.KOLUMNA_CENA)),
                            cursor.getFloat(cursor.getColumnIndex(DB.KOLUMNA_CENA))
                    );
                    produkt.add(klient);
                } while (cursor.moveToNext());
            }

    }
}
