package com.example.darek.handlowiec;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.widget.Toast;

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
    public static final String KOLUMNA_NR ="nrtel";
    public static final String KOLUMNA_ID_Produktu ="id";
    public static final String KOLUMNA_CENA ="Cena";
    public static final String KOLUMNA_ILOSC ="Ilosc";
    public static final String KOLUMNA_ID_BAZA_PRODUKTY ="id_baza";
    public static final String TABELA_ZAMOWIEN = "Zamowienie";
    public static final String TABELA_SZCZEGOLY_ZAMOWIENIA = "SzczegolyZamowienie";
    public static final String KOLUMNA_ID_ZAMOWIENIA = "ID";
    public static final String KOLUMNA_ID_Z_BAZY_ZAMOWIENIA = "ID_zBazy";
    public static final String KOLUMNA_ID_HANDLOWCA = "UserID";
    public static final String KOLUMNA_ID_KLIENTA = "CustomerID";
    public static final String KOLUMNA_ID_KLIENTA_BAZY= "CustomerID_zBazy";
    public static final String KOLUMNA_ORDERDATE = "OrderDate";
    public static final String KOLUMNA_ID_SZCZEGOLY_ZAMOWIENIA= "ID";
    public static final String KOLUMNA_ID_PRODUKTU_SZ ="ProductID";
    public static final String KOLUMNA_ID_ZAMOWIENIA_LOCAL = "IDZamLocal";


    private String LastID ="";
    private String[] Ooo;
    private ArrayList<String> Oo;
    private ArrayList<Integer> IDdoEdycji;
    //wersja bazy
    private static final int DB_VERSION =12;

    //KONSTRUKTOR
    public DB(Context context){super(context,DB_NAZWA,null,DB_VERSION);}

    //Tworzenie bazy
    @Override
    public void onCreate(SQLiteDatabase db) {
       // DariuszDariusz
        String sql = "CREATE TABLE "+ TABELA_KLIENTOW + "("+KOLUMNA_ID_KLIENT+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KOLUMNA_NAZWA_KLIENTA + " VARCHAR, "  + KOLUMNA_NR + " INTEGER, " + KOLUMNA_ID_KLIENTA_BAZY + " INTEGER);";


        db.execSQL(sql);
        String sql2 = "CREATE TABLE "+ TABELA_PRODUKTOW + "("+KOLUMNA_ID_Produktu+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KOLUMNA_NAZWA_KLIENTA + " VARCHAR, " + KOLUMNA_CENA + " VARCHAR, " + KOLUMNA_ILOSC + " INTEGER, "
                + KOLUMNA_ID_BAZA_PRODUKTY + " INT); ";
        db.execSQL(sql2);

        String sql3 ="CREATE TABLE "+ TABELA_ZAMOWIEN + "("+KOLUMNA_ID_ZAMOWIENIA+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +KOLUMNA_ID_Z_BAZY_ZAMOWIENIA+ " INT, " + KOLUMNA_ID_HANDLOWCA + " INT, " + KOLUMNA_ID_KLIENTA + " INT, " + KOLUMNA_ORDERDATE + " VARCHAR(50)); ";
        db.execSQL(sql3);

        String sql4 ="CREATE TABLE "+ TABELA_SZCZEGOLY_ZAMOWIENIA + "("+KOLUMNA_ID_SZCZEGOLY_ZAMOWIENIA+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +KOLUMNA_ID_ZAMOWIENIA_LOCAL+ " INT, " +KOLUMNA_ID_Z_BAZY_ZAMOWIENIA+ " INT, "+KOLUMNA_ID_PRODUKTU_SZ+ " INT, "  + KOLUMNA_ILOSC+ " INT, " + KOLUMNA_ORDERDATE + " VARCHAR(50)); ";
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
    public boolean addKlient(String nazwa, int nrTel, int id_zBazy){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(KOLUMNA_NAZWA_KLIENTA, nazwa);
            contentValues.put(KOLUMNA_NR, nrTel);
            contentValues.put(KOLUMNA_ID_KLIENTA_BAZY, id_zBazy);


            db.insert(TABELA_KLIENTOW, null, contentValues);
            db.close();
            return true;
        }catch (Exception e){
            throw e;
        }
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
    public boolean addZamowienie(int idBaza, int UserID, int CustomerID){
        SQLiteDatabase db =this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KOLUMNA_ID_Z_BAZY_ZAMOWIENIA,idBaza);
        contentValues.put(KOLUMNA_ID_HANDLOWCA,UserID);
        contentValues.put(KOLUMNA_ID_KLIENTA, CustomerID);


        db.insert(TABELA_ZAMOWIEN, null, contentValues);
        db.close();
        return true;

    }
    public boolean updateZamowienia(String id, String value){
        SQLiteDatabase db =this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KOLUMNA_ID_Z_BAZY_ZAMOWIENIA,value);

        db.update(TABELA_ZAMOWIEN, cv,KOLUMNA_ID_ZAMOWIENIA+ " = "+id, null);

        return true;
    }
    public boolean updateSzczeglyZamowienia(String id, String value){
        SQLiteDatabase db =this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KOLUMNA_ID_Z_BAZY_ZAMOWIENIA,value);

        db.update(TABELA_SZCZEGOLY_ZAMOWIENIA, cv,KOLUMNA_ID_ZAMOWIENIA+ " = "+id, null);

        return true;
    }
    public boolean addSzczegolyZamowienia(int OrderIDLocal,int OrderID,int ProductID,int Ilosc){
        SQLiteDatabase db =this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KOLUMNA_ID_ZAMOWIENIA_LOCAL,OrderIDLocal);
        contentValues.put(KOLUMNA_ID_Z_BAZY_ZAMOWIENIA,OrderID);
        contentValues.put(KOLUMNA_ID_PRODUKTU_SZ,ProductID);
        contentValues.put(KOLUMNA_ILOSC, Ilosc);


        db.insert(TABELA_SZCZEGOLY_ZAMOWIENIA, null, contentValues);
        db.close();


        return true;
    }
    public void clearProdukty(){
        String sql= "Delete FROM " +TABELA_PRODUKTOW+ ";";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);

    }
    public void clearKlienci(){
        String sql= "Delete FROM " +TABELA_KLIENTOW+ ";";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);

    }
    public String getLastOrderID(){
        String sql ="SELECT ID FROM " +TABELA_ZAMOWIEN+" ORDER BY id DESC LIMIT 1;";

        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                LastID = cursor.getString(0);
            }while(cursor.moveToNext());
        }
        return LastID;
    }
    public String[] getLastUnsyncID(){
        String sql ="SELECT ID, CustomerID FROM " +TABELA_ZAMOWIEN+" WHERE ID_zBazy = 0 ORDER BY id DESC LIMIT 1;";
        Ooo = new String[2];
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        int i =0;
        if(cursor.moveToFirst()){
            do{
                Ooo[0] = cursor.getString(0);
                Ooo[1] = cursor.getString(1);
            }while(cursor.moveToNext());
        }
        return Ooo;
    }

    public ArrayList<String> getLastUnsyncIloscAndID(String id){
        String sql ="SELECT ProductID, Ilosc FROM " +TABELA_SZCZEGOLY_ZAMOWIENIA+" WHERE IDZamLocal = "+id+";";
        Oo = new ArrayList<>();
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        int i =0;
        if(cursor.moveToFirst()){
            do{
                Oo.add(cursor.getString(0));
                Oo.add(cursor.getString(1));
            }while(cursor.moveToNext());
        }
        return Oo;
    }

    public ArrayList<Integer> getProductsIDFromSzczegoly(String id){
        String sql ="SELECT ProductID FROM " +TABELA_SZCZEGOLY_ZAMOWIENIA+" WHERE IDZamLocal = "+id+";";
        IDdoEdycji = new ArrayList<>();
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        int i =0;
        if(cursor.moveToFirst()){
            do{
                IDdoEdycji.add(Integer.parseInt(cursor.getString(0)));
            }while(cursor.moveToNext());
        }
        return IDdoEdycji;
    }
    public String getUnsycedCountOfSzZamID(String id){
        String sql ="SELECT COUNT (ID) FROM " +TABELA_SZCZEGOLY_ZAMOWIENIA+" WHERE IDZamLocal = "+id+ ";";
        String licznik = "";
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        int i =0;
        if(cursor.moveToFirst()){
            do{
                licznik = cursor.getString(0);

            }while(cursor.moveToNext());
        }
        return licznik;
    }
    public ArrayList<Zamowienia> getZamowienia(){
        ArrayList<Zamowienia> orders = new ArrayList<Zamowienia>();

        String sql ="Select Zamowienie.ID, Zamowienie.ID_zBazy,  Zamowienie.UserID, Klienci.Nazwa FROM " +TABELA_ZAMOWIEN + " Inner Join Klienci ON Zamowienie.CustomerID = Klienci.CustomerID_zBazy ;";

        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        int i =0;
        if(cursor.moveToFirst()){
            do{
                orders.add(new Zamowienia(i, Integer.parseInt(cursor.getString(0)),(Integer.parseInt(cursor.getString(1))),Integer.parseInt(cursor.getString(2)),cursor.getString(3)));
                i++;
            }while(cursor.moveToNext());
        }
        return orders;
    }
    public ArrayList<Zamowienia>getNotSynchronizedOrders(){
        ArrayList<Zamowienia> orders = new ArrayList<Zamowienia>();

        String sql ="Select ID, ID_zBazy, UserID, CustomerID FROM " +TABELA_ZAMOWIEN + " WHERE ID_zBazy = 0 Order by "+ KOLUMNA_ID_ZAMOWIENIA+ " ASC;";

        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        int i =0;
        if(cursor.moveToFirst()){
            do{
                orders.add(new Zamowienia(i, Integer.parseInt(cursor.getString(0)),(Integer.parseInt(cursor.getString(1))),(Integer.parseInt(cursor.getString(2))), cursor.getString(3)));
                i++;
            }while(cursor.moveToNext());
        }
        return orders;

    }
    public ArrayList<Klient> getClient(){
        ArrayList<Klient> client = new ArrayList<Klient>();

        String sql ="Select Nazwa, nrtel, CustomerID_zBazy FROM " +TABELA_KLIENTOW + " Order by "+ KOLUMNA_ID_KLIENT+ " ASC;";

        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        int i =0;
        if(cursor.moveToFirst()){
            do{
                client.add(new Klient(i, cursor.getString(0),(Integer.parseInt(cursor.getString(1))),(Integer.parseInt(cursor.getString(2)))));
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
    public ArrayList<produkty> getProductByID(String ID) {
        ArrayList<produkty> products = new ArrayList<>();

        String sql = "Select p.id, p.Nazwa, p.Cena, p.Ilosc, p.id_baza FROM  Produkty p, SzczegolyZamowienie s WHERE s.IDZamLocal = " + ID +
                " AND p.id_baza =  s.ProductID;";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                products.add(new produkty(i, (Integer.parseInt(cursor.getString(4))), cursor.getString(1), (Float.parseFloat(cursor.getString(2)))));
                i++;
            } while (cursor.moveToNext());
        }


        return products;
    }
}
