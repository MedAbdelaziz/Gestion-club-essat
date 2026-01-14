package com.example.class_projet_app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class PvActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PvAdapter adapter;
    ArrayList<Pv> pvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pv);

        recyclerView = findViewById(R.id.pvRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pvList = new ArrayList<>();

        // Sample PVs
        pvList.add(new Pv("PV Meeting 01", "2026-01-10"));
        pvList.add(new Pv("PV Meeting 02", "2026-01-20"));
        pvList.add(new Pv("PV Meeting 03", "2026-01-25"));

        adapter = new PvAdapter(pvList);
        recyclerView.setAdapter(adapter);
    }
}
