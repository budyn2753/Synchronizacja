package com.example.darek.handlowiec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;



import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Darek on 2017-09-12.
 */

public class GetProduktyAcivity extends AsyncTask<String,String,String> {

    private DBmySQL sql ;
    private TextView statusField;
    private Context context;

    public String text;

    public GetProduktyAcivity(Context context, TextView status, String text) {
        this.context = context;
        this.statusField = status;
        this.text = text;


        this.sql = new DBmySQL();

    }

    @Override
    protected String doInBackground(String... arg0) {

        try {

            String link = "http://192.168.0.99/getProdukty.php";
            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));


            StringBuilder sb = new StringBuilder();
            String line = "";
            // Read Server Response


            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            text = sb.toString();
            return text;


        } catch(MalformedURLException e){
            return new String (e.getMessage());
        } catch(IOException e){
            return new String (e.getMessage());
        }
    }
    @Override
    protected void onPostExecute(String text){


    }

}