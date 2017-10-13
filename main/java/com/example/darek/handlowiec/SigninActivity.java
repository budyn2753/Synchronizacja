package com.example.darek.handlowiec;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Darek on 2017-09-12.
 */

public class SigninActivity extends AsyncTask<String,Void,String> {


    private TextView statusField;
    private Context context;

    private ProgressDialog progres = null;

    public SigninActivity(Context context,TextView statusField,int flag) {
        this.context = context;
        this.statusField = statusField;

    }
    @Override
    protected void onPreExecute(){
        progres = new ProgressDialog(context);
        progres.setMessage("Proszę czekać");
        progres.setTitle("Logowanie");
        progres.show();
    }

    @Override
    protected String doInBackground(String... arg0) {

        String username = (String) arg0[0];
        String password = (String) arg0[1];

        try {


            String link = "http://192.168.0.99/logon.php";
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
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
        this.statusField.setText(result);
        progres.dismiss();

    }

}


