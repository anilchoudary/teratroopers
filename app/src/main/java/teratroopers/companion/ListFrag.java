package teratroopers.companion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;


public class ListFrag extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    View view;
    mydbhelper mydb;

    Button b1;
    LinearLayout linearLayout;
    int n,i;
    String cname;
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
    public void createbuttons() {

        Cursor res=mydb.getalldata();
        while (res.moveToNext()) {
            i=0;
            cname=res.getString(0);
            b1 = new Button(getActivity());
            b1.setText(res.getString(0));
            b1.setTag(res.getString(0));
            linearLayout.addView(b1);
            b1.setOnClickListener(ListFrag.this);
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


