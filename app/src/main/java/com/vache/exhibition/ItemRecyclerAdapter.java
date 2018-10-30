package com.vache.exhibition;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.RecyclerViewHolder> {

    private List<String> images = new ArrayList<>();
    private Context context;

    public ItemRecyclerAdapter(Context context) {
        this.context = context;
        setHasStableIds(true);
    }

    public void putImages(List<String> items) {
        if (!images.equals(items)) {
            images = items;
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public ItemRecyclerAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exhibit_image_item, parent, false);
        ItemRecyclerAdapter.RecyclerViewHolder vh = new ItemRecyclerAdapter.RecyclerViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRecyclerAdapter.RecyclerViewHolder holder, final int position) {
        Glide.with(context)
                .load(images.get(position))
                .into(holder.image);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }

}


