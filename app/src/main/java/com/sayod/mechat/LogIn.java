package com.sayod.mechat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    EditText email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);

        email=findViewById(R.id.login_email_edittext);
        pass=findViewById(R.id.login_password_edittext);




        findViewById(R.id.login_signup_button).setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent intent=new Intent(LogIn.this,SignUp.class);
                                          startActivity(intent);
                                      }
                                  });

        findViewById(R.id.login_button).setOnClickListener(v -> {
                    String semail = email.getText().toString().trim();
                    String spass = pass.getText().toString().trim();

                    if (semail.isEmpty()) {
                        email.setError("Fill it before login");
                        return;
                    }
                    if(spass.isEmpty()){
                        pass.setError("Fill it before login");
                        return;
                    }

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(semail,spass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(LogIn.this,MainActivity.class));
                            }else{
                                Toast.makeText(LogIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                });





    }
}


