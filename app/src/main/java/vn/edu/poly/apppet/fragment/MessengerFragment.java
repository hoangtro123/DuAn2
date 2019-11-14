package vn.edu.poly.apppet.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import vn.edu.poly.apppet.R;


public class MessengerFragment extends Fragment {
    private View ib;
    private String user_name,room_name;

    private Button btnSend;
    private EditText msgInput;
    private ScrollView scrollView;
    private TextView textView;
    private DatabaseReference root ;
    private String temp_key;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ib = inflater.inflate (R.layout.fragment_messenger, container, false);

        intivity();

        Bundle bundle = getArguments ();
        room_name = bundle.getString ("room_name");
        user_name = bundle.getString ("user_name");

        root = FirebaseDatabase.getInstance().getReference().child(room_name);

        btnSend.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Map<String,Object> map = new HashMap<String, Object> ();
                temp_key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("msg",msgInput.getText().toString());

                message_root.updateChildren(map2);
            }
        });

        root.addChildEventListener (new ChildEventListener () {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return ib;
    }

    private void intivity() {

        btnSend = ib.findViewById(R.id.btn_send);
        msgInput = ib.findViewById(R.id.msg_input);
        scrollView = ib.findViewById(R.id.scrollView);
        textView = ib.findViewById(R.id.textView);



    }

    private String chat_msg,chat_user_name;

    private void append_chat_conversation(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()){

            chat_msg = (String) ((DataSnapshot)i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot)i.next()).getValue();

            textView.append(chat_user_name +" : "+chat_msg +" \n");
        }


    }


}
