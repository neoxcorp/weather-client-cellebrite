package com.cellebrite.weatherclient.listener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnItemClickListener implements RecyclerView.OnItemTouchListener {

    private MyOnItemClickListener myOnItemClickListener;
    private GestureDetector gestureDetector;

    public interface MyOnItemClickListener {
        public void OnItemClick(View view, int position);
    }

    public OnItemClickListener(Context context, MyOnItemClickListener myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;
        this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View chView = rv.findChildViewUnder(e.getX(), e.getY());
        if (chView != null && myOnItemClickListener != null && gestureDetector.onTouchEvent(e)) {
            // myOnItemClickListener.OnItemClick(chView, rv.getChildLayoutPosition(chView));
            myOnItemClickListener.OnItemClick(chView, rv.getChildAdapterPosition(chView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
}