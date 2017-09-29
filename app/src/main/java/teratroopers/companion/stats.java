package teratroopers.companion;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnDpWidthModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class stats extends Fragment implements View.OnClickListener{


    mydbhelper mydb;
    LinearLayout linearLayout;
    LinearLayout linearLayout2;
    String cname;
    View view;
    Bundle b;
    public String[] cols={"no","name","date 1","date 2","date 3","date 4","date 5"};
    public String[][] datess={
            {"1","pras","1","2","3"},
            {"2","bittu","1","2","3"},
            {"3","hello","1","2","3"},
            {"4","biscuit","1","2","3"},
            {"4","biscuit","1","2","3"},
            {"4","biscuit","1","2","3"},
            {"4","biscuit","1","2","3"},
            {"4","biscuit","1","2","3"},
            {"4","biscuit","1","2","3"},
            {"4","biscuit","1","2","3"},
            {"4","biscuit","1","2","3"},
            {"4","biscuit","1","2","3"},
    };



    public stats() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_stats, container, false);
        this.mydb = new mydbhelper((Context)this.getActivity());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        b=savedInstanceState;
        linearLayout=(LinearLayout)view.findViewById(R.id.linearRegister);
        linearLayout2=(LinearLayout)view.findViewById(R.id.lineartable);
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
            b1.setOnClickListener(stats.this);
        }
    }
    public void onClick(View v) {
        cname = v.getTag().toString();
        linearLayout.setVisibility(View.INVISIBLE);
        linearLayout2.setVisibility(View.VISIBLE);

        TableView<String[]> tableView=view.findViewById(R.id.tableView);
        tableView.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(getContext(),cols));
        tableView.setColumnCount(7);
        TableColumnDpWidthModel columnModel = new TableColumnDpWidthModel(getContext(),7,100);
        columnModel.setColumnWidth(1, 100);
        columnModel.setColumnWidth(2, 100);
        tableView.setColumnModel(columnModel);
        tableView.setDataAdapter(new SimpleTableDataAdapter(getContext(),datess));

    }

}
