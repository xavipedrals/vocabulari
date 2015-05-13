package com.example.xavivaio.vocabulari;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.*;
import com.avast.android.dialogs.iface.IListDialogListener;
import com.example.xavivaio.vocabulari.Dades.GestorBD;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class ConfigurarJocActivity extends ActionBarActivity implements IListDialogListener {

    Button idiomaTrad1Btn, idiomaTrad2Btn;
    Button jugarBtn, cancelarBtn;
    EditText etIdioma1, etIdioma2;
    String idioma1, idioma2;
    Cursor cursor;
    ConfigurarJocActivity c = this;
    private static final int REQUEST_LIST_SIMPLE = 9;
    private static final int REQUEST_LIST_SINGLE = 11;
    int dadesIntroduides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_joc);
        dadesIntroduides = 0;
        etIdioma1 = (EditText) findViewById(R.id.editTextTrad1);
        etIdioma2 = (EditText) findViewById(R.id.editTextTrad2);

        idiomaTrad1Btn = (Button) findViewById(R.id.buttonTrad1);
        idiomaTrad1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> idiomesList = getIdiomesData();
                eliminaRepetits(idiomesList);
                String[] idiomesArr = new String[idiomesList.size()];
                idiomesArr = idiomesList.toArray(idiomesArr);

                com.avast.android.dialogs.fragment.ListDialogFragment
                        .createBuilder(c, getSupportFragmentManager())
                        .setTitle("Escull un idioma:")
                        .setItems(idiomesArr)
                        .setRequestCode(REQUEST_LIST_SINGLE)
                        .setChoiceMode(AbsListView.CHOICE_MODE_SINGLE)
                        .show();
            }
        });

        idiomaTrad2Btn = (Button) findViewById(R.id.buttonTrad2);
        idiomaTrad2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> idiomesList = getIdiomesPossibilitatTraduccio();
                //eliminaRepetits(idiomesList);
                String[] idiomesArr = new String[idiomesList.size()];
                idiomesArr = idiomesList.toArray(idiomesArr);

                com.avast.android.dialogs.fragment.ListDialogFragment
                        .createBuilder(c, getSupportFragmentManager())
                        .setTitle("Escull un idioma:")
                        .setItems(idiomesArr)
                        .setRequestCode(REQUEST_LIST_SINGLE)
                        .setChoiceMode(AbsListView.CHOICE_MODE_SINGLE)
                        .show();
            }
        });

        jugarBtn = (Button) findViewById(R.id.buttonJugar);
        jugarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Comprovar errors
                Intent intent = new Intent(ConfigurarJocActivity.this, JugarActivity.class);
                Bundle b = new Bundle();
                b.putString("idioma1", idioma1);
                b.putString("idioma2", idioma2);
                intent.putExtras(b);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configurar_joc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemSelected(CharSequence value, int number, int requestCode) {
        if (requestCode == REQUEST_LIST_SIMPLE || requestCode == REQUEST_LIST_SINGLE) {
            Toast.makeText(c, "Selected: " + value, Toast.LENGTH_SHORT).show();
            if (dadesIntroduides == 0) {
                idioma1 = value.toString();
                etIdioma1.setText(idioma1);
            } else if (dadesIntroduides == 1) {
                idioma2 = value.toString();
                etIdioma2.setText(idioma2);
            }
            dadesIntroduides++;
        }
    }


    public ArrayList<String> getIdiomesData() {
        cursor = new GestorBD(getApplicationContext()).getTraduccioControl();
        ArrayList<String> allIdiomesTrad = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                allIdiomesTrad.add(cursor.getString(cursor.getColumnIndex(GestorBD.TRADUCCIONS_COLUMN_IDIOMA1)));
                allIdiomesTrad.add(cursor.getString(cursor.getColumnIndex(GestorBD.TRADUCCIONS_COLUMN_IDIOMA2)));
            } while (cursor.moveToNext());
        }
        return allIdiomesTrad;
    }

    public ArrayList<String> getIdiomesPossibilitatTraduccio(){
        String auxIdioma1 = etIdioma1.getText().toString();
        //TODO: comprova errors
        ArrayList<String> allIdiomesTrad = new ArrayList<String>();
        cursor = new GestorBD(getApplicationContext()).getTaulesTraduccio(auxIdioma1);
        if (cursor.moveToFirst()) {
            do {
                String aux1 = (cursor.getString(cursor.getColumnIndex(GestorBD.TRADUCCIONS_COLUMN_IDIOMA1)));
                String aux2 = (cursor.getString(cursor.getColumnIndex(GestorBD.TRADUCCIONS_COLUMN_IDIOMA2)));
                if (!aux1.equals(auxIdioma1)) allIdiomesTrad.add(aux1);
                else if (!aux2.equals(auxIdioma1)) allIdiomesTrad.add(aux2);
            } while (cursor.moveToNext());
        }
        return allIdiomesTrad;
    }

    public void eliminaRepetits(ArrayList<String> arrayList){
        Set<String> hs = new HashSet<>();
        hs.addAll(arrayList);
        arrayList.clear();
        arrayList.addAll(hs);
    }
}
