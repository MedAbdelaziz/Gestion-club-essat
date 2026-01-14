package com.example.class_projet_app;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingViewHolder> {

    ArrayList<Meeting> meetingList;

    public MeetingAdapter(ArrayList<Meeting> meetingList) {
        this.meetingList = meetingList;
    }

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meeting, parent, false);
        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
        Meeting meeting = meetingList.get(position);

        holder.title.setText(meeting.getTitle());
        holder.date.setText("📅 " + meeting.getDate());
        holder.location.setText("📍 " + meeting.getLocation());
        holder.description.setText(meeting.getDescription());
        holder.status.setText(meeting.getStatus());

        // Creative status color
        if (meeting.getStatus().equals("Upcoming")) {
            holder.status.setBackgroundColor(Color.parseColor("#4CAF50"));
        } else {
            holder.status.setBackgroundColor(Color.parseColor("#9E9E9E"));
        }
    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }

    static class MeetingViewHolder extends RecyclerView.ViewHolder {

        TextView title, date, location, description, status;

        public MeetingViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.meetingTitle);
            date = itemView.findViewById(R.id.meetingDate);
            location = itemView.findViewById(R.id.meetingLocation);
            description = itemView.findViewById(R.id.meetingDescription);
            status = itemView.findViewById(R.id.meetingStatus);
        }
    }
}
