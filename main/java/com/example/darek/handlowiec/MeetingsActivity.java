package com.example.darek.handlowiec;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

public class MeetingsActivity extends AppCompatActivity {

    ArrayList<Spotkanie> Meetings = new ArrayList<Spotkanie>();
    ArrayList<String> displayedk = new ArrayList<String>();
    ArrayList<String>KlienciSP = new ArrayList<String>();
    DB db;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day , h, minute;
    final static int RQS_1 = 4;
    public int wybranyKlient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);


        db = new DB(this);

        FillSpotkania();
        KlienciSP = db.getClientsNames();
        //Toast.makeText(this,KlienciSP.toString(),Toast.LENGTH_SHORT).show();
        ListView chl = (ListView)findViewById(R.id.meetinglist);

        for(Spotkanie x: Meetings){
            displayedk.add("Spotkanie z :\t" + x.getKlient_ID()+ " o: " +x.getData() );
        }


        ArrayAdapter<String> mHistory = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,displayedk);
        chl.setAdapter(mHistory);
        chl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Spotkanie tmp = Meetings.get(Meetings.indexOf(new Spotkanie((int)id)));
                alertView(tmp.getNotatka());
            }
        });

        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH);
        day = now.get(Calendar.DAY_OF_MONTH);
        h = now.get(Calendar.HOUR);
        minute = now.get(Calendar.MINUTE);
    }


    @SuppressWarnings("deprecation")
    public void UstalSpotkanie (View v){
        final CharSequence[] nazwy = new CharSequence[KlienciSP.size()];
        int i=0;
        for (String k: KlienciSP) {
            nazwy[i] =k;
            i++;
            //Toast.makeText(this,nazwy[i]+"\n",Toast.LENGTH_SHORT).show();
        }

        AlertDialog.Builder chceknox_dialog = new AlertDialog.Builder(this);
        chceknox_dialog.setTitle("Wybierz Klienta");
        chceknox_dialog.setSingleChoiceItems(nazwy, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String txt = nazwy[i].toString();
                txt.trim();
                String [] tmp = txt.split(" ");
                wybranyKlient = Integer.parseInt(tmp[0]);
               // int z = Integer.getInteger(txt);

                Toast.makeText(getApplicationContext(),Integer.toString(wybranyKlient),Toast.LENGTH_SHORT).show();
                showDialog(999);
                dialogInterface.dismiss();
            }
        });
        AlertDialog zaznaczenie =chceknox_dialog.create();
        zaznaczenie.show();


    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub


        if (id == 999) {

            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        else if(id==888){
            return new TimePickerDialog(this,onTimeListener, h,minute,true);

        }

        return null;
    }

    @SuppressWarnings("deprecation")
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    year= arg1;
                    month= arg2;
                    day = arg3;
                    showDialog(888);

                }
            };
            private TimePickerDialog.OnTimeSetListener onTimeListener = new
                    TimePickerDialog.OnTimeSetListener(){
                        @Override
                        public  void onTimeSet(TimePicker view, int arg1, int arg2){
                            h = arg1;
                            minute =arg2;

                            Calendar gt = Calendar.getInstance();

                            gt.set(year,month,day,h,minute,00);
                            Calendar current = Calendar.getInstance();
                            if(gt.compareTo(current)<= 0){
                                Toast.makeText(getApplicationContext(),"Zła data",Toast.LENGTH_SHORT).show();

                            }
                            else {
                                long i;
                                i = gt.getTimeInMillis() - current.getTimeInMillis();
                                Toast.makeText(getApplicationContext(),"Dobra data: " + Long.toString(i) ,Toast.LENGTH_SHORT).show();
                                setAlarm(gt);
                            }


                            //setAlarm(calendar.getTimeInMillis());
                        }
                    };
    public String getDate(int y, int m, int d , int h, int min){
        StringBuilder sb = new StringBuilder();
        sb.append(y);
        sb.append("-");
        sb.append(m);
        sb.append("-");
        sb.append(d);
        sb.append("  ");
        sb.append(h);
        sb.append(":");
        sb.append(min);


        db.addSpotkanie(sb.toString(),"",wybranyKlient);
        FillSpotkania();
        return sb.toString();


    }
    public void FillSpotkania(){

        Meetings = db.getSpotkania();
        //Meetings.add(new Spotkanie(0,"2017-11-01","sadassdasd",1));
        //Meetings.add(new Spotkanie(1,"2017-10-16","asdsasdassdasd",2));
        //Meetings.add(new Spotkanie(2,"2017-10-16","saddasd",3));
    }
    private void alertView( String message ) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Notatka")
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                    }
                }).show();
    }

    private void setAlarm(Calendar target){

        Intent i = new Intent(MeetingsActivity.this, Alarm.class);
        i.putExtra("Naglowek", "Spotkanie z jegomościem");
        i.putExtra("Czas", getDate(year,month,day,h,minute));
        @SuppressWarnings("deprecation")
        PendingIntent pi = PendingIntent.getBroadcast(getBaseContext(),RQS_1,i,0);
        AlarmManager am =  (AlarmManager)getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP,target.getTimeInMillis(),pi);

    }


}
