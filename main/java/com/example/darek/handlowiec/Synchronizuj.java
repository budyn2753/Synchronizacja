package com.example.darek.handlowiec;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Synchronizuj extends AppCompatActivity implements AsyncResponse {
    String logedUser;
    DB db;
    getIdZamowienia getIDz = new getIdZamowienia();
    int id_Zamowienia, idKlienta;
    String idZamzBazy ;

    private TextView Szczegoly, status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronizuj);

        Szczegoly =(TextView)findViewById(R.id.textViewSzczegoly);
        status = (TextView)findViewById(R.id.statustextView);
        logedUser = getIntent().getStringExtra("logedUser");
        id_Zamowienia = getIntent().getIntExtra("IDZamowienia",0);
        idKlienta =  getIntent().getIntExtra("IDKlienta",0);
        Szczegoly.setText("Element synchronizowany to zamówienia dla klienta: " + Integer.toString(idKlienta));
        db = new DB(this);
        Toast.makeText(this,logedUser+Integer.toString(id_Zamowienia)+Integer.toString(idKlienta),Toast.LENGTH_LONG).show();

    }

    public void onClick2(View v) {

        if (Integer.parseInt(logedUser) == 0) {
            Toast.makeText(this, "Musisz być zalogowany aby synchronizować!!!", Toast.LENGTH_SHORT).show();
            final Dialog login = new Dialog(this);
            // Set GUI of login screen
            login.setContentView(R.layout.logon_dialog);
            login.setTitle("Login");

            // Init button of login GUI
            Button btnLogin = (Button) login.findViewById(R.id.btnLogin);
            Button btnCancel = (Button) login.findViewById(R.id.btnCancel);
            final EditText txtUsername = (EditText)login.findViewById(R.id.txtUsername);
            final EditText txtPassword = (EditText)login.findViewById(R.id.txtPassword);

            // Attached listener for login GUI button
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(txtUsername.getText().toString().trim().length() > 0 && txtPassword.getText().toString().trim().length() > 0)
                    {

                        new SigninActivity(Synchronizuj.this, status).execute(txtUsername.getText().toString(), txtUsername.getText().toString());
                        //Redirect to dashboard / home screen.
                        login.dismiss();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please enter Username and Password", Toast.LENGTH_LONG).show();

                    }
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login.dismiss();
                }
            });

            // Make dialog box visible.
            login.show();
            Toast.makeText(this, "Musisz być zalogowany aby synchronizować!!!", Toast.LENGTH_SHORT).show();
            String txt = status.getText().toString();
            String [] tmp = txt.split(" ");

            logedUser = tmp[0];


        } else if(logedUser != "0") {
            idZamzBazy = "";

            int licznik = Integer.parseInt(db.getUnsycedCountOfSzZamID(Integer.toString(id_Zamowienia)));


            new AddZamowienie(this).execute(logedUser, Integer.toString(idKlienta), Integer.toString(id_Zamowienia));
            getIDz.delegate = this;
            getIDz.execute(logedUser, Integer.toString(id_Zamowienia));
            try {
                idZamzBazy = getIDz.get();

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            db.updateZamowienia(Integer.toString(id_Zamowienia), idZamzBazy);

            ArrayList<String> sb = db.getLastUnsyncIloscAndID(Integer.toString(id_Zamowienia));
            for (int i = 0; i <= licznik; i++) {
                try {
                    new addSzczegolyZamowienia(this).execute(idZamzBazy, sb.get(i), sb.get(i + 1));
                    db.updateSzczeglyZamowienia(idZamzBazy, Integer.toString(id_Zamowienia));
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
            Intent i = new Intent(Synchronizuj.this, ShowNotSynchronizedOrders.class);
            i. putExtra("logedUser", logedUser);
            startActivity(i);


        }
    }
    @Override
    public void onPause(){
        super.onPause();
        finish();
    }
    @Override
    public void processFinish(String output) {
        idZamzBazy = output;
    }
}
