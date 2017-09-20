package com.example.darek.handlowiec;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class NotSynchronizedActivity extends AppCompatActivity {

    ArrayList<String> Niezsynchonizowane = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_synchronized);

        GetNotSynchronized();

        ListView chl = (ListView)findViewById(R.id.NSlist);

        ArrayAdapter<String> mHistory = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Niezsynchonizowane);
        chl.setAdapter(mHistory);
    }

    private void GetNotSynchronized(){
        Niezsynchonizowane.add("1");
        Niezsynchonizowane.add("2");
        Niezsynchonizowane.add("3");
        Niezsynchonizowane.add("4");
    }
}
