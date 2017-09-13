package com.example.darek.handlowiec;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Darek on 2017-06-08.
 */

public class KlientAdapter extends ArrayAdapter<Klient> {

    //lista przechowująca wszystkich klientów
    private List<Klient> klienci;

    //objekt context
    private Context context;

    //konstruktor
    public KlientAdapter(Context context, int resource, List<Klient> klienci){
        super(context,resource,klienci);
        this.context =context;
        this.klienci = klienci;
    }


    @Override
    public View getView(int position,View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listVieweItem = inflater.inflate(R.layout.klienci,null,true);
        TextView textViewImie = (TextView) listVieweItem.findViewById(R.id.textViewName);
        //TextView textViewNazwisko = (TextView) listVieweItem.findViewById(R.id.textViewNazwisko);
        //TextView textViewNrTel = (TextView) listVieweItem.findViewById(R.id.textNrTelefonu);
        ImageView imageViewStatus = (ImageView) listVieweItem.findViewById(R.id.imageViewStatus);

        Klient klient = klienci.get(position);

        textViewImie.setText(klient.getImie() + " " + klient.getNazwiko() + " " +klient.getNrTel());
        //textViewNazwisko.setText(klient.getNazwiko());
        //textViewNrTel.setText(klient.getNrTel());

        if (klient.getStatus()==0)
            imageViewStatus.setBackgroundResource(R.drawable.stopwatch);
        else
            imageViewStatus.setBackgroundResource(R.drawable.success);

        return listVieweItem;
    }


}
