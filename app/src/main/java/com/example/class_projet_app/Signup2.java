package com.example.class_projet_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup2 extends AppCompatActivity {

    EditText email,username,phone,password;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        email= findViewById(R.id.emailInput);
        username= findViewById(R.id.usernameInput);
        phone = findViewById(R.id.phoneNumberInput);
        password= findViewById(R.id.password);
        signup = findViewById(R.id.signupButton);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> member = new HashMap<>();
                member.put("username",username.getText().toString().strip());
                member.put("email",email.getText().toString().strip());
                member.put("phone",phone.getText().toString().strip());
                member.put("password",password.getText().toString().strip());

                db.collection("Members").add(member).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Signup2.this,"Signed up successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Signup2.this, MainActivity.class);
                        startActivity(i);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Signup2.this,"Failure to Sign up", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }





}