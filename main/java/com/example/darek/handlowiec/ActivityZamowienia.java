package com.example.darek.handlowiec;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class ActivityZamowienia extends Activity {

    //arraylist z zaznaczonymi rzeczami z niej zapisac zamówienie do bazy
    ArrayList<produkty> selectedItems = new ArrayList<produkty>();
    //arrayList z oferowanymi produktami
    ArrayList<produkty> items = new ArrayList<produkty>();
    String selectedItem;
    ArrayList<String> displayed = new ArrayList<String>();
    DB sqlLocal;

    //DBmySQL sqll;
    //Logon logon = new Logon();
    String txt = "";
    TextView SumaZakupow;


    public long idzaznaczone;
    public String IloscProduktow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sqlLocal = new DB(this);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zamowienia);

        sqlLocal = new DB(this);
        //sqll = new DBmySQL();

        ListView chl = (ListView)findViewById(R.id.checkable_list);
        chl.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        txt = getIntent().getStringExtra("tekst");
        Toast.makeText(this,txt, Toast.LENGTH_LONG).show();

        SumaZakupow = (TextView)findViewById(R.id.txtvie);

        FillProdukty();

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

    public void showSelectedItems(View view){
        //tu poprostu zapisze sie zamówienie do bazy
        String items="";
        for(produkty item:selectedItems){
            items+="-id: "+item.getId()+ "id z bazy: "+ item.getId_baza() + " nazwa: " + item.getNazwa() + " ilosc: " + item.getIlosc() + " cena: " + item.getCena() + "\n";
        }
        Toast.makeText(this,"Zaznaczyłeś\n" + items, Toast.LENGTH_LONG).show();
    }

    public void FillProdukty(){

        items = sqlLocal.getProducts();
        //String tmptmp = SigninActivity.this.line;
        //Toast.makeText(this,tmptmp, Toast.LENGTH_LONG).show();


        //trzeba coś zrobić z zużyciem pamięci !
        if (txt == "")
            items.add(new produkty(0,"Brak Połączenia z bazą", 0 ));
        else{
            int IloscKolumnWBazie = 4;
            String[] temp = txt.split(",");
            txt = "";
            int iter = temp.length / IloscKolumnWBazie;
            for(int i = 0; i < iter; i++){
                if (i == 0)
                    items.add(new produkty(i,Integer.parseInt(temp[0]), temp[1], Float.parseFloat(temp[2])));
                else
                    items.add(new produkty(i,Integer.parseInt(temp[i*IloscKolumnWBazie]), temp[(i*IloscKolumnWBazie) + 1], Float.parseFloat(temp[(i*IloscKolumnWBazie) + 2])));
            }

        }

    }
}
