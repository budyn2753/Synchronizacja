package com.example.darek.handlowiec;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MeetingsActivity extends AppCompatActivity {

    ArrayList<Spotkanie> Meetings = new ArrayList<Spotkanie>();
    ArrayList<String> displayedk = new ArrayList<String>();

    //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public Date date ;
    DB db;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day , h, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        db = new DB(this);

        FillSpotkania();

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
                //AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                //alertDialog.setTitle("Alert Dialog");
                //alertDialog.setMessage(tmp.getNotatka());
               // alertDialog.setIcon(R.drawable.success);
               // alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                   // public void onClick(DialogInterface dialog, int which) {
                   //     //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                  //  }
              //  });

               // alertDialog.show();

            }

        });

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        //showDate(year, month+1, day);
    }

    @SuppressWarnings("deprecation")
    public void UstalSpotkanie (View v){
        showDialog(999);
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
                    month= arg2+1;
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
                            showDate(year,month,day,h,minute);
                        }
                    };
    public void showDate(int y, int m, int d , int h, int min){
        StringBuilder sb = new StringBuilder();
        sb.append(d);
        sb.append("-");
        sb.append(m);
        sb.append("-");
        sb.append(y);
        sb.append("  ");
        sb.append(h);
        sb.append(":");
        sb.append(minute);

        Toast.makeText(this,sb.toString(),Toast.LENGTH_LONG).show();
    }
    public void FillSpotkania(){
       
        Meetings.add(new Spotkanie(0,"2017-11-01","",1));
        Meetings.add(new Spotkanie(1,"2017-10-16","",2));
        Meetings.add(new Spotkanie(2,"2017-10-16","saddasd",3));
    }
}
