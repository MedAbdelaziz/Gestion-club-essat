package com.example.class_projet_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class PvAdapter extends RecyclerView.Adapter<PvAdapter.PvViewHolder> {

    ArrayList<Pv> pvList;

    public PvAdapter(ArrayList<Pv> pvList) {
        this.pvList = pvList;
    }

    @NonNull
    @Override
    public PvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pv, parent, false);
        return new PvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PvViewHolder holder, int position) {
        Pv pv = pvList.get(position);
        holder.title.setText(pv.getTitle());
        holder.date.setText("📅 " + pv.getDate());
    }

    @Override
    public int getItemCount() {
        return pvList.size();
    }

    static class PvViewHolder extends RecyclerView.ViewHolder {

        TextView title, date;

        public PvViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.pvTitle);
            date = itemView.findViewById(R.id.pvDate);
        }
    }
}
