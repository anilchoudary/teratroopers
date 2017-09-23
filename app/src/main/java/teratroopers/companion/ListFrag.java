package teratroopers.companion;

import  android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class ListFrag extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    View view;
    mydbhelper mydb;

    Button b1;
    LinearLayout linearLayout;
    int i;
    String cname;
    String str;
    public ListFrag() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_list2, container, false);
        this.mydb = new mydbhelper((Context)this.getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linearLayout=(LinearLayout)view.findViewById(R.id.linearlayout);
        createbuttons();
    }

    //TODO create new background for buttons with sharper arrow
    public void createbuttons() {

        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,150);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
        lp1.setMargins(20, 20, 20, 10); //ltrd
        final Cursor res=mydb.getalldata();
        while (res.moveToNext()) {
            i=0;
            cname=res.getString(0);
            Log.i("name",cname);

            b1 = new Button(getActivity());
            b1.setText(res.getString(0));
            b1.setTag(res.getString(0));
            b1.setElevation(3.8f);
            b1.setBackgroundResource(R.drawable.backbutt);
            b1.setLayoutParams(lp1);
            b1.setGravity(Gravity.CENTER);
            linearLayout.addView(b1);
           //here

          /*  ImageView divider1 = new ImageView(getActivity());

            lp2.setMargins(20, 0, 20, 0);
            divider1.setLayoutParams(lp2);
            divider1.setElevation(0.5f);
            divider1.setBackgroundColor(Color.DKGRAY);

            linearLayout.addView(divider1);
            */

            //to here
            b1.setOnClickListener(ListFrag.this);
            b1.setOnLongClickListener(
                    new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            str=view.getTag().toString();
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Do you want to delete the class?")
                                    .setMessage("You can not undo the action")
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                mydb.deleteclass(str);
                                        }})
                                    .setNegativeButton(android.R.string.no, null).show();
                            return true;
                        }

                    }

            );

        }

    }

    public void onClick(View v) {
        String str=v.getTag().toString();
        //Toast.makeText(getActivity(),str+" clicked",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent("teratroopers.companion.Attendance");
        intent.putExtra("name",str);
        startActivity(intent);
        }

}


