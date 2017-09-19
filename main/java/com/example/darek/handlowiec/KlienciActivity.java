package com.example.darek.handlowiec;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class KlienciActivity extends AppCompatActivity {

    DB db;
    ArrayList<Klient> klienci = new ArrayList<Klient>();
    ArrayList<String> displayedk = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klienci);

        ListView chl = (ListView)findViewById(R.id.checkable_listK);
        chl.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        FillKlienci();

        for(Klient x: klienci){
            displayedk.add("Nazwa: " + x.getNazwa());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.rowlayout, R.id.txt_lan, displayedk);
        chl.setAdapter(adapter);
        chl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                    Klient tmp = klienci.get(klienci.indexOf(new Klient((int)id)));
                    //Toast.makeText(getParent(), "Zaznaczyłeś\n" + tmp.getNazwa(), Toast.LENGTH_LONG).show();
                    Intent i = new Intent(KlienciActivity.this, ActivityZamowienia.class);
                    i.putExtra("IDKlienta", tmp.getID_baza());
                    startActivity(i);
            }
        });
    }

    private void FillKlienci(){
        klienci = db.getClient();
        /*
        klienci.add(new Klient(0, 11, "Dariusz1",  723410501));
        klienci.add(new Klient(1, 22, "Dariusz11",  723410501));
        klienci.add(new Klient(2, 33, "Dariusz111",  723410501));
        klienci.add(new Klient(3, 44, "11111",  723410501));
        */
    }

}
