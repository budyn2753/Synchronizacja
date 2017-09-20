package com.example.darek.handlowiec;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

public class addSzczegolyZamowienia extends AsyncTask<String,String,String> {

    Context context;

    public addSzczegolyZamowienia(Context context) {
        this.context =context;
    }

    @Override
    protected String doInBackground(String... arg0) {

        String orderid = (String) arg0[0];
        String productid = (String) arg0[1];
        String ilosc =(String) arg0[2];

        try {


            String link = "http://192.168.0.99/addSzczegolyZamowienia.php";
            String data = URLEncoder.encode("orderid", "UTF-8") + "=" + URLEncoder.encode(orderid, "UTF-8");
            data += "&" + URLEncoder.encode("productid", "UTF-8") + "=" +
                    URLEncoder.encode(productid, "UTF-8");
            data += "&" + URLEncoder.encode("ilosc", "UTF-8") + "=" +
                    URLEncoder.encode(ilosc, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = "";

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }


            return sb.toString();

        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }




    }
    @Override
    protected void onPostExecute(String result){
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show();

    }

}

