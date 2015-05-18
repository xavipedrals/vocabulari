package com.example.xavivaio.vocabulari;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.xavivaio.vocabulari.Adapters.IdiomesAdapter;
import com.example.xavivaio.vocabulari.Adapters.JugadorAdapter;
import com.example.xavivaio.vocabulari.Dades.GestorBD;
import com.example.xavivaio.vocabulari.GestionaIdioma.GestionaUnIdioma;


public class RankingActivity extends ActionBarActivity {

    ListView listView;
    Cursor c;
    JugadorAdapter jugadorsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Ranking");
        setContentView(R.layout.activity_ranking);
        listView = (ListView) findViewById(R.id.listViewRanking);
        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_ranking, menu);
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void getData() {
        c = new GestorBD(getApplicationContext()).getPuntuacions();
        jugadorsAdapter = new JugadorAdapter(getApplicationContext(), c);
        listView.setAdapter(jugadorsAdapter);
        jugadorsAdapter.notifyDataSetChanged();
    }
}
