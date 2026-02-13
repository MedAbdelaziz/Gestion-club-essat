
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

public class ManagePvs extends AppCompatActivity {
    Button add;
    ListView pvListView;
    List<String> titles  = new ArrayList<String>();
    List<Pv> PVS = new ArrayList<Pv>();
    ArrayAdapter<String> adapter ;
    FirebaseFirestore db  = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_pvs);
        add= findViewById(R.id.addBtn);
        pvListView = findViewById(R.id.pvList);
        registerForContextMenu(pvListView);
        adapter  = new ArrayAdapter<String>(ManagePvs.this,android.R.layout.simple_list_item_1,titles);
        pvListView.setAdapter(adapter);
        fetchPv();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagePvs.this,AddPvActivity.class);
                startActivity(intent);
            }
        });


    }
    public void fetchPv(){
        titles.clear();
        PVS.clear();
        db.collection("PVS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc: task.getResult()) {
                        Pv pv = new Pv(
                                doc.getId(),
                                doc.getString("title"),
                                doc.getString("date")
                        );
                        PVS.add(pv);
                        titles.add(doc.getString("title"));
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    Toast.makeText(ManagePvs.this,"Fetch data failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.members_list_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (info == null) return super.onContextItemSelected(item);

        Pv selectedPv = PVS.get(info.position);
        int listPosition = info.position;

        if (item.getItemId() == R.id.deleteBtn) {
            deleteMember(selectedPv, listPosition);
            return true;
        } else if (item.getItemId() == R.id.editBtn) {
            editMember(selectedPv);
            return true;
        }
        return false;
    }

    public void deleteMember(Pv selectedPv, int position) {

        db.collection("PVS").document(selectedPv.getId()).delete()
                .addOnSuccessListener(a -> {

                    PVS.remove(selectedPv);
                    titles.remove(position);
                    adapter.notifyDataSetChanged();

                    Toast.makeText(ManagePvs.this, "Deleted successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ManagePvs.this, "Delete failed", Toast.LENGTH_SHORT).show();
                });
    }

    public void editMember(Pv selectedPv) {
        Intent intent = new Intent(ManagePvs.this, EditPvActivity.class);
        intent.putExtra("id", selectedPv.getId());
        intent.putExtra("title", selectedPv.getTitle());
        intent.putExtra("date", selectedPv.getDate());

        startActivity(intent);
    }
}