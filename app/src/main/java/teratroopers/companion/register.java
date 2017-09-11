package teratroopers.companion;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class register extends Fragment {

    mydbhelper mydb;
    Button viewattendance;
    String date;

    public register() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_register, container, false);
        this.mydb = new mydbhelper((Context)this.getActivity());
        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewattendance();
    }


    public void viewattendance() {
        viewattendance = (Button) getView().findViewById(R.id.button);
        viewattendance.setOnClickListener(
                new
                        View.OnClickListener() {
                            public void onClick(View view) {
                                try{
                                    check();
                                }
                                // Log.i("in click","ckick");
                                //check();
                                catch(Exception e){
                                    Snackbar.make(view,"required date attendance is not taken until now",Snackbar.LENGTH_SHORT).show();
                                }


                            }
                        }
        );
    }



   public void check() {
        DatePicker datePicker = (DatePicker) getView().findViewById(R.id.datePicker);
        String d, m;
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        if (day < 10)
            d = "0" + day;
        else
            d = String.valueOf(day);
        if (month < 10)
            m = "0" + month;
        else
            m = String.valueOf(month);

        date = "dt" + d + m + year;

        Log.i("sroll", date);
        // Log.i("sroll",cname);


        Cursor res = mydb.retrievedatatodisplayattendance(date, "prashanth");
        StringBuffer buffer = new StringBuffer();
        //res.moveToNext();
        //int k = Integer.parseInt(res.getString(0));
        //  Log.i("test", String.valueOf(k));
        // buffer.append(date+":-\n");

        while (res.moveToNext()) {
            buffer.append(res.getString(0) + "=");
            buffer.append(res.getString(1) + "\n");


            //buffer.append("Ending Roll :" + res.getString(2) + "\n");
        }
        showmessage(date, buffer.toString());



    }

    public void showmessage(String title,String Message) {
        android.support.v7.app.AlertDialog.Builder builder = new  android.support.v7.app.AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }



}
