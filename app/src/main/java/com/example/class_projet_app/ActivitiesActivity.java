package com.example.class_projet_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

public class ActivitiesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ActivityAdapter adapter;
    List<ActivityModel> activityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        recyclerView = findViewById(R.id.recyclerActivities);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        activityList = new ArrayList<>();
        activityList.add(new ActivityModel(
                "Formation Android",
                "15 Mars 2026",
                "Initiation au développement Android avec Java"
        ));
        activityList.add(new ActivityModel(
                "Workshop IA",
                "22 Mars 2026",
                "Découverte du Machine Learning et IA"
        ));
        activityList.add(new ActivityModel(
                "Hackathon",
                "10 Avril 2026",
                "Compétition de programmation en équipe"
        ));

        adapter = new ActivityAdapter(activityList);
        recyclerView.setAdapter(adapter);
    }
}
