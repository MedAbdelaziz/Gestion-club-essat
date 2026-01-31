package com.example.class_projet_app;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
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

import com.example.class_projet_app.MemberAdapter;

import java.util.ArrayList;

public class MembersList extends AppCompatActivity {

    ListView memList ;
    ArrayList<Member> members = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_list);
        memList = findViewById(R.id.membersList);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Members").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        members.add(new Member(doc.getId(),doc.getString("username"),doc.getString("email"),doc.getString("password"),doc.getString("phone")));
                    }
                    //members.forEach(item->Log.d("MemberList",item.getName()) );
                    MemberAdapter adapter = new MemberAdapter(MembersList.this,members);
                    memList.setAdapter(adapter);
                }
                else Toast.makeText(MembersList.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}