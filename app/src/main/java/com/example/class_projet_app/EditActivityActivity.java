package com.example.class_projet_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditActivityActivity extends AppCompatActivity {
    EditText titleV,dateV,descriptionV;
    String id,title,date,description;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_activity);
        id = getIntent().getStringExtra("id");
        titleV= findViewById(R.id.titleInput);
        title= getIntent().getStringExtra("title");
        dateV=findViewById(R.id.dateInput);
        date =  getIntent().getStringExtra("date");
        descriptionV= findViewById(R.id.descriptionInput);
        description= getIntent().getStringExtra("description");
        update = findViewById(R.id.updateBtn);

        titleV.setText(title);
        dateV.setText(date);
        descriptionV.setText(description);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db =  FirebaseFirestore.getInstance();
                Map<String,Object> activity = new HashMap<String,Object>();
                activity.put("title",titleV.getText().toString());
                activity.put("date", dateV.getText().toString());

                activity.put("description",descriptionV.getText().toString());

                db.collection("Activities").document(id).update(activity).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditActivityActivity.this, "Error Updating!",Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(EditActivityActivity.this, ManageActivities.class);
                startActivity(intent);
            }
        });
    }
}
