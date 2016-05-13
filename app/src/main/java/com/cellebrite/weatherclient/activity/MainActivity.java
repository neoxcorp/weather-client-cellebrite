package com.cellebrite.weatherclient.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cellebrite.weatherclient.R;
import com.cellebrite.weatherclient.adapter.DataAdapter;
import com.cellebrite.weatherclient.model.DataItem;
import com.cellebrite.weatherclient.service.ServiceGetWeatherData;
import com.cellebrite.weatherclient.util.BusProvider;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_main;

    private List<DataItem> dataItems = new ArrayList<>();

    private RecyclerView weatherHistoryRv;
    private DataAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        init();
        Intent service = new Intent(MainActivity.this, ServiceGetWeatherData.class);
        startService(service);
    }

    private void init() {
        dataItems.addAll(DataItem.getAllData());

        weatherHistoryRv = (RecyclerView) findViewById(R.id.weatherHistoryRv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        weatherHistoryRv.setLayoutManager(gridLayoutManager);
        dataAdapter = new DataAdapter(dataItems);
        weatherHistoryRv.setAdapter(dataAdapter);
    }

    private void upDateRv() {
        dataItems.clear();
        dataItems.addAll(DataItem.getAllData());
        dataAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onStop() {
        BusProvider.getInstance().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void neeedUpdate(Object o) {
        upDateRv();
    }
}