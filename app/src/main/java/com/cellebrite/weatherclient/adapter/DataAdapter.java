package com.cellebrite.weatherclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cellebrite.weatherclient.R;
import com.cellebrite.weatherclient.model.DataItem;
import com.cellebrite.weatherclient.util.AppConst;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private List<DataItem> itemList;
    private Context context;

    public DataAdapter(List<DataItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_weather_view, viewGroup, false);
        context = v.getContext();
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        final DataItem item = itemList.get(i);
        String temperature = String.valueOf(item.temperature) + AppConst.DEGREE_CELCIUS;
        String humidity = String.valueOf(item.humidity) + AppConst.PERCENT;
        myViewHolder.degreeTv.setText(temperature);
        myViewHolder.humidityTv.setText(humidity);
        myViewHolder.descriptionTv.setText(item.weatherDescription);
        Picasso.with(context).load(item.iconSource)
                .placeholder(R.drawable.def)
                .error(R.drawable.def).into(myViewHolder.iconIv);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView degreeTv, humidityTv, descriptionTv;
        ImageView iconIv;

        public MyViewHolder(View view) {
            super(view);
            degreeTv = (TextView) view.findViewById(R.id.degreeTv);
            humidityTv = (TextView) view.findViewById(R.id.humidityTv);
            descriptionTv = (TextView) view.findViewById(R.id.descriptionTv);
            iconIv = (ImageView) view.findViewById(R.id.iconIv);
        }
    }
}