package teratroopers.companion;

import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class test extends AppCompatActivity {
    mydbhelper mydb;
    EditText a1,a2,a3;
    Button viw;
    TextView tv;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mydb =new mydbhelper(this);
        a1 = (EditText) findViewById(R.id.editText5);
        a2 = (EditText) findViewById(R.id.editText6);
        a3 = (EditText) findViewById(R.id.editText7);
        viw=(Button)findViewById(R.id.button6);
        fab=(FloatingActionButton)findViewById(R.id.confirmfab);
        confirmfab();
        viewall();
        //backbtn();

    }
    public void confirmfab()
    {

        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                                int sr, er;
                                String cname;
                                sr = Integer.parseInt(a2.getText().toString());
                                er = Integer.parseInt(a3.getText().toString());
                                cname=a1.getText().toString();

                                if (sr < er) {
                                    boolean isInserted=mydb.dbname(cname,sr,er);
                                   // boolean isInserted = mydb.insertData(cname, sr, er);
                                    if (isInserted == true) {
                                        Toast.makeText(test.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                                        a1.setText("");
                                        a2.setText("");
                                        a3.setText("");
                                        tv=(TextView)findViewById(R.id.classaddtext);
                                        tv.animate().alpha(1).setDuration(1500);
                                        } else
                                    Toast.makeText(test.this, "internal error occurred! please create class with another name", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                Toast.makeText(test.this, "Starting roll no should be less than Ending roll no", Toast.LENGTH_SHORT).show();
                                }
                        }
                        catch (Exception e) {
                                Toast.makeText(test.this, "Enter data in all fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
    public void viewall(){

        viw.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = mydb.getalldata();
                        if (res.getCount() == 0) {
                            //show message
                            showmessage("Error", "Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ClassName :" + res.getString(0) + "\n");
                            buffer.append("Starting Roll :" + res.getString(1) + "\n");
                            buffer.append("Ending Roll :" + res.getString(2) + "\n");

                        }
                        //show all data
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

   /* public void backbtn(){
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }
        );
    }*/
}
