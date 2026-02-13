package com.example.class_projet_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivitiesActivity extends AppCompatActivity {
    Button autoFill;
    RecyclerView recyclerView;
    ActivityAdapter adapter;
    List<ActivityModel> activityList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        autoFill= findViewById(R.id.fillBtn);
        recyclerView = findViewById(R.id.recyclerActivities);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        activityList = new ArrayList<>();


        adapter = new ActivityAdapter(activityList);
        recyclerView.setAdapter(adapter);

        fetchActivity();
        autoFill.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                activityList.clear();
                activityList.add(new ActivityModel(
                        "1",
                        "Formation Android",
                        "15 Mars 2026",
                        "Initiation au développement Android avec Java"
                ));
                activityList.add(new ActivityModel(
                        "2",
                        "Workshop IA",
                        "22 Mars 2026",
                        "Découverte du Machine Learning et IA"
                ));
                activityList.add(new ActivityModel(
                        "3",
                        "Hackathon",
                        "10 Avril 2026",
                        "Compétition de programmation en équipe"
                ));
                adapter.notifyDataSetChanged();
                for(ActivityModel activityO : activityList){
                    Map<String, Object> activity = new HashMap<String,Object>();
                    activity.put("title",activityO.getTitle());
                    activity.put("date",activityO.getDate());
                    activity.put("description",activityO.getDescription());
                    db.collection("Activities").add(activity);
                }

            }
        });
    }
    public void fetchActivity(){
        activityList.clear();
        db.collection("Activities").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc: task.getResult()){
                        activityList.add(new ActivityModel(
                                doc.getId().toString(),
                                doc.getString("title").toString(),
                                doc.getString("date").toString(),
                                doc.getString("description").toString()
                        ));
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivitiesActivity.this, "Error fetching activities !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
