package com.cellebrite.weatherclient.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cellebrite.weatherclient.R;
import com.cellebrite.weatherclient.adapter.DataAdapter;
import com.cellebrite.weatherclient.model.DataItem;
import com.cellebrite.weatherclient.rest.api.GetWeatherApi;
import com.cellebrite.weatherclient.rest.model.WeatherData;
import com.cellebrite.weatherclient.util.AppConst;
import com.cellebrite.weatherclient.util.DataManager;
import com.cellebrite.weatherclient.util.LogTag;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        getData();
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

    public void getData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(AppConst.BASE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create()).build();
        GetWeatherApi service = retrofit.create(GetWeatherApi.class);
        Call<WeatherData> call1 = service.currentWeather(DataManager.getWeatherParam());
        call1.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                LogTag.v("onResponse: " + response.body().getWeather().get(0).getDescription());
                DataItem di = new DataItem();
                di.weatherDescription = response.body().getWeather().get(0).getDescription();
                di.iconSource = DataManager.getIconSource(response.body()
                        .getWeather().get(0).getIcon());
                di.temperature = response.body().getMain().getTemp().intValue();
                di.humidity = response.body().getMain().getHumidity();
                di.dt = response.body().getDt();
                di.save();
                upDateRv();
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                LogTag.v("onFailure: " + t.getMessage());
            }
        });
    }
}
