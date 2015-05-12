package com.example.xavivaio.vocabulari.Adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xavivaio.vocabulari.Dades.GestorBD;
import com.example.xavivaio.vocabulari.Idioma;
import com.example.xavivaio.vocabulari.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xavivaio on 12/05/2015.
 */
public class IdiomesAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Idioma> idiomaItems;

    public IdiomesAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.idiomaItems = new ArrayList<Idioma>();
        if (cursor.moveToFirst()) {
                do {
                    Idioma idioma = new Idioma();
                    idioma.setName(cursor.getString(cursor.getColumnIndex(GestorBD.IDIOMES_COLUMN_NAME)));
                    idioma.setNumPar(cursor.getInt(cursor.getColumnIndex(GestorBD.IDIOMES_COLUMN_NUMPAR)));
                    idioma.setNumTrad(cursor.getInt(cursor.getColumnIndex(GestorBD.IDIOMES_COLUMN_NUMTRAD)));
                    idiomaItems.add(idioma);
                } while (cursor.moveToNext());
        }
    }

    public void addIdioma(Idioma idioma){
        idiomaItems.add(idioma);
    }

    public String getNomIdioma(int location){
        return idiomaItems.get(location).getName();
    }

    @Override
    public int getCount() {
        return idiomaItems.size();
    }

    @Override
    public Object getItem(int location) {
        return idiomaItems.get(location);
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
        Idioma idioma = idiomaItems.get(position);

        // title
        title.setText(idioma.getName());

        // rating
        rating.setText("Nombre de paraules: " + String.valueOf(idioma.getNumPar()));

        // release year
        year.setText(String.valueOf(idioma.getNumTrad()));

        return convertView;
    }

}
