package com.example.darek.handlowiec;

/**
 * Created by Darek on 2017-06-08.
 */

public class Klient {
    private int ID;
    private String Nazwa;
    private int ID_baza;
    private int nrTel;

    public int getID(){
        return ID;
    }




    public void setID(int id){
        this.ID =id;
    }

    public int getNrTel() {

        return nrTel;
    }

    public void setNrTel(int nrTel) {
        this.nrTel = nrTel;
    }



    public String getNazwa() {

        return Nazwa;
    }

    public void setNazwa(String nazwa) {
        this.Nazwa = nazwa;
    }



    public Klient(int id_k, String Nazwa,  int nrTel){
        this.ID = id_k;
        this.Nazwa = Nazwa;

        this.nrTel = nrTel;

    }
    public Klient(int id_k){
        this.ID = id_k;
    }

    public int getID_baza() {
        return ID_baza;
    }

    public void setID_baza(int ID_baza) {
        this.ID_baza = ID_baza;
    }

    @Override
    public boolean equals(Object object)
    {
        boolean sameSame = false;

        if (object != null && object instanceof Klient)
        {
            sameSame = this.getID() == ((Klient) object).getID();
        }

        return sameSame;
    }
}
