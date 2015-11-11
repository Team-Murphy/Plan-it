package com.plan_it.mobile.plan_it;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity {
    private int split = 0;
    private List<Item> mItems;
    private ItemListAdapter adapter;
    private RecyclerView items_recycler_view;

    void initializeData()
    {
        mItems = new ArrayList<>();
        mItems.add(new Item(1, "Tacos", "Kevin Murphy", 5.00, 10.50));
        mItems.add(new Item(2, "Cheese", "Amina", 10.00, 9.50));
        mItems.add(new Item(3, "Tent", "Kevin Murphy", 200, 1));
        mItems.add(new Item(4, "Sour Cream", "Mohammed", 5.00, 4.75));
        mItems.add(new Item(5, "Stereo", "Kristian", 100, 1));
        mItems.add(new Item(6, "Lettuce", "Luke", 3.00, 4.00));
        mItems.add(new Item(7, "Vodka", "Mohammed", 60.00, 57.50));
        mItems.add(new Item(8, "Beer", "Kamran", 100.00, 96.50));
        mItems.add(new Item(9, "Peppers", "Joanne", 7.50, 10.00));
        mItems.add(new Item(10, "Beef", "", 40.00, 1));
        mItems.add(new Item(11, "Tofu", "", 20.00, 1));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeData();
        adapter = new ItemListAdapter(this, mItems);
        setContentView(R.layout.activity_item_list);
        items_recycler_view = (RecyclerView)findViewById(R.id.item_list_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        items_recycler_view.setLayoutManager(llm);
        items_recycler_view.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void splitChanged(View v)
    {

    }
}
