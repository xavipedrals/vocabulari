package com.example.xavivaio.vocabulari.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xavivaio.vocabulari.Paraula;
import com.example.xavivaio.vocabulari.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xavivaio on 12/05/2015.
 */
public class ParaulesAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private List<Paraula> paraulaItems;

        public ParaulesAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.paraulaItems = new ArrayList<Paraula>();
            if (cursor.moveToFirst()) {
                do {
                    Paraula paraula = new Paraula();
                    paraula.setParaula(cursor.getString(cursor.getColumnIndex("name")));
                    paraula.setNumTrad(cursor.getInt(cursor.getColumnIndex("numTrad")));
                    paraulaItems.add(paraula);
                } while (cursor.moveToNext());
        }
    }

    @Override
    public int getCount() {
        return paraulaItems.size();
    }

    @Override
    public Object getItem(int location) {
        return paraulaItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //if (convertView == null)
        convertView = inflater.inflate(R.layout.list_row, null);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

        // getting movie data for the row
        Paraula paraula = paraulaItems.get(position);

        // title
        title.setText(paraula.getParaula());

        // rating
        rating.setText("Nombre de traduccions: " + String.valueOf(paraula.getNumTrad()));

        // release year
        year.setText(String.valueOf(0));

        return convertView;
    }

}
