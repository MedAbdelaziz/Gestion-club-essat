package com.example.class_projet_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup2 extends AppCompatActivity {

    EditText email,username,phone,password;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        email= findViewById(R.id.emailInput);
        username= findViewById(R.id.usernameInput);
        phone = findViewById(R.id.phoneNumberInput);
        password= findViewById(R.id.password);
        signup = findViewById(R.id.signupButton);
        FireBaseFirestore db = FirebaseFirestore.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> member = new HashMap<>();
                member.put("username",username.getText().toString());
                member.put("email",email.getText().toString());
                member.put("phone",phone.getText().toString());
                member.put("password",password.getText().toString());
            }
        });
    }





}