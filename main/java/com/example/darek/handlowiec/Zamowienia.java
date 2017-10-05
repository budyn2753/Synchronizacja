package com.example.darek.handlowiec;

/**
 * Created by Darek on 2017-09-20.
 */

public class Zamowienia {
    private int ID;
    private int ID_zBazy;
    private int UserID;
    private int CustomerID;
    private int IDLocal;
    private String CustomerName;

    public Zamowienia (int ID, int ID_zBazy, int UserID, String CN){
        this.ID = ID;
        this.ID_zBazy=ID_zBazy;
        this.UserID =UserID;
        this.CustomerName=CN;
    }

    public Zamowienia (int ID, int IDLocal, int ID_zBazy, int UserID, String CN){
        this.ID = ID;
        this.IDLocal = IDLocal;
        this.ID_zBazy=ID_zBazy;
        this.UserID =UserID;
        this.CustomerName=CN;
    }


    public Zamowienia (int ID){
        this.ID = ID;
    }
    public int getIDLocal() {
        return IDLocal;
    }

    public void setIDLocal(int IDLocal) {
        this.IDLocal = IDLocal;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_zBazy() {
        return ID_zBazy;
    }

    public void setID_zBazy(int ID_zBazy) {
        this.ID_zBazy = ID_zBazy;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }


    @Override
    public boolean equals(Object object)
    {
        boolean sameSame = false;

        if (object != null && object instanceof Zamowienia)
        {
            sameSame = this.getID() == ((Zamowienia) object).getID();
        }

        return sameSame;
    }


    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }
}
