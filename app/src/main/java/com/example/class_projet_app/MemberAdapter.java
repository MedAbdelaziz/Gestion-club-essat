package com.example.class_projet_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.class_projet_app.Member;
import com.example.class_projet_app.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MemberAdapter extends ArrayAdapter<Member> {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MemberAdapter(Context context, List<Member> members) {
        super(context, 0, members);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Member member = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_member_item, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.memberName);
        Button btnDelete = convertView.findViewById(R.id.deleteBtn);

        tvName.setText(member.getName());

        // Firestore Delete Logic
        btnDelete.setOnClickListener(v -> {
            db.collection("members").document(member.getId())
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        remove(member); // Remove from local list
                        notifyDataSetChanged(); // Refresh UI
                        Toast.makeText(getContext(), "Deleted!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Error deleting", Toast.LENGTH_SHORT).show();
                    });
        });

        return convertView;
    }
}