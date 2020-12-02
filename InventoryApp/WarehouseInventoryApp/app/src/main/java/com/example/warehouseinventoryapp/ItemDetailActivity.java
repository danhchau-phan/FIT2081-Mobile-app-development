package com.example.warehouseinventoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.warehouseinventoryapp.provider.ItemViewModel;

public class ItemDetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
//    ArrayList<Item> data = new ArrayList<>();
    W7RecyclerAdapter adapter;
    ItemViewModel mItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list_layout);
        mItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
//        String st = getIntent().getExtras().getString("data source");
//
//        Gson gson = new Gson();
//        Type type = new TypeToken<ArrayList<Item>>(){}.getType();
//        data = gson.fromJson(st, type);
//        System.out.println(data.size());

        recyclerView = findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new W7RecyclerAdapter();

        mItemViewModel.getAllItems().observe(this, newData -> {
            adapter.setData(newData);
            adapter.notifyDataSetChanged();
        });
//        adapter.setData(data);

        recyclerView.setAdapter(adapter);
    }

}
