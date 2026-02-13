package com.example.class_projet_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PvActivity extends AppCompatActivity {

    Button autoFill;
    RecyclerView recyclerView;
    PvAdapter adapter;
    ArrayList<Pv> pvList;
    FirebaseFirestore db= FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pv);

        recyclerView = findViewById(R.id.pvRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pvList = new ArrayList<>();
        autoFill = findViewById(R.id.fillBtn);
        // Sample PVs


        adapter = new PvAdapter(pvList);
        recyclerView.setAdapter(adapter);
        fetchPv();
        autoFill.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                pvList.clear();
                pvList.add(new Pv("1","PV Meeting 01", "2026-01-10"));
                pvList.add(new Pv("2","PV Meeting 02", "2026-01-20"));
                pvList.add(new Pv("3","PV Meeting 03", "2026-01-25"));
                adapter.notifyDataSetChanged();
                for(Pv pvO : pvList){
                    Map pv = new HashMap();
                    pv.put("title",pvO.getTitle());
                    pv.put("date",pvO.getDate());
                    db.collection("PVS").add(pv);
                }

            }
        });
    }
    public void fetchPv(){
        pvList.clear();
        db.collection("PVS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Pv pv = new Pv(
                                doc.getId().toString(),
                                doc.getString("title").toString(),
                                doc.getString("date").toString()
                        );
                        pvList.add(pv);
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(PvActivity.this,"Fetch Pv failed!", Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
