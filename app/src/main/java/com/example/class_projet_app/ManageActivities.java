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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ManageActivities extends AppCompatActivity {
    Button add ;
    ListView activitiesListV;
    List<ActivityModel> activitiesList = new ArrayList<ActivityModel>();
    List<String> titles = new ArrayList<String>();
    ArrayAdapter<String> adapter ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_activities);
        add = findViewById(R.id.addBtn);

        activitiesListV = findViewById(R.id.activitiesList);
        registerForContextMenu(activitiesListV);

        adapter = new ArrayAdapter<>(ManageActivities.this, android.R.layout.simple_list_item_1,titles);
        activitiesListV.setAdapter(adapter);

        fetchActivity();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageActivities.this,AddActivityActivity.class);
                startActivity(intent);
            }
        });
    }
    public void fetchActivity(){
        activitiesList.clear();
        titles.clear();
        db.collection("Activities").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc: task.getResult()){
                        activitiesList.add(new ActivityModel(
                                doc.getId().toString(),
                                doc.getString("title").toString(),
                                doc.getString("date").toString(),
                                doc.getString("description").toString()
                        ));
                        titles.add(doc.getString("title"));
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ManageActivities.this, "Error fetching activities !", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info){
        super.onCreateContextMenu(menu, v, info);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.members_list_menu, menu);
    }
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(info == null)super.onContextItemSelected(item);
        ActivityModel selectedActivity = activitiesList.get(info.position);
        if(item.getItemId()==R.id.deleteBtn){
            deleteActivity(selectedActivity, info.position);
            return true;
        }
        else if(item.getItemId()==R.id.editBtn){
            editActivity(selectedActivity);
            return true;
        }
        else return false;
    }
    public void deleteActivity(ActivityModel selectedActivity, int position){
        db.collection("Activities").document(selectedActivity.getId()).delete().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ManageActivities.this,"Error deleting activity !",Toast.LENGTH_SHORT);
            }
        });
        activitiesList.remove(position);
        titles.remove(position);
        adapter.notifyDataSetChanged();
    }
    public void editActivity(ActivityModel selectedActivity){
        Intent intent = new Intent(ManageActivities.this, EditActivityActivity.class);
        intent.putExtra("id",selectedActivity.getId());
        intent.putExtra("title",selectedActivity.getTitle());
        intent.putExtra("date",selectedActivity.getDate());
        intent.putExtra("description",selectedActivity.getDescription());
        startActivity(intent);
    }
}