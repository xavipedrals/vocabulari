package com.example.xavivaio.vocabulari.GestionaTotsIdiomes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.xavivaio.vocabulari.Dades.GestorBD;
import com.example.xavivaio.vocabulari.R;

/**
 * Created by xavivaio on 11/05/2015.
 */
public class AfegirIdiomaDialog extends DialogFragment {

    public interface RefreshFragment {
        void onFinishDialog(boolean b);
    }

    private EditText mEditText;
    private Button okButton;

    public AfegirIdiomaDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.afegir_idioma, container);
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        okButton = (Button) view.findViewById(R.id.okButton);

        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GestorBD(getActivity().getApplicationContext()).insertIdioma(mEditText.getText().toString());
                new GestorBD(getActivity().getApplicationContext()).createTableIdioma(mEditText.getText().toString());
                RefreshFragment activity = (RefreshFragment) getActivity();
                activity.onFinishDialog(true);

                dismiss();
            }
        });
        getDialog().setTitle("Afegir Idioma");
        getDialog().setCancelable(true);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}

