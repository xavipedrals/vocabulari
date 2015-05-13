package com.example.xavivaio.vocabulari.GestionaParaula;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.xavivaio.vocabulari.R;

public class GestionaParaulaActivity extends ActionBarActivity {

    Fragment f;
    String idioma, paraula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestiona_paraula);
        Bundle b = getIntent().getExtras();
        idioma = null;
        paraula = null;
        if (b != null) {
            idioma = b.getString("idioma", null);
            paraula = b.getString("paraula", null);
        }
        f = new GestionaParaulaActivityFragment();
        f.setArguments(b);
        getSupportFragmentManager().beginTransaction().add(R.id.containerParaula,f,"gestio").commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gestiona_paraula, menu);
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
}
