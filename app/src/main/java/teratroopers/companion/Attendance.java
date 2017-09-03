package teratroopers.companion;

import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Attendance extends AppCompatActivity {

    mydbhelper mydb;
    int sroll,eroll;
    Button disbutton;
    Button presbutton;
    Button absbutton;
    int present,absent;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        mydb =new mydbhelper(this);
        Bundle b = getIntent().getExtras();
        String cname = b.getString("name");
        getValues(cname);
        display();
        presentButton();
        absentButton();
    }

    public void getValues(String name) {

        disbutton = (Button) findViewById(R.id.rolldisplay);
        Cursor res = mydb.getcname(name);
        res.moveToNext();
        sroll = Integer.parseInt(res.getString(1));
        eroll = Integer.parseInt(res.getString(2));
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
                        present++;
                        if(sroll<eroll) {
                            sroll++;
                            display();
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
                        absent++;
                        if(sroll<eroll) {
                            sroll++;
                            display();
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
