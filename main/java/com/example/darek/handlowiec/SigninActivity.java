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

public class SigninActivity extends AsyncTask<String,String,String> {

    private DBmySQL sql;
    private TextView statusField;
    private Context context;
    public String line;

    public SigninActivity(Context context,TextView statusField,int flag) {
        this.context = context;
        this.statusField = statusField;
        this.sql = new DBmySQL();

    }

    @Override
    protected String doInBackground(String... arg0) {

        String username = (String) arg0[0];
        String password = (String) arg0[1];

        //xD
        //nowy commit xDDD
        String result = sql.Logon(username, password);
        line =sql.getProduktyFromMySQL();




        return result;
    }
    @Override
    protected void onPostExecute(String result){
        this.statusField.setText(result);

    }

}


