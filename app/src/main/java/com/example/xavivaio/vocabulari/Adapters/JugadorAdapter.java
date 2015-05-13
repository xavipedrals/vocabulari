package com.example.xavivaio.vocabulari.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xavivaio.vocabulari.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xavivaio on 13/05/2015.
 */
public class JugadorAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Jugador> jugadorItems;

    public JugadorAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.jugadorItems = new ArrayList<Jugador>();
        if (cursor.moveToFirst()) {
            do {
                Jugador jugador = new Jugador();
                jugador.setName(cursor.getString(cursor.getColumnIndex("name")));
                jugador.setPunts(cursor.getInt(cursor.getColumnIndex("punts")));
                jugadorItems.add(jugador);
            } while (cursor.moveToNext());
        }
    }

    public String getNomIdioma(int location){
        return jugadorItems.get(location).getName();
    }

    @Override
    public int getCount() {
        return jugadorItems.size();
    }

    @Override
    public Object getItem(int location) {
        return jugadorItems.get(location);
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
        Jugador jugador = jugadorItems.get(position);

        // title
        title.setText(jugador.getName());

        // rating
        rating.setText("Puntuacio: " + String.valueOf(jugador.getPunts()));

        // release year
        year.setText(String.valueOf(0));

        return convertView;
    }
}
