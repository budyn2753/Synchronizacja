package com.example.darek.handlowiec;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String URL_SAVE_NAME = "http://192.168.101.6:8080/sync/saveName.php";

    private DB db;
    private Button buttonZapisz;
    private EditText editTextImie;
    private EditText editTextNazwisko;
    private EditText editTextNrTel;
    private ListView listViewKlienci;

    private List<Klient> klienci;

    public static final int KLIENT_SYNCED_WITH_SERVER = 1;
    public static final int KLIENT_NOT_SYNCED_WITH_SERVER = 0;


    public static final String DATA_SAVED_BROADCAST = "com.example.darek.zapisdanych";

    private BroadcastReceiver broadcastReceiver;
    private KlientAdapter klientAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DB(this);
        klienci = new ArrayList<>();

        buttonZapisz = (Button) findViewById(R.id.buttonSave);
        editTextImie = (EditText) findViewById(R.id.editTextName);
        editTextNazwisko = (EditText) findViewById(R.id.editTextNazwiko);
        editTextNrTel = (EditText) findViewById(R.id.editTexNrTel);
        listViewKlienci = (ListView) findViewById(R.id.listViewNames);

        //nasłuchiwanie zdarzenia nacisniecia przycisku save
        buttonZapisz.setOnClickListener(this);

        //wywołanie metody wczytania wszytkich klientów
        loadKlienci();

        //broadcast receiver  aktualizacja statusów
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                //ponowne wczytanie Klientów
                loadKlienci();
            }
        };

        //registering the broadcast receiver to update sync status
        registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));
    }
    private void loadKlienci() {
        klienci.clear();
        Cursor cursor = db.getKlienci();
        if (cursor.moveToFirst()) {
            do {
                Klient klient = new Klient(
                        cursor.getString(cursor.getColumnIndex(DB.KOLUMNA_IMIE)),
                        cursor.getString(cursor.getColumnIndex(DB.KOLUMNA_NAZWISKO)),
                        cursor.getInt(cursor.getColumnIndex(DB.KOLUMNA_NR)),
                        cursor.getInt(cursor.getColumnIndex(DB.KOLUMNA_STATUS))
                );
                klienci.add(klient);
            } while (cursor.moveToNext());
        }

        klientAdapter = new KlientAdapter(this, R.layout.klienci,klienci);
        listViewKlienci.setAdapter(klientAdapter);
    }
    private void refreshList() {
        klientAdapter.notifyDataSetChanged();
    }

    /*
    * metoda odpowiedzialna za zapisywanie mdanych klientów na serwerze
    * */
    private void saveNameToServer() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Zapisywanie...");
        progressDialog.show();

        final String imie = editTextImie.getText().toString().trim();
        final String nazwisko = editTextNazwisko.getText().toString().trim();
        final String nrTel = editTextNrTel.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SAVE_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                //jeśli sukces zapisanie klienta w sqliteze statusem sycnced

                                saveNameToLocalStorage(imie, nazwisko,(Integer.parseInt(nrTel)), KLIENT_SYNCED_WITH_SERVER);
                            } else {
                                //jesli wystąpił jakis błąd zapisywanie klienta ze statusem unsynced
                                saveNameToLocalStorage(imie, nazwisko,(Integer.parseInt(nrTel)), KLIENT_NOT_SYNCED_WITH_SERVER);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        //kiedy wystąpi bład zapisu w sqlite ze statusem unsynced
                        saveNameToLocalStorage(imie, nazwisko,(Integer.parseInt(nrTel)), KLIENT_NOT_SYNCED_WITH_SERVER);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("imie", imie);
                params.put("nazwisko", nazwisko);
                params.put("nrtel", nrTel);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    //zapis do bazy sqlite
    private void saveNameToLocalStorage(String imie, String nazwisko, int nrTel,  int status) {
        editTextImie.setText("");
        editTextNazwisko.setText("");
        editTextNrTel.setText("");
        db.addKlient(imie,nazwisko,nrTel, status);
        Klient k = new Klient(imie,nazwisko, nrTel, status);
        klienci.add(k);
        refreshList();
    }

    @Override
    public void onClick(View view) {
        saveNameToServer();
    }
}


