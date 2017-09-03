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
    mydbhelper mydb;

    Button b1;
    LinearLayout linearLayout;
    int n,i;
    String[] names;
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
            b1 = new Button(getActivity());
            b1.setText(res.getString(0));
            linearLayout.addView(b1);
        }
    }

}


