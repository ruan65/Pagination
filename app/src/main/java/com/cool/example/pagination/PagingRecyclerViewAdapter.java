package com.cool.example.pagination;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cool.example.pagination.data.Item;

import java.util.ArrayList;
import java.util.List;

public class PagingRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int MAIN_VIEW = 0;

    private List<Item> elements = new ArrayList<>();

    private boolean allItemsLoaded;

    static class MainViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MainViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public void addNewItems(List<Item> items) {

        if (items.size() == 0) {
            allItemsLoaded = true;
            return;
        }
        elements.addAll(items);
    }

    public boolean areAllItemsLoaded() {
        return allItemsLoaded;
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    public Item getItem(int position) {
        return elements.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == MAIN_VIEW) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
            return new MainViewHolder(v);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return MAIN_VIEW;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == MAIN_VIEW) {

            onBindTextHolder(holder, position);
        }
    }

    private void onBindTextHolder(RecyclerView.ViewHolder holder, int position) {

        MainViewHolder mainViewHolder = (MainViewHolder) holder;
        mainViewHolder.textView.setText(getItem(position).getItemStr());
    }

    @Override
    public int getItemCount() {
        return elements == null ? 0 : elements.size();
    }
}
