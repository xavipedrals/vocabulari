package com.example.xavivaio.vocabulari;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.ListDialogFragment;
import com.avast.android.dialogs.iface.IListDialogListener;
import com.example.xavivaio.vocabulari.Dades.GestorBD;

import java.util.ArrayList;

public class CreaTraduccioActivity extends ActionBarActivity implements IListDialogListener {

    Button buttonIdioma1, buttonIdioma2, buttonParaula1, buttonParaula2;
    Button buttonOK, buttonCancel;
    EditText etIdioma1, etIdioma2, etParaula1, etParaula2;
    CreaTraduccioActivity c = this;
    private static final int REQUEST_LIST_SIMPLE = 9;
    private static final int REQUEST_LIST_SINGLE = 11;
    Cursor cursor;
    int dadesIntroduides = 0;
    String idioma1, idioma2, paraula1, paraula2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_traduccio);
        setTitle("Nova traduccio");

        etIdioma1 = (EditText) findViewById(R.id.editTextIdioma1);
        etIdioma2 = (EditText) findViewById(R.id.editTextIdioma2);
        etParaula1 = (EditText) findViewById(R.id.editTextParaula1);
        etParaula2 = (EditText) findViewById(R.id.editTextParaula2);

        Bundle b = getIntent().getExtras();
        idioma1 = idioma2 = paraula1 = paraula2 = "";
        if (b != null) {
            idioma1 = b.getString("idioma", null);
            paraula1 = b.getString("paraula", null);
            dadesIntroduides = 2;
        }
        if (!idioma1.equals("")){
            etIdioma1.setText(idioma1);
            if (!paraula1.equals("")){
                etParaula1.setText(paraula1);
            }
        }

        buttonIdioma1 = (Button) findViewById(R.id.buttonIdioma1);
        buttonIdioma1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> idiomesList = getIdiomesData();
                String[] idiomesArr = new String[idiomesList.size()];
                idiomesArr = idiomesList.toArray(idiomesArr);

                ListDialogFragment
                        .createBuilder(c, getSupportFragmentManager())
                        .setTitle("Escull un idioma:")
                        .setItems(idiomesArr)
                        .setRequestCode(REQUEST_LIST_SINGLE)
                        .setChoiceMode(AbsListView.CHOICE_MODE_SINGLE)
                        .show();
            }
        });

        buttonIdioma2 = (Button) findViewById(R.id.buttonIdioma2);
        buttonIdioma2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> idiomesList = getIdiomesData();
                idiomesList.remove(idioma1);
                String[] idiomesArr = new String[idiomesList.size()];
                idiomesArr = idiomesList.toArray(idiomesArr);

                ListDialogFragment
                        .createBuilder(c, getSupportFragmentManager())
                        .setTitle("Escull un idioma:")
                        .setItems(idiomesArr)
                        .setRequestCode(REQUEST_LIST_SINGLE)
                        .setChoiceMode(AbsListView.CHOICE_MODE_SINGLE)
                        .show();
            }
        });

        buttonParaula1 = (Button) findViewById(R.id.buttonParaula1);
        buttonParaula1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> paraulesList = getParaulesIdioma(idioma1);
                String[] paraulesArr = new String[paraulesList.size()];
                paraulesArr = paraulesList.toArray(paraulesArr);

                ListDialogFragment
                        .createBuilder(c, getSupportFragmentManager())
                        .setTitle("Escull una paraula:")
                        .setItems(paraulesArr)
                        .setRequestCode(REQUEST_LIST_SINGLE)
                        .setChoiceMode(AbsListView.CHOICE_MODE_SINGLE)
                        .show();
            }
        });

        buttonParaula2 = (Button) findViewById(R.id.buttonParaula2);
        buttonParaula2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> paraulesList = getParaulesIdioma(idioma2);
                String[] paraulesArr = new String[paraulesList.size()];
                paraulesArr = paraulesList.toArray(paraulesArr);

                ListDialogFragment
                        .createBuilder(c, getSupportFragmentManager())
                        .setTitle("Escull una paraula:")
                        .setItems(paraulesArr)
                        .setRequestCode(REQUEST_LIST_SINGLE)
                        .setChoiceMode(AbsListView.CHOICE_MODE_SINGLE)
                        .show();
            }
        });

        buttonOK = (Button) findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = false;
                if(checkIdioma1()){
                    if(checkParaula1()){
                        if(checkIdioma2()){
                            if(checkParaula2()) b = true;
                        }
                    }
                }
                if (b){
                    Toast.makeText(c, "Traduccio creada correctament", Toast.LENGTH_SHORT).show();
                    new GestorBD(getApplicationContext()).createTableTraduccio(idioma1, idioma2);
                    new GestorBD(getApplicationContext()).insertTraduccioControl(idioma1, idioma2);
                    new GestorBD(getApplicationContext()).insertTraduccio(idioma1, idioma2, paraula1, paraula2);
                    int i1 = new GestorBD(getApplicationContext()).getNumTradParaula(idioma1, paraula1);
                    int i2 = new GestorBD(getApplicationContext()).getNumTradParaula(idioma2, paraula2);
                    new GestorBD(getApplicationContext()).actualitzaNumTrad(idioma1, paraula1,i1+1);
                    new GestorBD(getApplicationContext()).actualitzaNumTrad(idioma2, paraula2,i2+1);
                    new GestorBD(getApplicationContext()).insertTraduccio(idioma1, idioma2, paraula1, paraula2);
                    etIdioma1.setText("");
                    etIdioma2.setText("");
                    etParaula1.setText("");
                    etParaula2.setText("");
                }else{
                    Toast.makeText(c, "Parametres incorrectes", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crea_traduccio, menu);
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
            if(dadesIntroduides == 0){
                idioma1 = value.toString();
                etIdioma1.setText(idioma1);
            }
            else if(dadesIntroduides == 1){
                paraula1 = value.toString();
                etParaula1.setText(paraula1);
            }
            else if(dadesIntroduides == 2){
                idioma2 = value.toString();
                etIdioma2.setText(idioma2);
            }
            else if(dadesIntroduides == 3){
                paraula2 = value.toString();
                etParaula2.setText(paraula2);
            }
            dadesIntroduides++;
        }
    }

    public ArrayList<String> getIdiomesData() {
        cursor = new GestorBD(getApplicationContext()).getIdiomes();
        ArrayList<String> idiomesList = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                idiomesList.add(cursor.getString(cursor.getColumnIndex(GestorBD.IDIOMES_COLUMN_NAME)));
            } while (cursor.moveToNext());
        }
        return idiomesList;
    }

    public ArrayList<String> getParaulesIdioma(String idioma){
        cursor = new GestorBD(getApplicationContext()).getParaulesIdioma(idioma);
        ArrayList<String> paraulesList = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                paraulesList.add(cursor.getString(cursor.getColumnIndex(GestorBD.IDIOMA_COLUMN_PARAULA)));
            } while (cursor.moveToNext());
        }
        return paraulesList;
    }

    public boolean isValidWord(String w) {
        return w.matches("[A-Za-z]*");
    }

    public boolean checkIdioma1(){
        String auxIdioma1 = etIdioma1.getText().toString();
        if (!idioma1.equals("")){
            if (auxIdioma1.equals(idioma1)) return true;
            boolean b = new GestorBD(getApplicationContext()).checkIdioma(auxIdioma1);
            if (b) idioma1 = auxIdioma1;
            return b;
        }else{
            if(isValidWord(auxIdioma1) && !auxIdioma1.contains(" ") && !auxIdioma1.equals("")) {
                boolean b = new GestorBD(getApplicationContext()).checkIdioma(auxIdioma1);
                if (b) idioma1 = auxIdioma1;
                return b;
            } return false;
        }
    }

    public boolean checkIdioma2(){
        String auxIdioma2 = etIdioma2.getText().toString();
        if (!idioma2.equals("")){
            if (auxIdioma2.equals(idioma1)) return true;
            boolean b = new GestorBD(getApplicationContext()).checkIdioma(auxIdioma2);
            if (b) idioma2 = auxIdioma2;
            return b;
        }else{
            if(isValidWord(auxIdioma2) && !auxIdioma2.contains(" ") && !auxIdioma2.equals("")) {
                boolean b = new GestorBD(getApplicationContext()).checkIdioma(auxIdioma2);
                if (b) idioma2 = auxIdioma2;
                return b;
            } return false;
        }
    }

    public boolean checkParaula1(){
        String auxParaula1 = etParaula1.getText().toString();
        if (paraula1.equals("")){
            if (auxParaula1.equals(paraula1)) return true;
            boolean b = new GestorBD(getApplicationContext()).checkParaula(idioma1, auxParaula1);
            if (b) paraula1 = auxParaula1;
            return b;
        }else{
            if(isValidWord(auxParaula1) && !auxParaula1.contains(" ") && !auxParaula1.equals("")) {
                boolean b = new GestorBD(getApplicationContext()).checkParaula(idioma1, auxParaula1);
                if (b) paraula1 = auxParaula1;
                return b;
            } return false;
        }
    }

    public boolean checkParaula2(){
        String auxParaula2 = etParaula2.getText().toString();
        if (paraula2.equals("")){
            if (auxParaula2.equals(paraula2)) return true;
            boolean b = new GestorBD(getApplicationContext()).checkParaula(idioma2, auxParaula2);
            if (b) paraula2 = auxParaula2;
            return b;
        }else{
            if(isValidWord(auxParaula2) && !auxParaula2.contains(" ") && !auxParaula2.equals("")) {
                boolean b = new GestorBD(getApplicationContext()).checkParaula(idioma2, auxParaula2);
                if (b) paraula2 = auxParaula2;
                return b;
            } return false;
        }
    }
}
