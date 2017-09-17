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

public class GetProduktyAcivity extends AsyncTask<Object,Object,String> {
    public AsyncResponse delegate = null;

    @Override
    protected String doInBackground(Object... params) {


        try {

            String link = "http://192.168.101.4:8080/sync/getProdukty.php";
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
            sb.toString();

            return sb.toString();

        } catch(MalformedURLException e){
            return new String (e.getMessage());
        } catch(IOException e){
            return new String (e.getMessage());
        }
    }
    @Override
    protected void onPostExecute(String result)
    {
        delegate.processFinish(result);
    }

}