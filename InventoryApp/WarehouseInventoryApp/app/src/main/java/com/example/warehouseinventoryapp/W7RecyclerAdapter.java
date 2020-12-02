package com.example.warehouseinventoryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouseinventoryapp.provider.Item;

import java.util.ArrayList;
import java.util.List;

public class W7RecyclerAdapter extends RecyclerView.Adapter<W7RecyclerAdapter.ViewHolder> {
    List<Item> data = new ArrayList<>();

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemName.setText(data.get(position).getItemName());
        holder.quantity.setText(Integer.toString(data.get(position).getQuantity()));
        holder.cost.setText(Double.toString(data.get(position).getCost()));
        holder.description.setText(data.get(position).getDescription());
        holder.frozen.setText(Boolean.toString(data.get(position).getFrozenItem()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Item> data) { this.data = data;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView quantity;
        TextView cost;
        TextView description;
        TextView frozen;
        public ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemNameCard);
            quantity = itemView.findViewById(R.id.quantityCard);
            cost = itemView.findViewById(R.id.costCard);
            description = itemView.findViewById(R.id.descriptionCard);
            frozen = itemView.findViewById(R.id.frozenCard);
        }
    }
}
