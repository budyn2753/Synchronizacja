package com.example.darek.handlowiec;

import android.widget.ArrayAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Darek on 2017-09-13.
 */

public class DBmySQL {


    public String Logon(String username, String password){
        try {


            String link = "http://192.168.0.99/logon.php";
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");

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

    public String syncProdukty() {

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
                return sb.toString();


            } catch(MalformedURLException e){
                return new String (e.getMessage());
            } catch(IOException e){
            return new String (e.getMessage());
            }


        }
    }
