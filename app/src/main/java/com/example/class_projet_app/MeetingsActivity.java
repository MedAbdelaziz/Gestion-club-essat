package com.example.class_projet_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
import java.util.HashMap;
import java.util.Map;

public class MeetingsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button autoFill;
    MeetingAdapter adapter;
    ArrayList<Meeting> meetingList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        autoFill = findViewById(R.id.fillBtn);
        recyclerView = findViewById(R.id.meetingsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        meetingList = new ArrayList<>();


        adapter = new MeetingAdapter(meetingList);
        recyclerView.setAdapter(adapter);
        fetchMeetings();
        autoFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        "Live"
                ));
                adapter.notifyDataSetChanged();
                for(Meeting meetingO : meetingList){
                    Map<String,Object> meeting = new HashMap<String,Object>();
                    meeting.put("title",meetingO.getTitle());
                    meeting.put("date", meetingO.getDate());
                    meeting.put("location",meetingO.getLocation());
                    meeting.put("description",meetingO.getDescription());
                    meeting.put("status",meetingO.getStatus());
                    db.collection("Meetings").add(meeting);


                }

            }
        });

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
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
