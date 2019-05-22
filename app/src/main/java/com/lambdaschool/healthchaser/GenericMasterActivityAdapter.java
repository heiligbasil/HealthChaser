package com.lambdaschool.healthchaser;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GenericMasterActivityAdapter extends RecyclerView.Adapter<GenericMasterActivityAdapter.ViewHolder> {
    private ArrayList<String> stringArrayList;

    public GenericMasterActivityAdapter(ArrayList<String> stringArrayList) {
        this.stringArrayList = stringArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_element, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GenericMasterActivityAdapter.ViewHolder viewHolder, int i) {
        String entryText = stringArrayList.get(i);
        viewHolder.textView.setText(entryText);
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        View viewParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recycler_view_text_view);
            viewParent = itemView.findViewById(R.id.recycler_view_parent);
        }
    }
}
