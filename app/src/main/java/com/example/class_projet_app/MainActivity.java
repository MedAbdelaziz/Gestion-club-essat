package com.example.class_projet_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.emailInput);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> member= new HashMap<>();
                member.put("username",user);
                member.put("password",pass);
                
                if (user.equals("admin") && pass.equals("admin123")) {
                    Toast.makeText(MainActivity.this, "Admin Login Successful!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                    startActivity(intent);
                } else if (user.equals("member") && pass.equals("1234")) {
                    Toast.makeText(MainActivity.this, "Member Login Successful!", Toast.LENGTH_SHORT).show();
                    // Redirect to Member Activity
                    Intent intent = new Intent(MainActivity.this, MemberActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
