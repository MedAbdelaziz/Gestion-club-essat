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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddActivityActivity extends AppCompatActivity {
    Button add;
    EditText title,date,description;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);
        add = findViewById(R.id.addBtn);
        title= findViewById(R.id.titleInput);
        date = findViewById(R.id.dateInput);
        description = findViewById(R.id.descriptionInput);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> activity = new HashMap<String,Object>();
                activity.put("title",title.getText().toString());
                activity.put("date",date.getText().toString());
                activity.put("description",description.getText().toString());
                db.collection("Activities").add(activity).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivityActivity.this,"Failure to add activity !",Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(AddActivityActivity.this, ManageActivities.class);
                startActivity(intent);
            }
        });
    }
}