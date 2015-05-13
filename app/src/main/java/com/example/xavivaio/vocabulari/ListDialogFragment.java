package com.example.xavivaio.vocabulari;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xavivaio.vocabulari.Adapters.IdiomesAdapter;
import com.example.xavivaio.vocabulari.Dades.GestorBD;
import com.example.xavivaio.vocabulari.GestionaIdioma.GestionaUnIdioma;

/**
 * Created by xavivaio on 13/05/2015.
 */
public class ListDialogFragment extends DialogFragment {

    ListView listView;
    Button okButton, cancelButton;
    IdiomesAdapter idiomesAdapter;
    Cursor c;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_llista, container, false);

        okButton = (Button) rootView.findViewById(R.id.okButton);
        cancelButton = (Button) rootView.findViewById(R.id.cancelButton);

        listView = (ListView) rootView.findViewById(android.R.id.list);
        getData();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String input = mEditText.getText().toString();
//                if(isValidWord(input) && !input.contains(" ")) {
//                    new GestorBD(getActivity().getApplicationContext()).insertParaula(idioma, input);
//                    int numPar = new GestorBD(getActivity().getApplicationContext()).getNumParIdioma(idioma);
//                    new GestorBD(getActivity().getApplicationContext()).updateIdiomes(idioma, numPar + 1);
//                    RefreshFragment activity = (RefreshFragment) getActivity();
//                    activity.onFinishDialog(true);
                    dismiss();
//                } else
//                    Toast.makeText(getActivity().getApplicationContext(), "Introdueix una paraula valida", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        getDialog().setTitle("Afegir Paraula");
        getDialog().setCancelable(true);
        return rootView;
    }

    public void getData() {
        c = new GestorBD(getActivity().getApplicationContext()).getIdiomes();
        idiomesAdapter = new IdiomesAdapter(getActivity().getApplicationContext(), c);
        listView.setAdapter(idiomesAdapter);
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
//        idiomesAdapter.notifyDataSetChanged();
    }
}
