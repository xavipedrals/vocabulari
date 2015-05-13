package com.example.xavivaio.vocabulari.GestionaParaula;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.xavivaio.vocabulari.Adapters.IdiomesAdapter;
import com.example.xavivaio.vocabulari.Adapters.TraduccionsAdapter;
import com.example.xavivaio.vocabulari.CreaTraduccioActivity;
import com.example.xavivaio.vocabulari.Dades.GestorBD;
import com.example.xavivaio.vocabulari.R;
import com.melnykov.fab.FloatingActionButton;

/**
 * A placeholder fragment containing a simple view.
 */
public class GestionaParaulaActivityFragment extends Fragment {

    public GestionaParaulaActivityFragment() {
    }

    ListView listView;
    FloatingActionButton fab;
    TraduccionsAdapter traduccionsAdapter;
    Cursor c;
    String idioma, paraula;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gestiona_paraula, container, false);

        Bundle b = getArguments();
        //boolean refresh = false;
        if (b != null) {
            //refresh = b.getBoolean("refresh", false);
            paraula = b.getString("paraula", paraula);
            idioma = b.getString("idioma", idioma);
        }
        getActivity().setTitle(idioma + ": " + paraula);

        listView = (ListView) rootView.findViewById(android.R.id.list);
        Log.d("HOLA", "Hola colega");
        getData();

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.attachToListView(listView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("idioma", idioma);
                b.putString("paraula", paraula);
                Intent intent = new Intent(getActivity(), CreaTraduccioActivity.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

//        if (refresh){
//            listView.post(new Runnable() {
//                @Override
//                public void run() {
//                    listView.setSelection(idiomesAdapter.getCount()-1);
//                }
//            });
//        }
        return rootView;

    }

    public void getData() {
        c = new GestorBD(getActivity().getApplicationContext()).getTaulesTraduccio(idioma);
        traduccionsAdapter = new TraduccionsAdapter(getActivity().getApplicationContext(), paraula);
        if (c.moveToFirst()) {
            do {
                String idioma1 = c.getString(c.getColumnIndex(GestorBD.TRADUCCIONS_COLUMN_IDIOMA1));
                String idioma2 = c.getString(c.getColumnIndex(GestorBD.TRADUCCIONS_COLUMN_IDIOMA2));
                String nomTaula = idioma1+idioma2;
                Log.d("CURSOR", "Taula traduccio control -> "+ nomTaula);
                Cursor aux = new GestorBD(getActivity().getApplicationContext()).getParaulesTraduccio(nomTaula, paraula);
                traduccionsAdapter.addTraduccions(aux, nomTaula);
            } while (c.moveToNext());
        }

        listView.setAdapter(traduccionsAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Bundle b = new Bundle();
//                b.putString("idioma", idiomesAdapter.getNomIdioma(position));
//                Intent intent = new Intent(getActivity().getApplicationContext(), GestionaUnIdioma.class);
//                intent.putExtras(b);
//                getActivity().startActivity(intent);
//            }
//        });
        traduccionsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
}
