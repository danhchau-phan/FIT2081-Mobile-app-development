package com.example.warehouseinventoryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.warehouseinventoryapp.provider.Item;
import com.example.warehouseinventoryapp.provider.ItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity {

    private EditText itemName;
    private EditText quantity;
    private EditText cost;
    private EditText description;
    private ToggleButton frozenItem;

    private ItemViewModel mViewModel;

    private View contraintLayout;
    float downX, downY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        final FloatingActionButton fabBtn = findViewById(R.id.fab);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mViewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) menuItem -> {
            int item = menuItem.getItemId();

            if (item == R.id.nav_add_item) {
                onClickAddNewItem((Button) findViewById(R.id.AddNewItemBtn));
            }
            if (item == R.id.nav_clear_fields) {
                onClickClearAllItem((Button) findViewById(R.id.ClearAllItemBtn));
            }
            if (item == R.id.nav_list_all_items) {
                Context context = getApplicationContext();
                Intent intent = new Intent(context, ItemDetailActivity.class);
                startActivity(intent);
            }
            drawerLayout.closeDrawers();
            return true;
        });

        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = ((EditText) findViewById(R.id.itemNamePt)).getText().toString();
                CharSequence text = String.format("%s has been added", (itemName.equals("") ? "No item" : "New item ("+itemName+")"));
                Snackbar.make(v, text, Snackbar.LENGTH_LONG).show();
                onClickAddNewItem(v);
            }
        });
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);

        itemName = (EditText) findViewById(R.id.itemNamePt);
        quantity = (EditText) findViewById(R.id.QuantityPt);
        cost = (EditText) findViewById(R.id.CostPt);
        description = (EditText) findViewById(R.id.DescriptionPt);
        frozenItem = (ToggleButton) findViewById(R.id.FrozenItemBtn);

        registerReceiver(new WarehouseSMSReceiver(), new IntentFilter(SMSReceiver.SMS_FILTER));

        contraintLayout = findViewById(R.id.constraint_layout);

        contraintLayout.setOnTouchListener((v, event) -> onSwipe(v, event));

    }

    private boolean onSwipe(View v, MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                downX = event.getRawX();
                downY = event.getRawY();
                return true;
            case (MotionEvent.ACTION_MOVE):
                return true;
            case (MotionEvent.ACTION_UP):
                float upX = event.getRawX();
                if (Math.abs(downY - event.getRawY()) < 60){
                    if (upX - downX > 0) {
                        onClickAddNewItem(v);
                    } else if (upX - downX < 0) {
                        onClickClearAllItem(v);
                    }
                    return true;
                } else
                    return false;
            default:
                return false;
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.option_add_item) {
            onClickAddNewItem((Button) findViewById(R.id.AddNewItemBtn));
        }
        if (id == R.id.option_clear_item) {
            onClickClearAllItem((Button) findViewById(R.id.ClearAllItemBtn));
        }
        return true;
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
    }

    protected void onStart() {
        super.onStart();
        loadItem();
    }

    protected void onStop() {
        super.onStop();
    }

    private void loadItem() {
        SharedPreferences sP = getApplicationContext().getSharedPreferences("Last Item", MODE_PRIVATE);
        itemName.setText(sP.getString("Item Name", ""));
        quantity.setText(sP.getString("Quantity", "0"));
        cost.setText(sP.getString("Cost", "0.0"));
        description.setText(sP.getString("Description", ""));
        frozenItem.setChecked(sP.getBoolean("Frozen Item", false));
    }

    private void saveItem() {
        SharedPreferences sP = getApplicationContext().getSharedPreferences("Last Item", MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        editor.putString("Item Name", itemName.getText().toString());
        editor.putString("Cost", cost.getText().toString());
        editor.putString("Quantity", quantity.getText().toString());
        editor.putString("Description", description.getText().toString());
        editor.putBoolean("Frozen Item", frozenItem.isChecked());
        editor.commit();
    }

    public void onClickAddNewItem(View view) {
        String itemName = ((EditText) findViewById(R.id.itemNamePt)).getText().toString();
        CharSequence text = String.format("%s has been added", (itemName.equals("") ? "No item" : "New item ("+itemName+")"));

        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();

        saveItem();

        int quantity = Integer.parseInt(((EditText) findViewById(R.id.QuantityPt)).getText().toString());
        double cost = Double.parseDouble(((EditText) findViewById(R.id.CostPt)).getText().toString());
        String description = ((EditText) findViewById(R.id.DescriptionPt)).getText().toString();
        boolean frozen = ((ToggleButton) findViewById(R.id.FrozenItemBtn)).isChecked();
        Item item = new Item(itemName, quantity, cost, description,
                frozen);
        mViewModel.insert(item);
    }

    public void onClickClearAllItem(View view) {
        itemName.getText().clear();
        quantity.setText("0");
        cost.setText("0.0");
        description.getText().clear();
        frozenItem.setChecked(false);
        Toast toast = Toast.makeText(getApplicationContext(), "All fields are cleared", Toast.LENGTH_SHORT);
        toast.show();

        mViewModel.deleteAll();
    }
    class WarehouseSMSReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            StringTokenizer sT = new StringTokenizer(message, ";");

            itemName.setText(sT.nextToken());
            quantity.setText(sT.nextToken());
            cost.setText(sT.nextToken());
            description.setText(sT.nextToken());
            frozenItem.setChecked(Boolean.parseBoolean(sT.nextToken()));
        }
    }


}
