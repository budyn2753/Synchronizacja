package com.example.darek.handlowiec;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;
import android.view.WindowManager;

public class ActivityZamowienia extends Activity implements AsyncResponse {



    //arraylist z zaznaczonymi rzeczami z niej zapisac zamówienie do bazy
    ArrayList<produkty> selectedItems = new ArrayList<produkty>();
    //arrayList z oferowanymi produktami
    ArrayList<produkty> items = new ArrayList<produkty>();
    String selectedItem;
    ArrayList<String> displayed = new ArrayList<String>();
    DB db;
    int IDKlienta;
    TextView SumaZakupow;
    public long idzaznaczone;
    public String IloscProduktow;
    public String logedUser;
    getIdZamowienia getIDz = new getIdZamowienia();
    String id_Zamowienia ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        db = new DB(this);
        logedUser = getIntent().getStringExtra("logedUser");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zamowienia);




        ListView chl = (ListView)findViewById(R.id.checkable_list);
        chl.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        IDKlienta = getIntent().getIntExtra("IDKlienta",0);
        SumaZakupow = (TextView)findViewById(R.id.txtview);

        FillProdukty();

        if(items.isEmpty()){
            Intent i = new Intent(ActivityZamowienia.this, Logon.class);
            Toast.makeText(this, "Cos poszło nie tak! Sprobuj ponownie", Toast.LENGTH_SHORT).show();
            startActivity(i);
        }

        for(produkty x: items){
            displayed.add(x.getNazwa() + " Cena: " + x.getCena());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.rowlayout, R.id.txt_lan, displayed);
        chl.setAdapter(adapter);
        chl.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                idzaznaczone = id;

                if(selectedItems.contains(new produkty((int)id)))
                {
                    selectedItems.remove(new produkty((int)id));
                    TextView x = (TextView)view;
                    produkty tmp = items.get(items.indexOf(new produkty((int)idzaznaczone)));
                    x.setText(tmp.getNazwa() + " Cena: " + tmp.getCena());
                }

                else
                {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ActivityZamowienia.this);
                    LayoutInflater inflater = ActivityZamowienia.this.getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.dialog, null);
                    final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

                    dialogBuilder.setView(dialogView);
                    dialogBuilder.setTitle("Ilość");
                    dialogBuilder.setMessage("Podaj Ilość Zamawianego Produktu");
                    dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            IloscProduktow = edt.getText().toString();
                            TextView x = (TextView)view;
                                if(!IloscProduktow.isEmpty()){
                                     selectedItems.add(new produkty(items.get(items.indexOf(new produkty((int)idzaznaczone))),Integer.parseInt(IloscProduktow)));
                                     x.setText(x.getText() + "\t Ilość: " + Integer.parseInt(IloscProduktow));

                                }
                                 else
                                {
                                    selectedItems.add(new produkty(items.get(items.indexOf(new produkty((int)idzaznaczone))), 1));
                                    x.setText(x.getText() + "\t Ilość: 1");
                                }

                            SumaZakupow.setText("Aktualna wartość zamówienia: " + PoliczCene());

                        }
                    });

                    final AlertDialog b = dialogBuilder.create();
                    edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                b.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                            }
                        }
                    });
                    b.show();

                }


                SumaZakupow.setText("Aktualna wartość zamówienia: " + PoliczCene());

            }
        });
    }

    private float PoliczCene(){
        float suma = 0;
        for(produkty item:selectedItems){
            suma += item.getCena() * item.getIlosc();
        }
        return suma;
    }

    public void AddNewZamowienie(View view) throws InterruptedException {
        //tu poprostu zapisze sie zamówienie do bazy


        String items="";

        if(Integer.parseInt(logedUser)==0) {
            db.addZamowienie(0, Integer.parseInt(logedUser), IDKlienta);
        }else {
            db.addZamowienie(0, Integer.parseInt(logedUser), IDKlienta);
            String lOrderID = db.getLastOrderID();
            String idKlienta = Integer.toString(IDKlienta);

            Toast.makeText(this, " Dodaje zamowienia jako Zalogowany: "+ logedUser+ "ID zamowienia: "+idKlienta + "Zalogowany: "+lOrderID, Toast.LENGTH_SHORT).show();
            new AddZamowienie(this).execute(logedUser, idKlienta, lOrderID);
            getIDz.delegate = this;
            getIDz.execute(logedUser, lOrderID);
            try {
                id_Zamowienia = getIDz.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


            db.updateZamowienia(lOrderID, id_Zamowienia);
        }

        for(produkty item:selectedItems){
            String idP, i;

            items+="-id: "+item.getId()+ "id z bazy: "+ item.getId_baza() + " nazwa: " + item.getNazwa() + " ilosc: " + item.getIlosc() + " cena: " + item.getCena() + "\n";
            idP= Integer.toString(item.getId_baza()).trim();
            i = Integer.toString(item.getIlosc()).trim();
            if(Integer.parseInt(logedUser)!=0) {
                new addSzczegolyZamowienia(this).execute(id_Zamowienia, idP, i);
                db.addSzczegolyZamowienia(Integer.parseInt(db.getLastOrderID()),Integer.parseInt(id_Zamowienia), item.getId_baza(), item.getIlosc());
            }else{
                db.addSzczegolyZamowienia(Integer.parseInt(db.getLastOrderID()),0,item.getId_baza(), item.getIlosc());
            }


        }


        Intent i = new Intent(ActivityZamowienia.this, ShowMyOrders.class);
        startActivity(i);
        //this.finish();

    }


    public void FillProdukty(){

        items = db.getProducts();

    }


    @Override
    public void processFinish(String output) {
        id_Zamowienia = output;
    }
}
