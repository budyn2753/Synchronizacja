package com.example.darek.handlowiec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Darek on 2017-09-14.
 */

public class ProduktAdapter extends ArrayAdapter<produkty> {

    //lista przechowująca wszystkich klientów
    private List<produkty> produkty;
    //objekt context
    private Context context;

    //konstruktor
    public ProduktAdapter(Context context, int resource, List<produkty> produkty){
        super(context,resource,produkty);
        this.context =context;
        this.produkty = produkty;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listVieweItem = inflater.inflate(R.layout.produkty,null,true);
        TextView textViewImie = (TextView) listVieweItem.findViewById(R.id.textViewProdukt);

       produkty produkt = produkty.get(position);

        textViewImie.setText( produkt.getId_baza()+" "+produkt.getNazwa() + " " + produkt.getCena() + " " +produkt.getIlosc());

        return listVieweItem;
    }


}
