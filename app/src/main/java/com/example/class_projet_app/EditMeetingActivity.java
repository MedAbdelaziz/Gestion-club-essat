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

public class EditMeetingActivity extends AppCompatActivity {
    EditText titleV,dateV,locationV,descriptionV;
    RadioGroup statusV;
    RadioButton checkedStatus;
    int checkedStatusId;
    String id,title,date,location,description,status;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meeting);
        id = getIntent().getStringExtra("id");
        titleV= findViewById(R.id.titleInput);
        title= getIntent().getStringExtra("title");
        dateV=findViewById(R.id.dateInput);
        date =  getIntent().getStringExtra("date");
        locationV= findViewById(R.id.locationInput);
        location= getIntent().getStringExtra("location");
        descriptionV= findViewById(R.id.descriptionInput);
        description= getIntent().getStringExtra("description");
        statusV = findViewById(R.id.statusInput);
        status = getIntent().getStringExtra("status");

        if(status.equals("Upcoming"))statusV.check(R.id.status1);
        else if (status.equals("Done"))statusV.check(R.id.status2);
        else if(status.equals("Live"))statusV.check(R.id.status3);
        update = findViewById(R.id.updateBtn);

        titleV.setText(title);
        locationV.setText(location);
        dateV.setText(date);
        descriptionV.setText(description);

        update.setOnClickListener(new View.OnClickListener() {
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
                db.collection("Meetings").document(id).update(meeting).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditMeetingActivity.this, "Updated Successfully!",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditMeetingActivity.this, "Error Updating!",Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(EditMeetingActivity.this, ManageMeetings.class);
                startActivity(intent);
            }
        });
    }
}
