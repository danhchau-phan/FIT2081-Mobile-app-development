package com.example.warehouseinventoryapp.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("select * from items")
    LiveData<List<Item>> getAllItems();

    @Insert
    void addItem(Item item);

    @Query("delete from items")
    void deleteAllItems();
}
