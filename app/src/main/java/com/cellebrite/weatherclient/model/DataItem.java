package com.cellebrite.weatherclient.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.cellebrite.weatherclient.util.DataManager;

import java.util.List;

@Table(name = "DataItems", id = "_id")
public class DataItem extends Model {

    @Column public String weatherDescription, iconSource;
    @Column public int temperature, humidity, dt;

    public DataItem() {
        super();
    }

    public DataItem(String weatherDescription, String iconId, int temperature, int humidity,
                    int dt) {
        super();
        this.weatherDescription = weatherDescription;
        this.iconSource = DataManager.getIconSource(iconId);
        this.temperature = temperature;
        this.humidity = humidity;
        this.dt = dt;
    }

    public static List<DataItem> getAllData() {
        return new Select()
                .from(DataItem.class)
                //.orderBy("dt" + " ASC")
                .orderBy("dt" + " DESC")
                .execute();
    }
}