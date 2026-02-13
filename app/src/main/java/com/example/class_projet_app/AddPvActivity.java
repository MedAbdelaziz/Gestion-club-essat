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

public class AddPvActivity extends AppCompatActivity {

    Button add ;
    EditText title,date;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pv);
            title = findViewById(R.id.titleInput);
            date = findViewById(R.id.dateInput);
            add = findViewById(R.id.addBtn);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String,Object> pv = new HashMap<>();
                    pv.put("title", title.getText().toString());
                    pv.put("date", date.getText().toString());
                    db.collection("PVS").add(pv).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddPvActivity.this, "Pv added successfully !", Toast.LENGTH_SHORT);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddPvActivity.this, "Failure adding pv !", Toast.LENGTH_SHORT);
                        }
                    });
                    Intent intent = new Intent(AddPvActivity.this, ManagePvs.class);
                    startActivity(intent);
                }
            });

    }
}