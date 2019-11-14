package vn.edu.poly.apppet.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import vn.edu.poly.apppet.R;


public class ChatFragment extends Fragment {

    private View v;

    private EditText room_name;
    private Button add_room;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList<> ();

    private String name;

    private DatabaseReference root = FirebaseDatabase.getInstance ().getReference ().getRoot ();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate (R.layout.fragment_chat, container, false);

        intivity ();
        request_user_name();


        add_room.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Map<String, Object> map = new HashMap<String, Object> ();
                map.put (room_name.getText ().toString (), "");
                root.updateChildren (map);

            }
        });

        root.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<String> ();
                Iterator i = dataSnapshot.getChildren ().iterator ();

                while (i.hasNext ()) {
                    set.add (((DataSnapshot) i.next ()).getKey ());
                }

                list_of_rooms.clear ();
                list_of_rooms.addAll (set);

                arrayAdapter.notifyDataSetChanged ();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        listView.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                Intent intent = new Intent(getActivity (),MessengerFragment.class);
//                intent.putExtra("room_name",((TextView)view).getText().toString() );
//                intent.putExtra("user_name",name);
//                startActivity(intent);

                MessengerFragment messengerFragment = new MessengerFragment ();

                Bundle bundle = new Bundle ();
                bundle.putString ("room_name", ((TextView)view).getText().toString() );
                bundle.putString ("user_name",name );
                messengerFragment.setArguments (bundle);


                Fragment fragment = messengerFragment;
                FragmentManager fragmentManager = getFragmentManager ();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                fragmentTransaction.replace (R.id.frameLayout, fragment);

                fragmentTransaction.commit ();
            }
        });


        return v;
    }

    private void intivity() {
        room_name = v.findViewById (R.id.room_name_edittext);
        add_room = v.findViewById (R.id.btn_add_room);
        listView = v.findViewById (R.id.listView);
        arrayAdapter = new ArrayAdapter<String> (getActivity (), android.R.layout.simple_list_item_1, list_of_rooms);
        listView.setAdapter (arrayAdapter);


    }

    private void request_user_name() {
        AlertDialog.Builder builder = new AlertDialog.Builder (getActivity ());
        builder.setTitle ("Enter name:");

        final EditText input_field = new EditText (getActivity ());

        builder.setView (input_field);
        builder.setPositiveButton ("OK", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                name = input_field.getText ().toString ();
            }
        });

        builder.setNegativeButton ("Can cel", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel ();
                request_user_name ();
            }
        });

        builder.show ();
    }


}
