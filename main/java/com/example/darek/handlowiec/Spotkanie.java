package com.example.darek.handlowiec;

import java.util.Date;

/**
 * Created by Darek on 2017-10-29.
 */

public class Spotkanie {

    private int element_id;
    private String data;
    private String notatka;
    private int Klient_ID;

    public Spotkanie (int element_id, String data, String notatka, int kID){
        this.element_id = element_id;
        this.data = data;
        this.notatka = notatka;
        this.Klient_ID = kID;
    }
    public Spotkanie(int id){
        this.element_id = id;
    }

    public int getElement_id() {
        return element_id;
    }

    public void setElement_id(int element_id) {
        this.element_id = element_id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNotatka() {
        return notatka;
    }

    public void setNotatka(String notatka) {
        this.notatka = notatka;
    }

    public int getKlient_ID() {
        return Klient_ID;
    }

    public void setKlient_ID(int klient_ID) {
        Klient_ID = klient_ID;
    }



}
