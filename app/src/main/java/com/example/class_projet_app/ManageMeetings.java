package com.example.class_projet_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ManageMeetings extends AppCompatActivity {
    Button addBtn;
    ListView meetListView;
    List<Meeting> meetList= new ArrayList<Meeting>();
    List<String> titles  = new ArrayList<String>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_meetings);
        addBtn = findViewById(R.id.addBtn);
        meetListView = findViewById(R.id.meetingsList);
        registerForContextMenu(meetListView);
        adapter = new ArrayAdapter<>(ManageMeetings.this, android.R.layout.simple_list_item_1,titles);
        meetListView.setAdapter(adapter);
        fetchMeeting();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageMeetings.this,AddMeetingActivity.class);
                startActivity(intent);
            }
        });
    }
    private void fetchMeeting(){
        meetList.clear();
        titles.clear();
        db.collection("Meetings").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Meeting meeting = new Meeting(
                                doc.getId().toString(),
                                doc.get("title").toString(),
                                doc.get("date").toString(),
                                doc.get("location").toString(),
                                doc.get("description").toString(),
                                doc.get("status").toString()
                        );
                        meetList.add(meeting);
                        titles.add(doc.getString("title").toString());
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(ManageMeetings.this,"Fetch data failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.members_list_menu, menu);

    }
    public boolean onContextItemSelected( MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(info == null) return super.onContextItemSelected(item);
        Meeting selectedMeeting = meetList.get(info.position);
        if(item.getItemId()== R.id.deleteBtn){
            deleteMeeting(selectedMeeting, info.position);
            return true;
        }
        else if(item.getItemId()==R.id.editBtn){
            editMeeting( selectedMeeting);
            return true;
        }
        else return false;
    }
    public void deleteMeeting(Meeting selectedMeeting, int position){
        db.collection("Meetings").document(selectedMeeting.getId()).delete()
                .addOnSuccessListener(a ->{
                    meetList.remove(selectedMeeting.getTitle());
                    titles.remove(selectedMeeting);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(ManageMeetings.this, "Deleted successfully!", Toast.LENGTH_SHORT).show();

                }).addOnFailureListener(e -> {
                    Toast.makeText(ManageMeetings.this, "Delete failed", Toast.LENGTH_SHORT).show();
                });

    }
    public void editMeeting(Meeting selectedMeeting){
        Intent intent = new Intent(ManageMeetings.this, EditMeetingActivity.class);
        intent.putExtra("id",selectedMeeting.getId());
        intent.putExtra("title",selectedMeeting.getTitle());
        intent.putExtra("date",selectedMeeting.getDate());
        intent.putExtra("location",selectedMeeting.getLocation());
        intent.putExtra("description",selectedMeeting.getDescription());
        intent.putExtra("status",selectedMeeting.getStatus());
        startActivity(intent);
    }

}