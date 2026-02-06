package com.example.class_projet_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddMemberActivity extends AppCompatActivity {

    EditText nameV,emailV,passV,phoneV;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        nameV = findViewById(R.id.usernameInput);
        emailV = findViewById(R.id.emailInput);
        passV = findViewById(R.id.password);
        phoneV = findViewById(R.id.phoneNumberInput);
        add = findViewById(R.id.addBtn);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Log.d("GET EXTRA", name + " " + email + " " + pass + " " + phone + " ");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> member = new HashMap<>();
                member.put("username",nameV.getText().toString());
                member.put("email",emailV.getText().toString());
                member.put("password", passV.getText().toString());
                member.put("phone", phoneV.getText().toString());
                db.collection("Members").add(member).addOnSuccessListener(a -> {
                    Toast.makeText(AddMemberActivity.this, "Updated successfully! ", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(a -> {
                    Toast.makeText(AddMemberActivity.this, "Updated Failed! ", Toast.LENGTH_SHORT).show();
                });
                Intent intent = new Intent(AddMemberActivity.this, MembersList.class);
                startActivity(intent);
            }
        });

    }
}