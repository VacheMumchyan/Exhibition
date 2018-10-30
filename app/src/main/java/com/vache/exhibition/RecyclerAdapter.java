package com.vache.exhibition;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private List<Exhibit> exhibitList = new ArrayList<>();
    private Context context;

    public RecyclerAdapter(Context context) {
        this.context = context;
        setHasStableIds(true);
    }

    public void putExhibits(List<Exhibit> items) {
        if (!exhibitList.equals(items)) {
            exhibitList = items;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exhibit_item, parent, false);
        RecyclerAdapter.RecyclerViewHolder vh = new RecyclerAdapter.RecyclerViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapter.RecyclerViewHolder holder, final int position) {
        holder.title.setText(exhibitList.get(position).getTitle());
        RecyclerView recyclerView = holder.recyclerView;
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new ItemDecoration(20, exhibitList.size(), false));
        final ItemRecyclerAdapter adapter = new ItemRecyclerAdapter(context);
        recyclerView.setAdapter(adapter);
        adapter.putImages(exhibitList.get(position).getImages());
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return exhibitList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        RecyclerView recyclerView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            recyclerView = itemView.findViewById(R.id.item_recyclerView);
        }
    }

}

