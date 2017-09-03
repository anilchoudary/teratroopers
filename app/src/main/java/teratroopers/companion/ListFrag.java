package teratroopers.companion;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


public class ListFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    View view;
    mydbhelper mydb=new mydbhelper(getContext());
    Button b1;
    LinearLayout linearLayout;
    int n,i;
    String[] names;
    public ListFrag() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_list2, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //viewList();

        //paste

        linearLayout=(LinearLayout)view.findViewById(R.id.linearlayout);
        Cursor res = mydb.getalldata();
        if (res.getCount() == 0) {
            //show message
            // showmessage("Error", "Nothing found");
            return;
        }
        // StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            n++;
           /* buffer.append("ClassName :" + res.getString(0) + "\n");
            buffer.append("Starting Roll :" + res.getString(1) + "\n");
            buffer.append("Ending Roll :" + res.getString(2) + "\n");*/

            while(res.moveToNext()){
                names[i]=res.getString(0);
                Log.i("class name: ",names[i]);
                i++;
            }

        }
       // names=new String[n];

       // res.moveToFirst();


        //show all data
        //showmessage("Data", buffer.toString());
        // createbuttonslist();

    }
    /*public void viewList(){

        linearLayout=(LinearLayout)view.findViewById(R.id.linearlayout);
        Cursor res = mydb.getalldata();
        if (res.getCount() == 0) {
            //show message
           // showmessage("Error", "Nothing found");
            return;
        }
       // StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            n++;
           /* buffer.append("ClassName :" + res.getString(0) + "\n");
            buffer.append("Starting Roll :" + res.getString(1) + "\n");
            buffer.append("Ending Roll :" + res.getString(2) + "\n");

        }
        names=new String[n];

        res.moveToFirst();
        while(res.moveToNext()){
            names[i]=res.getString(0);
            Log.i("class name: ",names[i]);
            i++;
        }

        //show all data
        //showmessage("Data", buffer.toString());
       // createbuttonslist();
    }*/
   /* public void showmessage(String title,String Message) {
        AlertDialog.Builder builder = new  AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }*/
   /* public void createbuttonslist(){
        for(int i=0;i<n;i++) {
            b1 = new Button(getActivity());
            b1.setText(names[i]);
            linearLayout.addView(b1);
        }
    }*/
}


