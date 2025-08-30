package com.sayod.mechat;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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
    ArrayList<String> messagesList;
    ArrayAdapter<String> adapter;
    DatabaseReference dataref;

    String username= FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        inputMessage = findViewById(R.id.home_edittext);
        listView = findViewById(R.id.home_listview);

        messagesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messagesList);
        listView.setAdapter(adapter);

        // Reference to "messages" path in database
        dataref = FirebaseDatabase.getInstance().getReference("messages");

        // Send button
        findViewById(R.id.home_send_button).setOnClickListener(v -> {
            String text = inputMessage.getText().toString().trim();
            if (!text.isEmpty()) {
                Message message = new Message(username, text);
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
                messagesList.clear();
                for (DataSnapshot msgSnapshot : snapshot.getChildren()) {
                    Message msg = msgSnapshot.getValue(Message.class);
                    if (msg != null) {
                        messagesList.add(msg.username + ": " + msg.text);
                    }
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