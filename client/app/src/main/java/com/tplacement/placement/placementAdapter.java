package com.tplacement.placement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tplacement.R;

import java.util.Collections;
import java.util.List;

public class placementAdapter extends RecyclerView.Adapter<placementAdapter.ViewHolder> {
    Context context;

    private List<placementData> list = Collections.emptyList();
    private ViewHolder holder;
    private int position;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private TextView desc, date;
        LinearLayout placement_container_box;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.desc);
            date = itemView.findViewById(R.id.date);
            placement_container_box = itemView.findViewById(R.id.placement_container_box);
            view = itemView;
        }


    }

    public placementAdapter(List<placementData> DataSet, Context context) {
        this.list = DataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.placement_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        this.holder = holder;
        this.position = position;
        holder.name.setText(list.get(position).getName());
        holder.desc.setText(list.get(position).getDesc());
        holder.date.setText(list.get(position).getDate());

        holder.placement_container_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Intent placement = new Intent(context.getApplicationContext(), PlacementActivity.class);

                placement.putExtra("name", list.get(position).getName());
                placement.putExtra("desc", list.get(position).getDesc());
                placement.putExtra("date", list.get(position).getDate());
                placement.putExtra("org", list.get(position).getOrg());
                placement.putExtra("org_location", list.get(position).getOrg_location());
                placement.putExtra("org_desc", list.get(position).getOrg_desc());
                placement.putExtra("roles", list.get(position).getRoles());
                placement.putExtra("hr", list.get(position).getHr());
                placement.putExtra("skills", list.get(position).getSkills());
                placement.putExtra("education", list.get(position).getEducation());
                placement.putExtra("experience", list.get(position).getExperience());
                placement.putExtra("id", list.get(position).getId());
                activity.startActivity(placement);


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
