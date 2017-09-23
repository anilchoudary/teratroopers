package teratroopers.companion;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class register extends Fragment implements View.OnClickListener {

    mydbhelper mydb;
    LinearLayout linearLayout;
    LinearLayout linearLayout2;
    String cname;

    public register() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_register, container, false);
        this.mydb = new mydbhelper((Context)this.getActivity());
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            linearLayout=(LinearLayout)view.findViewById(R.id.linearlayout);
            linearLayout.setVisibility(View.VISIBLE);
            linearLayout2=(LinearLayout)view.findViewById(R.id.linearlayout2);
            linearLayout2.setVisibility(View.INVISIBLE);
            createbuttons();
    }

    public void createbuttons() {
        Button b1;
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,150);
        lp1.setMargins(20, 20, 20, 10); //ltrd
        final Cursor res=mydb.getalldata();
        while (res.moveToNext()) {
            cname=res.getString(0);
            b1 = new Button(getActivity());
            b1.setText(res.getString(0));
            b1.setTag(res.getString(0));
            b1.setElevation(3.8f);
            b1.setBackgroundResource(R.drawable.backbutt);
            b1.setLayoutParams(lp1);
            b1.setGravity(Gravity.CENTER);
            linearLayout.addView(b1);
            b1.setOnClickListener(register.this);
        }
    }

    public void onClick(View v) {
        cname=v.getTag().toString();
        linearLayout.setVisibility(View.INVISIBLE);
        linearLayout2.setVisibility(View.VISIBLE);
        viewattendance();
    }

    public void viewattendance() {
        Button viewattendance;
        viewattendance = (Button) getView().findViewById(R.id.button);
        viewattendance.setOnClickListener(
                new
                        View.OnClickListener() {
                            public void onClick(View view) {
                                try{
                                    check();
                                }
                                catch(Exception e){
                                    Snackbar.make(view,"attendance not taken",Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        }
        );
    }

   public void check() {
        String date,d,m;
        DatePicker datePicker = (DatePicker) getView().findViewById(R.id.datePicker);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        if (day < 10) {
            d = "0" + day;
        }
        else {
            d = String.valueOf(day);
        }
        if (month < 10) {
            m = "0" + month;
        }
        else {
            m = String.valueOf(month);
        }
        date = "dt" + d + m + year;

        Cursor res = mydb.retrievedatatodisplayattendance(date, cname);
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append(res.getString(0) + "=");
            buffer.append(res.getString(1) + "\n");
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