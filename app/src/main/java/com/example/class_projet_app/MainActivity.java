package com.example.class_projet_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    TextView signup;
    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.emailInput);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signup = findViewById(R.id.signupText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().strip();
                String pass = password.getText().toString().strip();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                if (user.equals("admin") && pass.equals("admin123")) {
                    Toast.makeText(MainActivity.this, "Admin Login Successful!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                    startActivity(intent);
                }
                else{
                    db.collection("Members").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                QuerySnapshot document = task.getResult();
                                if(!document.isEmpty()){
                                    for( QueryDocumentSnapshot doc : document){
                                        Log.d("here", ""+doc.get("username").toString().equals(user));
                                        if(doc.get("username").toString().equals(user)){
                                            Log.d("here",user);
                                            if(doc.get("password").toString().equals(pass)){
                                                Toast.makeText(MainActivity.this, "Member Login Successful!", Toast.LENGTH_SHORT).show();
                                                // Redirect to Member Activity
                                                Intent intent = new Intent(MainActivity.this, MemberActivity.class);
                                                startActivity(intent);
                                            }
                                            else{
                                                Toast.makeText(MainActivity.this, "Password Incorrect !", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                    Toast.makeText(MainActivity.this,"This Username dosen't exist\nRegister",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Signup2.class);
                startActivity(intent);
            }
        });
    }
}
