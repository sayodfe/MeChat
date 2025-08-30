package com.sayod.mechat;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    EditText inputMessage;
    ListView listView;
    ArrayList<Message> messagesList;
    BaseAdapter adapter;
    DatabaseReference dataref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        inputMessage = findViewById(R.id.home_edittext);
        listView = findViewById(R.id.home_listview);

        messagesList = new ArrayList<>();
        adapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return messagesList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View cv, ViewGroup parent) {
                if(cv==null){
                    cv=getLayoutInflater().inflate(R.layout.item_message_self,null);
                }
                TextView others=cv.findViewById(R.id.item_message_other_user_textview);
                TextView metv=cv.findViewById(R.id.txtMessage);
                if(messagesList.get(position).getUserid().equals(FirebaseAuth.getInstance().getUid())){
                    others.setVisibility(View.INVISIBLE);
                    metv.setVisibility(View.VISIBLE);
                    metv.setText(messagesList.get(position).getText());
                }else{
                    metv.setVisibility(View.INVISIBLE);
                    others.setVisibility(View.VISIBLE);
                    others.setText(messagesList.get(position).getText());
                }
                return cv;
            }
        };
        listView.setAdapter(adapter);


        // Reference to "messages" path in database
        dataref = FirebaseDatabase.getInstance().getReference("messages");

        // Send button
        findViewById(R.id.home_send_button).setOnClickListener(v -> {
            String text = inputMessage.getText().toString().trim();
            if (!text.isEmpty()) {
                Message message = new Message( text,FirebaseAuth.getInstance().getUid(),FirebaseAuth.getInstance().getCurrentUser().getEmail());
                dataref.push().setValue(message);  // store message in DB
                inputMessage.setText("");
            } else {
                Toast.makeText(Home.this, "Enter a message", Toast.LENGTH_SHORT).show();
            }
        });

        // Listen for new messages
        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesList=new ArrayList<>();
                for (DataSnapshot msgSnapshot : snapshot.getChildren()) {
                    Message msg = msgSnapshot.getValue(Message.class);
                    messagesList.add(msg);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });






    }
}