package com.example.xavivaio.vocabulari.GestionaIdioma;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.xavivaio.vocabulari.Dades.GestorBD;
import com.example.xavivaio.vocabulari.Adapters.ParaulesAdapter;
import com.example.xavivaio.vocabulari.R;
import com.melnykov.fab.FloatingActionButton;

/**
 * A placeholder fragment containing a simple view.
 */
public class GestionaUnIdiomaFragment extends Fragment {

    ListView listView;
    FloatingActionButton fab;
    ParaulesAdapter paraulesAdapter;
    String idioma;
    Cursor c;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gestiona_idiomes, container, false);

        Bundle b = getArguments();
        idioma = null;
        boolean refresh = false;
        if (b != null) {
            idioma = b.getString("idioma", null);
            refresh = b.getBoolean("refresh", false);
        }

        listView = (ListView) rootView.findViewById(android.R.id.list);
        getData();

        listView.setAdapter(paraulesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                int id2 = (int) customListAdapter.getItemId(position);
//                getActivity().startActivity(new Intent(getActivity().getApplicationContext(), GestionaUnIdioma.class));
            }
        });


        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.attachToListView(listView);
        fab.setColorNormal(getResources().getColor(R.color.green_baisc));
        fab.setColorPressed(getResources().getColor(R.color.green_sub));
        fab.setColorRipple(getResources().getColor(R.color.green_sub2));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AfegirParaulaDialog dialog = new AfegirParaulaDialog();
                Bundle b = new Bundle();
                b.putString("idioma", idioma);
                dialog.setArguments(b);
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                dialog.show(fragmentManager, "tag");
                getData();
            }
        });

        if (refresh){
            listView.post(new Runnable() {
                @Override
                public void run() {
                    listView.setSelection(paraulesAdapter.getCount()-1);
                }
            });
        }
        return rootView;

    }

    public void getData() {
        c = new GestorBD(getActivity().getApplicationContext()).getParaulesIdioma(idioma);
        paraulesAdapter = new ParaulesAdapter(getActivity().getApplicationContext(), c);
        listView.setAdapter(paraulesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Bundle b = new Bundle();
//                b.putString("idioma", idiomesAdapter.getNomIdioma(position));
//                Intent intent = new Intent(getActivity().getApplicationContext(), GestionaUnIdioma.class);
//                intent.putExtras(b);
//                getActivity().startActivity(intent);
            }
        });
        paraulesAdapter.notifyDataSetChanged();
    }

    public GestionaUnIdiomaFragment() {
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
