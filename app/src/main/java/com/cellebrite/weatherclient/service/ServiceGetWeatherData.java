package com.cellebrite.weatherclient.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import com.cellebrite.weatherclient.model.DataItem;
import com.cellebrite.weatherclient.rest.api.GetWeatherApi;
import com.cellebrite.weatherclient.rest.model.WeatherData;
import com.cellebrite.weatherclient.util.AppConst;
import com.cellebrite.weatherclient.util.BusProvider;
import com.cellebrite.weatherclient.util.DataManager;
import com.cellebrite.weatherclient.util.LogTag;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGetWeatherData extends Service {

    private boolean isWork = false;

    private Retrofit retrofit;
    private GetWeatherApi service;
    private Call<WeatherData> call;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isWork) {
            isWork = true;
            retrofit = new Retrofit.Builder().baseUrl(AppConst.BASE_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            service = retrofit.create(GetWeatherApi.class);
            call = service.currentWeather(DataManager.getWeatherParam());

            getData();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getData();
                }
            }, 600 * 1000); // 10 minutes time in milliseconds
        }
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void getData() {
        call.enqueue(new Callback<WeatherData>() {
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
                // update ui if show
                BusProvider.getInstance().post(new Object());
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                LogTag.v("onFailure: " + t.getMessage());
            }
        });
    }
}