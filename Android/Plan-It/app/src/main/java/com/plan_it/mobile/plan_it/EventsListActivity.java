package com.plan_it.mobile.plan_it;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class EventsListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    public View view;
    private List<Event> mEvents;
    private EventsListAdapter adapter;
    private RecyclerView events_recycler_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeData();
        adapter = new EventsListAdapter(this, mEvents);
        setContentView(R.layout.activity_events_list);
        events_recycler_view = (RecyclerView)findViewById(R.id.events_list_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        events_recycler_view.setLayoutManager(llm);
        events_recycler_view.setAdapter(adapter);
        mEvents = new ArrayList<>();
    }
    public void initializeData(){
        mEvents = new ArrayList<>();
        try {
            getEventsList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_events_list, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onQueryTextChange(String query) {

        List<Event> filteredModelList;
        if (query == "INVITED" || query == "ATTENDING" || query == "DECLINED" || query == "OWNER" || query == "LEFT")
        {
            filteredModelList = new ArrayList<>();
            for (Event event : mEvents) {
                final String text = event.isAttending.toString();
                if (text.contains(query)) {
                    filteredModelList.add(event);
                }
            }
            adapter.animateTo(filteredModelList);
            events_recycler_view.scrollToPosition(0);

        } else if (query == "NONE") {
            filteredModelList = new ArrayList<>();
            for (Event event : mEvents)
            {
                filteredModelList.add(event);
            }
            adapter.animateTo(mEvents);
            events_recycler_view.scrollToPosition(0);
        }
        else{
            filteredModelList = filter(mEvents, query);
            adapter.animateTo(filteredModelList);
            events_recycler_view.scrollToPosition(0);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    private List<Event> filter(List<Event> events, String query) {

        query = query.toLowerCase();

            final List<Event> filteredModelList = new ArrayList<>();
            for (Event event : events) {
                final String text = event.name.toLowerCase();
                if (text.contains(query)) {
                    filteredModelList.add(event);
                    Log.d("event filtered", "name " + event.name + "owner " + event.owner + "description " + event.description + "photoId " + event.photoId);
                }
            }
           return filteredModelList;
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
        if (id == R.id.action_filter)
        {
            FilterEvents();
        }
        if (id == R.id.action_friendsList)
        {
            Intent intent = new Intent(this, FriendsListActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_create_new_event)
        {
            navCreateNewEvent(view);
        }
        if(id == R.id.action_item_list)
        {
            Intent intent = new Intent(this, ItemListActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void FilterEvents()
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.menu_filter_list);
        dialog.show();
        Button invited = (Button)dialog.findViewById(R.id.filterButtonInvited);
        Button attending = (Button)dialog.findViewById(R.id.filterButtonAttending);
        Button declined = (Button)dialog.findViewById(R.id.filterButtonDeclined);
        Button owner = (Button)dialog.findViewById(R.id.filterButtonOwner);
        Button showAll = (Button)dialog.findViewById(R.id.filterButtonAll);
        Button past = (Button)dialog.findViewById(R.id.filterButtonPast);

        invited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventsListAdapter.filter = "INVITED";
                Log.d("Filter: ", EventsListAdapter.filter);
                onQueryTextChange("INVITED");
                dialog.dismiss();
            }
        });
        attending.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EventsListAdapter.filter = "ATTENDING";
            Log.d("Filter: ", EventsListAdapter.filter);
            onQueryTextChange("ATTENDING");
            dialog.dismiss();
        }
    });
        declined.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EventsListAdapter.filter = "DECLINED";
            Log.d("Filter: ", EventsListAdapter.filter);
            onQueryTextChange("DECLINED");
            dialog.dismiss();
        }
    });
        past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventsListAdapter.filter = "LEFT";
                Log.d("Filter: ", EventsListAdapter.filter);
                onQueryTextChange("LEFT");
                dialog.dismiss();
            }
        });
        owner.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EventsListAdapter.filter = "OWNER";
            onQueryTextChange("OWNER");
            Log.d("Filter: ", EventsListAdapter.filter);
            dialog.dismiss();
        }
    });
        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventsListAdapter.filter = "NONE";
                onQueryTextChange("NONE");
                Log.d("Filter: ", EventsListAdapter.filter);
                dialog.dismiss();
            }
        });
    }
    private Bitmap base64ToBitmap(String b64){
        byte[] imageAsBytes = Base64.decode(b64.getBytes(),Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
    public void getEventsList() throws JSONException {
        RestClient.get("events", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Toast.makeText(getApplicationContext(), "HIT 1", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray eventsList) {
                // Pull out the first event on the public timeline
                JSONObject firstEvent = null;
                try {
                    for(int i = 0; i < eventsList.length(); i++){
                        firstEvent = eventsList.getJSONObject(i);
                        String eventImge = firstEvent.getString("picture");
                        Bitmap eventimg = base64ToBitmap(eventImge);
                        Toast.makeText(getApplicationContext(), Integer.toString(i), Toast.LENGTH_LONG).show();
                        mEvents.add(new Event(firstEvent.getString("what"), "Kevin Murphy", firstEvent.getString("why"),firstEvent.getString("where"), eventimg, firstEvent.getString("when"),firstEvent.getString("endDate"),"2:00 PM","3:00 PM", IsAttending.INVITED, true, true));
                        adapter.animateTo(mEvents);
                        Log.d("RestD", firstEvent.toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void navCreateNewEvent(View v)
    {
        Intent intent = new Intent(this, CreateEventActivity.class);
        startActivity(intent);
    }
}
