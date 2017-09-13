package com.example.darek.handlowiec;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Darek on 2017-06-07.
 */

public class DB extends SQLiteOpenHelper {

    //Stałe w bazie tj. nazwabazy, nazwa tabeli, nazwa kolumn
    public static final String DB_NAZWA ="HandlowiecDB";
    public static final String TABELA_KLIENTOW ="Klienci";
    public static final String TABELA_PRODUKTOW ="Produkty";
    public static final String KOLUMNA_ID_KLIENT ="id";
    public static final String KOLUMNA_IMIE ="Imie";
    public static final String KOLUMNA_NAZWISKO ="Nazwisko";
    public static final String KOLUMNA_NR ="nrtel";
    public static final String KOLUMNA_STATUS ="STATUS";
    public static final String KOLUMNA_ID_Produktu ="id";
    public static final String KOLUMNA_NAZWA ="Nazwa";
    public static final String KOLUMNA_CENA ="Cena";
    public static final String KOLUMNA_ILOSC ="Ilosc";
    public static final String KOLUMNA_ID_BAZA ="id_baza";
    //wersja bazy
    private static final int DB_VERSION =1;

    //KONSTRUKTOR
    public DB(Context context){super(context,DB_NAZWA,null,DB_VERSION);}




    //Tworzenie bazy
    @Override
    public void onCreate(SQLiteDatabase db) {
       // DariuszDariusz
        String sql = "CREATE TABLE "+ TABELA_KLIENTOW + "("+KOLUMNA_ID_KLIENT+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KOLUMNA_IMIE + " VARCHAR, " + KOLUMNA_NAZWISKO + " VARCHAR, " + KOLUMNA_NR + " INTEGER, "
                +KOLUMNA_STATUS + " TINYINT); ";


        db.execSQL(sql);
        String sql2 = "CREATE TABLE "+ TABELA_PRODUKTOW + "("+KOLUMNA_ID_Produktu+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KOLUMNA_NAZWA + " VARCHAR, " + KOLUMNA_CENA + " VARCHAR, " + KOLUMNA_ILOSC + " INTEGER, "
                +KOLUMNA_ID_BAZA + " INT); ";
        db.execSQL(sql2);

    }
    //upgrading db
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXIST Persons";
        String sql1 = "DROP TABLE IF EXIST Produkty";
        db.execSQL(sql);
        db.execSQL(sql1);
        onCreate(db);
    }
    //Tworzenie Kilenta z podaniem statusu synchronizacji
    public boolean addKlient(String imie, String nazwisko, int nrTel, int status ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KOLUMNA_IMIE, imie);
        contentValues.put(KOLUMNA_NAZWISKO, nazwisko);
        contentValues.put(KOLUMNA_NR, nrTel);
        contentValues.put(KOLUMNA_STATUS,status);

        db.insert(TABELA_KLIENTOW, null, contentValues);
        db.close();
        return true;
    }
    public boolean addProdukt(int id_Baza, String Nazwa, Double Cena, int Ilosc ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KOLUMNA_NAZWA, Nazwa);
        contentValues.put(KOLUMNA_CENA, Cena);
        contentValues.put(KOLUMNA_ILOSC, Ilosc);
        contentValues.put(KOLUMNA_ID_BAZA,id_Baza);

        db.insert(TABELA_KLIENTOW, null, contentValues);
        db.close();
        return true;
    }

    //metoda zmieniająca status dla danego ID
    public boolean updateKlientStatus(int id, int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KOLUMNA_STATUS,status);
        db.update(KOLUMNA_STATUS,contentValues, KOLUMNA_ID_KLIENT + "=" + id, null);
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

    public ArrayList<produkty> getProducts(){
        ArrayList<produkty> products = new ArrayList<>();
        String sql ="Select * FROM" +TABELA_PRODUKTOW + " Order by "+ KOLUMNA_ID_Produktu+ " ASC;";

        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);

        if(cursor.moveToFirst()){
            do{
                products.add(new produkty((Integer.parseInt(cursor.getString(0))),(Integer.parseInt(cursor.getString(3))),cursor.getString(1),(Float.parseFloat(cursor.getString(2)))));
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
