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

public class Attendance extends AppCompatActivity {

    mydbhelper mydb;
    int sroll,eroll;
    Button disbutton;
    Button presbutton;
    Button absbutton;
    int present,absent;
    int total;
    String message;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        context=this;
        mydb =new mydbhelper(this);
        Bundle b = getIntent().getExtras();
        String cname = b.getString("name");
        //goToClass gtc=new goToClass(cname);
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
        presbutton=(Button)findViewById(R.id.present);
        presbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        message+=String.valueOf(sroll)+"=p\n";
                        if(sroll<eroll) {
                            present++;
                            sroll++;
                            display();
                        }
                        else if(sroll==eroll){
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
                        if(sroll<eroll) {
                            absent++;
                            sroll++;
                            display();
                        }
                        else if(sroll==eroll){
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


}


