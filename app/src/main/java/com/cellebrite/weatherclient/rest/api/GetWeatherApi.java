package com.cellebrite.weatherclient.rest.api;

import com.cellebrite.weatherclient.rest.model.WeatherData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface GetWeatherApi {

    @GET("/data/2.5/weather")
    Call<WeatherData> currentWeather(@QueryMap Map<String, String> param);
}