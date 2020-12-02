package com.example.warehouseinventoryapp.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class Item {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int itemId;

    @ColumnInfo(name = "item name")
    private String itemName;

    @ColumnInfo(name = "quantity")
    private int quantity;

    @ColumnInfo(name = "cost")
    private double cost;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "frozen item")
    private boolean frozenItem;

    public Item(String itemName, int quantity, double cost, String description, boolean frozenItem) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.cost = cost;
        this.description = description;
        this.frozenItem  = frozenItem;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public boolean getFrozenItem() {
        return frozenItem;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
