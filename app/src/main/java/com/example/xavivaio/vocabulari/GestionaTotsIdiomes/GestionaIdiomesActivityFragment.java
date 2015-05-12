package com.example.xavivaio.vocabulari.GestionaTotsIdiomes;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.xavivaio.vocabulari.Adapters.IdiomesAdapter;
import com.example.xavivaio.vocabulari.GestionaIdioma.GestionaUnIdioma;
import com.example.xavivaio.vocabulari.Dades.GestorBD;
import com.example.xavivaio.vocabulari.R;
import com.melnykov.fab.FloatingActionButton;


/**
 * A placeholder fragment containing a simple view.
 */
public class GestionaIdiomesActivityFragment extends Fragment {

    ListView listView;
    FloatingActionButton fab;
    IdiomesAdapter idiomesAdapter;
    Cursor c;

    public GestionaIdiomesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gestiona_idiomes, container, false);

        Bundle b = getArguments();
        boolean refresh = false;
        if (b != null) {
            refresh = b.getBoolean("refresh", false);
        }

        listView = (ListView) rootView.findViewById(android.R.id.list);
        getData();

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.attachToListView(listView);
        fab.setColorNormal(getResources().getColor(R.color.red_baisc));
        fab.setColorPressed(getResources().getColor(R.color.red_sub));
        fab.setColorRipple(getResources().getColor(R.color.red_sub2));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AfegirIdiomaDialog dialog = new AfegirIdiomaDialog();
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                dialog.show(fragmentManager, "tag");
                getData();
            }
        });

        if (refresh){
            listView.post(new Runnable() {
                @Override
                public void run() {
                    listView.setSelection(idiomesAdapter.getCount()-1);
                }
            });
        }
        return rootView;

    }

    public void getData() {
        c = new GestorBD(getActivity().getApplicationContext()).getIdiomes();
        idiomesAdapter = new IdiomesAdapter(getActivity().getApplicationContext(), c);
        listView.setAdapter(idiomesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                b.putString("idioma", idiomesAdapter.getNomIdioma(position));
                Intent intent = new Intent(getActivity().getApplicationContext(), GestionaUnIdioma.class);
                intent.putExtras(b);
                getActivity().startActivity(intent);
            }
        });
        idiomesAdapter.notifyDataSetChanged();
    }
}
