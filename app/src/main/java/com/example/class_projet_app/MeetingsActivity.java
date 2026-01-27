package com.example.class_projet_app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MeetingsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MeetingAdapter adapter;
    ArrayList<Meeting> meetingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        recyclerView = findViewById(R.id.meetingsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        meetingList = new ArrayList<>();

        // Sample data (you can replace later with Firebase)
        meetingList.add(new Meeting(
                "Weekly Club Meeting",
                "2026-01-20",
                "Room A12",
                "Discussion about upcoming events",
                "Upcoming"
        ));

        meetingList.add(new Meeting(
                "Budget Review",
                "2026-01-10",
                "Room B05",
                "Review of club finances",
                "Done"
        ));

        meetingList.add(new Meeting(
                "Project Brainstorm",
                "2026-01-25",
                "Lab 3",
                "Ideas for new technical projects",
                "Upcoming"
        ));

        adapter = new MeetingAdapter(meetingList);
        recyclerView.setAdapter(adapter);
    }
}
