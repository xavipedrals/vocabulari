package com.example.xavivaio.vocabulari;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xavivaio.vocabulari.Dades.GestorBD;

import java.util.ArrayList;
import java.util.Random;


public class JugarActivity extends ActionBarActivity {

    TextView tvPuntuacio, tvIdioma1, tvParaula1, tvIdioma2, tvPunts;
    EditText etParaula2;
    String idioma1, idioma2;
    Button seguentBtn, rendirseBtn;
    ArrayList<String> paraulesIdioma1, paraulesIdioma2;
    ArrayList<Boolean> comprovades;
    int mida;
    int randActual;
    boolean win;
    boolean alReves;
    String nomJug;
    int puntuacio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);
        setTitle("Jugar");
        Bundle b = getIntent().getExtras();
        idioma1 = idioma2 = "";
        if (b != null) {
            idioma1 = b.getString("idioma1", null);
            idioma2 = b.getString("idioma2", null);
        }
        if (idioma1.compareTo(idioma2) < 0){
            alReves = false;
        } else {
            alReves = true;
        }
        win = false;
        paraulesIdioma1 = new ArrayList<>();
        paraulesIdioma2 = new ArrayList<>();
        getAllTaulaTraduccio();
        mida = paraulesIdioma1.size();
        comprovades = new ArrayList<>();
        for(String i: paraulesIdioma1){
            comprovades.add(false);
        }

        tvPuntuacio = (TextView) findViewById(R.id.textViewPuntuacio);
        tvPunts = (TextView) findViewById(R.id.textViewPunts);
        tvPunts.setText("0");
        tvIdioma1 = (TextView) findViewById(R.id.textViewIdioma1);
        tvIdioma1.setText(idioma1);
        tvIdioma2 = (TextView) findViewById(R.id.textViewIdioma2);
        tvIdioma2.setText(idioma2);
        tvParaula1 = (TextView) findViewById(R.id.textViewParaula1);

        etParaula2 = (EditText) findViewById(R.id.editTextParaula2);

        rendirseBtn = (Button) findViewById(R.id.buttonRendirse);
        rendirseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JugarActivity.this, MainActivity.class));
            }
        });

        seguentBtn = (Button) findViewById(R.id.buttonSeguent);
        seguentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkWin()){
                    int punts = Integer.parseInt(tvPunts.getText().toString());
                    ++punts;
                    puntuacio = punts;
                    tvPunts.setText(String.valueOf(punts));

                    AlertDialog.Builder alert = new AlertDialog.Builder(JugarActivity.this);

                    alert.setTitle("Has guanyat");
                    alert.setMessage("Introdueix el teu nom:");

                    final EditText input = new EditText(JugarActivity.this);
                    alert.setView(input);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String value = input.getText().toString();
                            new GestorBD(getApplicationContext()).insertPuntuacio(value,puntuacio);
                            startActivity(new Intent(JugarActivity.this, RankingActivity.class));
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });

                    alert.show();

                } else {
                    setParaulaRandom();
                    int punts = Integer.parseInt(tvPunts.getText().toString());
                    ++punts;
                    puntuacio = punts;
                    tvPunts.setText(String.valueOf(punts));
                }
            }
        });
        setParaulaRandom();
    }

    public void setParaulaRandom(){
        Random generator = new Random();
        randActual = generator.nextInt(mida);
        Boolean b = comprovades.get(randActual);
        while (b){
            randActual = generator.nextInt(mida);
            b = comprovades.get(randActual);
        }
        comprovades.set(randActual, true);
        if (alReves){
            tvParaula1.setText(paraulesIdioma2.get(randActual));
        }else
        tvParaula1.setText(paraulesIdioma1.get(randActual));
    }

    public boolean checkWin(){
        int cont = 0;
        for(Boolean b: comprovades){
           if(!b) ++cont;
        }
        if(cont == 0) return true;
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jugar, menu);
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

    public void getAllTaulaTraduccio(){
        Cursor cursor = new GestorBD(getApplicationContext()).getAllTaulaTraduccio(idioma1, idioma2);
        if (cursor.moveToFirst()) {
            do {
                paraulesIdioma1.add(cursor.getString(cursor.getColumnIndex(GestorBD.TRADUCCIO_COLUMN_PARAULA1)));
                paraulesIdioma2.add(cursor.getString(cursor.getColumnIndex(GestorBD.TRADUCCIO_COLUMN_PARAULA2)));
            } while (cursor.moveToNext());
        }
    }
}
