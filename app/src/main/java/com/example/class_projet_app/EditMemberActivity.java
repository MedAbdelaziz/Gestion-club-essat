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

public class EditMemberActivity extends AppCompatActivity {
    //ask about a better approach after doing this!!!!
    EditText nameV,emailV,passV,phoneV;
    String id,name,email,pass,phone;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member);

        id= getIntent().getStringExtra("id");
        nameV= findViewById(R.id.usernameInput);
        name= getIntent().getStringExtra("username");
        emailV = findViewById(R.id.emailInput);
        email = getIntent().getStringExtra("email");
        passV= findViewById(R.id.password);
        pass = getIntent().getStringExtra("password");
        phoneV = findViewById(R.id.phoneNumberInput);
        phone = getIntent().getStringExtra("phone");
        update = findViewById(R.id.updateBtn);
        nameV.setText(name);
        passV.setText(pass);
        emailV.setText(email);
        phoneV.setText(phone);
        Log.d("GET EXTRA", name+" "+email+" "+pass+" "+phone+" ");
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String,Object> member = new HashMap<String, Object>();
                member.put("username",nameV.getText().toString());
                member.put("email",emailV.getText().toString());
                member.put("password", passV.getText().toString());
                member.put("phone", phoneV.getText().toString());
                db.collection("Members").document(id).update(member).addOnSuccessListener(a->{
                    Toast.makeText(EditMemberActivity.this,"Updated successfully! ",Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(a->{
                    Toast.makeText(EditMemberActivity.this,"Updated Failed! ",Toast.LENGTH_SHORT).show();
                });
                Intent intent = new Intent(EditMemberActivity.this, MembersList.class);
                startActivity(intent);
            }
        });




    }

}