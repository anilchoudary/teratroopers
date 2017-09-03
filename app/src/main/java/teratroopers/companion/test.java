package teratroopers.companion;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class test extends AppCompatActivity {
    mydbhelper mydb;
    EditText a1,a2,a3;
    Button con,viw,btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mydb =new mydbhelper(this);
        btn=(Button)findViewById(R.id.backbtn);
        a1 = (EditText) findViewById(R.id.editText5);
        a2 = (EditText) findViewById(R.id.editText6);
        a3 = (EditText) findViewById(R.id.editText7);
        con=(Button)findViewById(R.id.button5);
        viw=(Button)findViewById(R.id.button6);
        confirm();
        viewall();
        backbtn();

    }
    public void confirm()
    {
        con.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted= mydb.insertData(a1.getText().toString(),Integer.parseInt(a2.getText().toString()),
                                Integer.parseInt(a3.getText().toString()) );
                        if(isInserted==true)
                            Toast.makeText(test.this, "victory", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(test.this, "you screwd up", Toast.LENGTH_SHORT).show();

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

    public void backbtn(){
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }
        );
    }
}
