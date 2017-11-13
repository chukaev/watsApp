package com.watsapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watsapp.R;
import com.watsapp.activity.Sessions;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import java.util.List;

public class ArraySwipeAdapterSample<T> extends ArraySwipeAdapter {

    private final Sessions context;
    private final List to_read;

    public ArraySwipeAdapterSample(Context context, int resource, int textViewResourceId, List objects, List to_read) {
        super(context, resource, textViewResourceId, objects);
        this.context = (Sessions) context;
        this.to_read = to_read;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View v = super.getView(position, convertView, parent);

        if (to_read.contains(((TextView) v.findViewById(R.id.text_data)).getText())) {

            v.findViewById(R.id.row).setBackgroundColor(Color.BLUE);
        }
        v.findViewById(R.id.trash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer user = (Integer) getItem(position);
                if (context.deleteSession(user)) {
                    remove(user);
                }
            }
        });
        return v;
    }
}
