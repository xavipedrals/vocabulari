package com.example.xavivaio.vocabulari.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xavivaio.vocabulari.Dades.GestorBD;
import com.example.xavivaio.vocabulari.R;
import com.example.xavivaio.vocabulari.Traduccio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xavivaio on 13/05/2015.
 */
public class TraduccionsAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Traduccio> traduccioItems;
    private String paraula;

    public TraduccionsAdapter(Context context, String paraula) {
        this.context = context;
        this.paraula = paraula;
        this.traduccioItems = new ArrayList<Traduccio>();
    }

    public void addTraduccions(Cursor c, String nomTaula){
        if (c.moveToFirst()) {
            do {
                Traduccio t = new Traduccio();
                t.setNomTaula(nomTaula);
                t.setParaula1(c.getString(c.getColumnIndex(GestorBD.TRADUCCIO_COLUMN_PARAULA1)));
                t.setParaula2(c.getString(c.getColumnIndex(GestorBD.TRADUCCIO_COLUMN_PARAULA2)));
                traduccioItems.add(t);
            } while (c.moveToNext());
        }
    }

    @Override
    public int getCount() {
        return  traduccioItems.size();
    }

    @Override
    public Object getItem(int location) {
        return  traduccioItems.get(location);
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
        Traduccio t =  traduccioItems.get(position);

        // title
        if (!paraula.equals(t.getParaula1())){
            title.setText(t.getParaula1());
        } else
        title.setText(t.getParaula2());

        // rating
        rating.setText("fdsfasdfasdfasdfa");

        // release year
        year.setText(String.valueOf(0));

        return convertView;
    }

}