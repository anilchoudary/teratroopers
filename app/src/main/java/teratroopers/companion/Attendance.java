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
import android.util.*;
import android.view.View;
import android.widget.Button;
import java.text.*;
import java.util.*;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Attendance extends AppCompatActivity {

    mydbhelper mydb;
    int sroll,eroll;
    Button disbutton;
    Button presbutton;
    Button absbutton,view;
    int present,absent;
    int total;
    int c=0;
    int roll;
    String message;
    String cname;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //view=(Button)findViewById(R.id.button5) ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        context=this;
        mydb =new mydbhelper(this);
        Bundle b = getIntent().getExtras();
        cname = b.getString("name");
        //goToClass gtc=new goToClass(cname);
        getValues(cname);
        display();
        presentButton();
        absentButton();
        viewbutton();


    }

    public void getValues(String name) {

        disbutton = (Button) findViewById(R.id.rolldisplay);
        Cursor res = mydb.getcname(name);
        res.moveToNext();
        sroll = Integer.parseInt(res.getString(0));
        Log.i("sroll",String.valueOf(sroll));
        res.moveToLast();
        eroll = Integer.parseInt(res.getString(0));
        Log.i("sroll",String.valueOf(eroll));
    }
    public void display(){
        String number=Integer.toString(sroll);
        disbutton.setBackgroundColor(Color.BLUE);
        disbutton.setClickable(false);
        disbutton.setText(number);
    }

    //TODO store values in database and provide back button on snackbar
    //present button click upon attendance completion increases present count(check)

   public void presentButton(){
        total=(eroll-sroll)+1;
       c=sroll;
        presbutton=(Button)findViewById(R.id.present);
        presbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        message+=String.valueOf(sroll)+"=p\n";

                        if(c==sroll)
                        {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                                String date = sdf.format(new Date());
                                date = "dt" + date;
                                Log.i("our  date:", date);
                                Log.i("cname:",cname);
                                mydb.alter(date,cname);
                            }
                            catch(Exception e)
                            {
                              Log.i("inserting...","im in catch");
                            }

                        }
                        if(sroll<eroll) {
                            Log.i("sroll",String.valueOf(sroll));
                            mydb.insertattendance(cname,1,sroll);
                            present++;
                            sroll++;
                            display();
                        }
                        else if(sroll==eroll){
                            mydb.insertattendance(cname,1,sroll);
                            present++;
                            sroll++;

                        }
                        else{
                            disbutton.setText("Attendance Complete");
                            Snackbar.make(view,"Attendance Complete: \n"+present+"/"+total+"are present",Snackbar.LENGTH_LONG).show();
                        }

                    }
                }
        );
    }

    //TODO store values in database and provide back button on snackbar

    public void absentButton(){
        total=(eroll-sroll)+1;
        absbutton=(Button)findViewById(R.id.absent);
        absbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        message+=String.valueOf(sroll)+"=a\n";
                        if(c==0)
                        {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                                String date = sdf.format(new Date());
                                date = "dt" + date;
                                Log.i("our  date:", date);
                                Log.i("cname:",cname);
                                mydb.alter(date,cname);
                            }
                            catch(Exception e)
                            {
                               // Log.i("catch","im in catch");
                                Log.i("insert code", "inserting..");
                            }
                            c++;
                        }

                            int a=sroll;
                        if(sroll<eroll) {
                            mydb.insertattendance(cname,0,sroll);
                            absent++;
                            sroll++;
                            display();


                                //mydb.atd( 0,cname,sroll);



                        }
                        else if(sroll==eroll){
                            mydb.insertattendance(cname,0,sroll);

                            absent++;
                            sroll++;
                        }
                        else{
                            disbutton.setText("Attendance complete");
                            Snackbar.make(view,"Attendance Complete: \n"+present+"/"+total+" are present",Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void viewbutton(){
        view=(Button)findViewById(R.id.button5) ;
        view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                       Cursor res = mydb.viewattendance(cname);
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append(res.getString(0));

                            //buffer.append("Ending Roll :" + res.getString(2) + "\n");
                        }
                        showmessage("Data", buffer.toString());
                    }
                }
        );
    }
    public void showmessage(String title,String Message) {
        AlertDialog.Builder builder = new  AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }
}


