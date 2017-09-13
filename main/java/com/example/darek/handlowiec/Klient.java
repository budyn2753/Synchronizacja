package com.example.darek.handlowiec;

/**
 * Created by Darek on 2017-06-08.
 */

public class Klient {
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNrTel() {

        return nrTel;
    }

    public void setNrTel(int nrTel) {
        this.nrTel = nrTel;
    }

    public String getNazwiko() {

        return nazwiko;
    }

    public void setNazwiko(String nazwiko) {
        this.nazwiko = nazwiko;
    }

    public String getImie() {

        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    private String  imie;
    private String nazwiko;
    private int nrTel;
    private int status;

    public Klient(String imie, String nazwisko, int nrTel, int status){
        this.imie =imie;
        this.nazwiko = nazwisko;
        this.nrTel = nrTel;
        this.status =status;
    }

}
