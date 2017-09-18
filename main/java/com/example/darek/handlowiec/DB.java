package com.example.darek.handlowiec;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by Darek on 2017-06-07.
 */

public class DB extends SQLiteOpenHelper {

    //Stałe w bazie tj. nazwabazy, nazwa tabeli, nazwa kolumn
    public static final String DB_NAZWA ="android";
    public static final String TABELA_KLIENTOW ="Klienci";
    public static final String TABELA_PRODUKTOW ="Produkty";
    public static final String KOLUMNA_ID_KLIENT ="id";
    public static final String KOLUMNA_NAZWA_KLIENTA ="Nazwa";
    public static final String KOLUMNA_NAZWISKO ="Nazwisko";
    public static final String KOLUMNA_NR ="nrtel";
    public static final String KOLUMNA_STATUS ="STATUS";
    public static final String KOLUMNA_ID_Produktu ="id";
    public static final String KOLUMNA_NAZWA ="Nazwa";
    public static final String KOLUMNA_CENA ="Cena";
    public static final String KOLUMNA_ILOSC ="Ilosc";
    public static final String KOLUMNA_ID_BAZA_PRODUKTY ="id_baza";
    public static final String TABELA_ZAMOWIEN = "Zamowienie";
    public static final String TABELA_SZCZEGOLY_ZAMOWIENIA = "SzczegolyZamowienie";
    public static final String KOLUMNA_ID_ZAMOWIENIA = "ID";
    public static final String KOLUMNA_ID_Z_BAZY_ZAMOWIENIA = "ID_zBazy";
    public static final String KOLUMNA_ID_HANDLOWCA = "UserID";
    public static final String KOLUMNA_ID_KLIENTA = "CustomerID";
    public static final String KOLUMNA_ORDERDATE = "OrderDate";
    public static final String KOLUMNA_ID_SZCZEGOLY_ZAMOWIENIA= "CustomerID";
    public static final String KOLUMNA_ILOS_W_ZAMOWIENIU = "CustomerID";



    //wersja bazy
    private static final int DB_VERSION =2;



    //KONSTRUKTOR
    public DB(Context context){super(context,DB_NAZWA,null,DB_VERSION);}




