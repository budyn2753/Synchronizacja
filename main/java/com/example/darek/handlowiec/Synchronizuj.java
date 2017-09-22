package com.example.darek.handlowiec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Synchronizuj extends AppCompatActivity implements AsyncResponse {
    String logedUser;
    DB db;
    getIdZamowienia getIDz = new getIdZamowienia();
    int id_Zamowienia, idKlienta;
    String idZamzBazy ;

    private TextView Szczegoly;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronizuj);

        Szczegoly =(TextView)findViewById(R.id.textViewSzczegoly);
        logedUser = getIntent().getStringExtra("logedUser");
        id_Zamowienia = getIntent().getIntExtra("IDZamowienia",0);
        idKlienta =  getIntent().getIntExtra("IDKlienta",0);
        Szczegoly.setText("Element synchronizowany to zamówienia dla klienta: " + Integer.toString(idKlienta));
        db = new DB(this);
        Toast.makeText(this,logedUser+Integer.toString(id_Zamowienia)+Integer.toString(idKlienta),Toast.LENGTH_LONG).show();

    }

    public void onClick2(View v) {

        if (Integer.parseInt(logedUser) == 0) {
            Toast.makeText(this, "Musisz być zalogowany aby synchronizować!!!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Synchronizuj.this, Logon.class);
            startActivity(i);
        } else {
            idZamzBazy = "";

            int licznik = Integer.parseInt(db.getUnsycedCountOfSzZamID(Integer.toString(id_Zamowienia)));


            new AddZamowienie(this).execute(logedUser, Integer.toString(idKlienta), Integer.toString(id_Zamowienia));
            getIDz.delegate = this;
            getIDz.execute(logedUser, Integer.toString(id_Zamowienia));
            try {
                idZamzBazy = getIDz.get();
                db.updateZamowienia(Integer.toString(id_Zamowienia), idZamzBazy);

                ArrayList<String> sb = db.getLastUnsyncIloscAndID(Integer.toString(id_Zamowienia));
                for (int i = 0; i <= licznik; i++) {

                    new addSzczegolyZamowienia(this).execute(idZamzBazy, sb.get(i), sb.get(i + 1));
                    db.updateSzczeglyZamowienia(idZamzBazy, Integer.toString(id_Zamowienia));

                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent i = new Intent(Synchronizuj.this, ShowNotSynchronizedOrders.class);
            i. putExtra("logedUser", logedUser);
            startActivity(i);


        }
    }
    @Override
    public void processFinish(String output) {
        idZamzBazy = output;
    }
}
