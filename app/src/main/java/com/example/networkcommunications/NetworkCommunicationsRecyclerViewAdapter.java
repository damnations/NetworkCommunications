package com.example.networkcommunications;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NetworkCommunicationsRecyclerViewAdapter extends RecyclerView.Adapter<NetworkCommunicationsRecyclerViewAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<ImageList> modalList;

    public NetworkCommunicationsRecyclerViewAdapter(Context context, ArrayList<ImageList> modalList) {
        this.modalList = modalList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.picture_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NetworkCommunicationsRecyclerViewAdapter.ViewHolder holder, int position) {
        ImageList modalListItem = modalList.get(position);
        holder.imageView.setImageBitmap(modalListItem.image);

        holder.deleteButton.setOnClickListener((View view) ->{
            Log.d("Remove Item", "onBindViewHolder: delete");
            modalList.remove(holder.getAdapterPosition());
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return  modalList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private Button deleteButton;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            deleteButton = view.findViewById(R.id.deleteButton);

        }
    }
}
