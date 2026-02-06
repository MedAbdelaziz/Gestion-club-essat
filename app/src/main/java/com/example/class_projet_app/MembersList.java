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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MembersList extends AppCompatActivity {

    Button add;
    ListView memList;
    ArrayList<Member> members = new ArrayList<>();
    ArrayList<String> usernames = new ArrayList<>();

    // 1. Adapter must be global to be accessed in delete/edit methods
    ArrayAdapter<String> adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_list);
        add = findViewById(R.id.addBtn);

        memList = findViewById(R.id.membersList);
        registerForContextMenu(memList);

        adapter = new ArrayAdapter<>(MembersList.this, android.R.layout.simple_list_item_1, usernames);
        memList.setAdapter(adapter);

        fetchMembers();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MembersList.this, AddMemberActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchMembers() {
        members.clear();
        usernames.clear();

        db.collection("Members").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        members.add(new Member(
                                doc.getId(),
                                doc.getString("username"),
                                doc.getString("email"),
                                doc.getString("password"),
                                doc.getString("phone")
                        ));
                        usernames.add(doc.getString("username"));
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MembersList.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.members_list_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (info == null) return super.onContextItemSelected(item);

        // Get the specific member clicked
        Member selectedMember = members.get(info.position);
        int listPosition = info.position;

        if (item.getItemId() == R.id.deleteBtn) {
            deleteMember(selectedMember, listPosition);
            return true;
        } else if (item.getItemId() == R.id.editBtn) {
            editMember(selectedMember);
            return true;
        }
        return false;
    }

    public void deleteMember(Member selectedMember, int position) {
        // 3. FIX: Collection name was "members", changed to "Members" (Case Sensitive!)
        db.collection("Members").document(selectedMember.getId()).delete()
                .addOnSuccessListener(a -> {
                    // 4. Update the UI Lists
                    members.remove(selectedMember);
                    usernames.remove(position);
                    adapter.notifyDataSetChanged();

                    Toast.makeText(MembersList.this, "Deleted successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MembersList.this, "Delete failed", Toast.LENGTH_SHORT).show();
                });
    }

    public void editMember(Member selectedMember) {
        Intent intent = new Intent(MembersList.this, EditMemberActivity.class);
        intent.putExtra("id", selectedMember.getId());
        intent.putExtra("username", selectedMember.getName());
        intent.putExtra("email", selectedMember.getEmail());
        intent.putExtra("password",selectedMember.getPassword());
        intent.putExtra("phone", selectedMember.getPhone());
        startActivity(intent);
    }
}