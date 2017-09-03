package teratroopers.companion;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class home extends Fragment {


    public home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton fab=(FloatingActionButton) view.findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                      /*  register register = new register();
                        FragmentManager fragmentManager = getFragmentManager();
                        getActivity().setTitle("Attendance Register");
                        fragmentManager.beginTransaction().replace(R.id.fragment, register).commit();*/
                      Intent intent=new Intent("teratroopers.companion.test");
                        startActivity(intent);
                    }
                }
        );
        return view;
    }


}
