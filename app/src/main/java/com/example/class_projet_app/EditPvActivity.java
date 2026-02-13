package com.example.class_projet_app;

import android.content.Intent;
import android.os.Bundle;
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

public class EditPvActivity extends AppCompatActivity {

    Button update;
    EditText titleV,dateV;
    String id,title,date;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_pv);
        update = findViewById(R.id.updateBtn);
        id= getIntent().getStringExtra("id");
        titleV= findViewById(R.id.titleInput);
        title= getIntent().getStringExtra("title");
        dateV = findViewById(R.id.dateInput);
        date= getIntent().getStringExtra("date");

        titleV.setText(title);
        dateV.setText(date);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> pv = new HashMap<String,Object>();
                pv.put("title",titleV.getText().toString());
                pv.put("date",dateV.getText().toString());
                db.collection("PVS").document(id).update(pv).addOnSuccessListener(a->{
                    Toast.makeText(EditPvActivity.this,"Updated successfully! ",Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(a->{
                    Toast.makeText(EditPvActivity.this,"Updated Failed! ",Toast.LENGTH_SHORT).show();
                });
                Intent intent = new Intent(EditPvActivity.this, ManagePvs.class);
                startActivity(intent);
            }
        });
    }
}