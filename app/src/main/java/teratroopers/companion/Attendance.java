package teratroopers.companion;

import android.app.ProgressDialog;
import java.net.*;
import java.io.*;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.HapticFeedbackConstants.CONTEXT_CLICK;

public class Attendance extends AppCompatActivity {

    mydbhelper mydb;
    int sroll,eroll;
    Button disbutton;
    Button presbutton;
    Button absbutton;
    int total;
    int droll;
    int a;
    String date;
    String cname;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        context=this;
        mydb =new mydbhelper(this);
        Bundle b = getIntent().getExtras();
        cname = b.getString("name");
        getValues(cname);
        display();
        presentButton();
        absentButton();
    }

    public void getValues(String name) {

        disbutton = (Button) findViewById(R.id.rolldisplay);
        Cursor res = mydb.getcname(name);
        res.moveToNext();
        sroll = Integer.parseInt(res.getString(0));
        droll=sroll;
        res.moveToLast();
        eroll = Integer.parseInt(res.getString(0));
    }
    public void display(){
        String number=Integer.toString(droll);
        disbutton.setBackgroundColor(Color.CYAN);
        disbutton.setClickable(false);
        disbutton.setText(number);
    }

    //TODO store values in database and provide back button on snackbar (partially complete)
   public void presentButton(){
        total=(eroll-sroll)+1;
        presbutton=(Button)findViewById(R.id.present);
        presbutton.setBackgroundColor(Color.BLUE);
        presbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.performHapticFeedback(CONTEXT_CLICK);
                        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                        date = sdf.format(new Date());
                        date = "dt" + date;
                        if(droll<eroll) {
                            if (droll == sroll) {
                                Log.i("first:","droll=sroll");
                                mydb.alterTable(date,cname);
                            }
                            mydb.registerData(date,cname, droll, 1);
                            droll++;
                            display();
                        }
                        else if(droll==eroll){
                            mydb.registerData(date,cname, droll, 1);
                            disbutton.setText("Attendance complete");
                            Snackbar.make(view,"Attendance Complete",Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
        );
   }

    //TODO store values in database and provide back button on snackbar (partially complete)
    public void absentButton(){
       // a=sroll;
        total=(eroll-sroll)+1;
        absbutton=(Button)findViewById(R.id.absent);
        absbutton.setBackgroundColor(Color.BLUE);
        absbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.performHapticFeedback(CONTEXT_CLICK);
                        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                        date = sdf.format(new Date());
                        date="dt"+date;
                        if(droll<eroll) {
                            if (droll == sroll) {
                                mydb.alterTable(date,cname);
                            }
                            mydb.registerData(date,cname,droll,0);
                            droll++;
                            display();
                        }
                        else if(droll==eroll){
                            mydb.registerData(date,cname, droll, 0);
                            disbutton.setText("Attendance complete");
                            Snackbar.make(view,"Attendance Complete",Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }



}


