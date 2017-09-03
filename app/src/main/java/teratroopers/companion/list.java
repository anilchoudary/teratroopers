package teratroopers.companion;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;




/**
 * A simple {@link Fragment} subclass.
 */
public class list extends Fragment {


    Button b1;

    public list() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_list, container, true);

       /* String btn="Button";
        //parent=view.findViewById(R.layout.fragment_list);

        for (int i = 0; i < 25; i++) {
            b1 = new Button(getActivity());
            b1.setId(i + 1);
            b1.setText(btn+" "+(i+1));
            b1.setTag(i);
           parent.addView(b1);

        }*/


        return view;
    }

}
