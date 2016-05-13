package com.cellebrite.weatherclient.util;

import java.util.HashMap;
import java.util.Map;

public class DataManager {

    public static String getIconSource(String iconId) {
        if (iconId == null || iconId.length() < 1) return null;
        return "http://openweathermap.org/img/w/" + iconId + ".png";
    }

    public static Map<String, String> getWeatherParam() {
        Map<String, String> param = new HashMap<>();
        param.put("q", AppConst.LOCATION);
        param.put("units", AppConst.TYPE_UNITS);
        param.put("appid", AppConst.WEATHER_API_KEY);
        return param;
    }
}