    //Tworzenie bazy
    @Override
    public void onCreate(SQLiteDatabase db) {
       // DariuszDariusz
        String sql = "CREATE TABLE "+ TABELA_KLIENTOW + "("+KOLUMNA_ID_KLIENT+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KOLUMNA_NAZWA_KLIENTA + " VARCHAR, "  + KOLUMNA_NR + " INTEGER);";


        db.execSQL(sql);
        String sql2 = "CREATE TABLE "+ TABELA_PRODUKTOW + "("+KOLUMNA_ID_Produktu+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KOLUMNA_NAZWA_KLIENTA + " VARCHAR, " + KOLUMNA_CENA + " VARCHAR, " + KOLUMNA_ILOSC + " INTEGER, "
                + KOLUMNA_ID_BAZA_PRODUKTY + " INT); ";
        db.execSQL(sql2);

        String sql3 ="CREATE TABLE "+ TABELA_ZAMOWIEN + "("+KOLUMNA_ID_ZAMOWIENIA+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +KOLUMNA_ID_Z_BAZY_ZAMOWIENIA+ " INT, " + KOLUMNA_ID_HANDLOWCA + " INT, " + KOLUMNA_ID_KLIENTA + " INT, " + KOLUMNA_ORDERDATE + " VARCHAR(50)); ";
        db.execSQL(sql3);

        String sql4 ="CREATE TABLE "+ TABELA_SZCZEGOLY_ZAMOWIENIA + "("+KOLUMNA_ID_SZCZEGOLY_ZAMOWIENIA+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +KOLUMNA_ID_Z_BAZY_ZAMOWIENIA+ " INT, " + KOLUMNA_ID_Z_BAZY_ZAMOWIENIA + " INT, " + KOLUMNA_ILOSC + " INT, " + KOLUMNA_ORDERDATE + " VARCHAR(50)); ";
        db.execSQL(sql4);


    }
    //upgrading db
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE  Klienci";
        String sql1 = "DROP TABLE  Produkty";
        String sql2 = "DROP TABLE Zamowienie";
        String sql3 = "DROP TABLE SzczegolyZamowienie";
        db.execSQL(sql);
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        onCreate(db);
    }
    //Tworzenie Kilenta z podaniem statusu synchronizacji
    public boolean addKlient(String imie, String nazwisko, int nrTel, int status ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KOLUMNA_NAZWA_KLIENTA, imie);

        contentValues.put(KOLUMNA_NR, nrTel);


        db.insert(TABELA_KLIENTOW, null, contentValues);
        db.close();
        return true;
    }
    public boolean addProdukt(int id_Baza, String Nazwa, Double Cena, int Ilosc ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KOLUMNA_NAZWA_KLIENTA, Nazwa);
        contentValues.put(KOLUMNA_CENA, Cena);
        contentValues.put(KOLUMNA_ILOSC, Ilosc);
        contentValues.put(KOLUMNA_ID_BAZA_PRODUKTY,id_Baza);

        db.insert(TABELA_PRODUKTOW, null, contentValues);
        db.close();
        return true;
    }

    public boolean addZamowienie(int idBaza, int UserID, int CustomerID, String OrderDate){
        SQLiteDatabase db =this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KOLUMNA_ID_Z_BAZY_ZAMOWIENIA,idBaza);
        contentValues.put(KOLUMNA_ID_HANDLOWCA,UserID);
        contentValues.put(KOLUMNA_ID_KLIENTA, CustomerID);
        contentValues.put(KOLUMNA_ORDERDATE,OrderDate);

        db.insert(TABELA_ZAMOWIEN, null, contentValues);
        db.close();
        return true;

    }

    public boolean addSzczegolyZamowienia(int OrderID,int ProductID,int Ilosc){
        SQLiteDatabase db =this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KOLUMNA_ID_ZAMOWIENIA,OrderID);
        contentValues.put(KOLUMNA_ID_Produktu,ProductID);
        contentValues.put(KOLUMNA_ILOS_W_ZAMOWIENIU, Ilosc);


        db.insert(TABELA_SZCZEGOLY_ZAMOWIENIA, null, contentValues);
        db.close();


        return true;
    }

    //metoda zwracająca wszystkich Klientow
    public Cursor getKlienci(){
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM "+ TABELA_KLIENTOW +" ORDER BY " + KOLUMNA_ID_KLIENT +" ASC;";

        Cursor c = db.rawQuery(sql,null);
        return c;
    }
    public Cursor getProdukt(){
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT *  FROM "+ TABELA_PRODUKTOW+" ORDER BY " + KOLUMNA_ID_KLIENT +" ASC;";

        Cursor c = db.rawQuery(sql,null);
        return c;
    }

    public void clearProdukty(){
        String sql= "Delete FROM " +TABELA_PRODUKTOW+ ";";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);

    }


    public ArrayList<Klient> getClient(){
        ArrayList<Klient> client = new ArrayList<>();

        String sql ="Select * FROM " +TABELA_KLIENTOW + " Order by "+ KOLUMNA_ID_KLIENT+ " ASC;";

        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        int i =0;
        if(cursor.moveToFirst()){
            do{
                client.add(new Klient(i, cursor.getString(1),(Integer.parseInt(cursor.getString(2)))));
                i++;
            }while(cursor.moveToNext());
        }
        return client;
    }

    public ArrayList<produkty> getProducts(){
        ArrayList<produkty> products = new ArrayList<>();

        String sql ="Select * FROM " +TABELA_PRODUKTOW + " Order by "+ KOLUMNA_ID_Produktu+ " ASC;";

        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        int i =0;
        if(cursor.moveToFirst()){
            do{
                products.add(new produkty(i, (Integer.parseInt(cursor.getString(4))),cursor.getString(1),(Float.parseFloat(cursor.getString(2)))));
                i++;
            }while(cursor.moveToNext());
        }



        return products;
    }
    // metoda zwracająca wszystkich klientów których dane nie sa  zapisane na serwerze
    public Cursor getNiesynchronizowane(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " +TABELA_KLIENTOW +" WHERE "+ KOLUMNA_STATUS + " =0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
}
