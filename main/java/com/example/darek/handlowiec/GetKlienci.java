package com.example.darek.handlowiec;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;



/**
 * Created by Darek on 2017-09-12.
 */

public class GetKlienci extends AsyncTask<Object,Object,String> {
    public AsyncResponse delegate = null;

    @Override
    protected String doInBackground(Object... params) {


        try {

            String link = "http://192.168.0.99/getKlienci.php";
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
