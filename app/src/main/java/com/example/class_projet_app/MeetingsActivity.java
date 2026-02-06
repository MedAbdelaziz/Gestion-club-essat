package com.example.class_projet_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MeetingsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MeetingAdapter adapter;
    ArrayList<Meeting> meetingList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        recyclerView = findViewById(R.id.meetingsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        meetingList = new ArrayList<>();

        // Sample data (you can replace later with Firebase)
        meetingList.add(new Meeting(
                "1",
                "Weekly Club Meeting",
                "2026-01-20",
                "Room A12",
                "Discussion about upcoming events",
                "Upcoming"
        ));

        meetingList.add(new Meeting(
                "2",
                "Budget Review",
                "2026-01-10",
                "Room B05",
                "Review of club finances",
                "Done"
        ));

        meetingList.add(new Meeting(
                "3",
                "Project Brainstorm",
                "2026-01-25",
                "Lab 3",
                "Ideas for new technical projects",
                "Upcoming"
        ));

        adapter = new MeetingAdapter(meetingList);
        recyclerView.setAdapter(adapter);
        fetchMeetings();
    }
    public void fetchMeetings(){
        meetingList.clear();
        db.collection("Meetings").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        meetingList.add(new Meeting(
                                doc.getId(),
                                doc.getString("title"),
                                doc.getString("date"),
                                doc.getString("location"),
                                doc.getString("description"),
                                doc.getString("status")));
                    }
                }
            }
        });
    }
}
