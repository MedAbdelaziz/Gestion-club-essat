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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddMeetingActivity extends AppCompatActivity {
    EditText titleV,dateV,locationV,descriptionV;
    RadioGroup statusV;
    RadioButton checkedStatus;
    int checkedStatusId;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        titleV= findViewById(R.id.titleInput);
        dateV=findViewById(R.id.dateInput);
        locationV= findViewById(R.id.locationInput);
        descriptionV= findViewById(R.id.descriptionInput);
        statusV = findViewById(R.id.statusInput);
        add = findViewById(R.id.addBtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db =  FirebaseFirestore.getInstance();
                Map<String,Object> meeting = new HashMap<String,Object>();
                meeting.put("title",titleV.getText().toString());
                meeting.put("date", dateV.getText().toString());
                meeting.put("location",locationV.getText().toString());
                meeting.put("description",descriptionV.getText().toString());
                checkedStatusId = statusV.getCheckedRadioButtonId();
                if(checkedStatusId!=-1){
                    checkedStatus = findViewById(checkedStatusId);
                    meeting.put("status",checkedStatus.getText());
                }
                db.collection("Meetings").add(meeting).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddMeetingActivity.this, "Added Successfully!",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddMeetingActivity.this, "Error Adding!",Toast.LENGTH_SHORT).show();

                    }
                });
                Intent intent = new Intent(AddMeetingActivity.this, ManageMeetings.class);
                startActivity(intent);
            }
        });
    }
}