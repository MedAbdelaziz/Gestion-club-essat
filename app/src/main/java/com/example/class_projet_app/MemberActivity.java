package com.example.class_projet_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MemberActivity extends AppCompatActivity {

    Button btnActivities, btnMeetings, btnPV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        btnActivities = findViewById(R.id.btnActivities);
        btnMeetings = findViewById(R.id.btnMeetings);
        btnPV = findViewById(R.id.btnPV);

        btnActivities.setOnClickListener(v ->
                startActivity(new Intent(this, ActivitiesActivity.class)));

        btnMeetings.setOnClickListener(v ->
                startActivity(new Intent(this, MeetingsActivity.class)));

        btnPV.setOnClickListener(v ->
                startActivity(new Intent(this, PvActivity.class)));
    }
}
