package com.example.xavivaio.vocabulari.GestionaIdioma;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.xavivaio.vocabulari.GestionaTotsIdiomes.AfegirIdiomaDialog;
import com.example.xavivaio.vocabulari.R;
import com.example.xavivaio.vocabulari.RefreshFragment;

public class GestionaUnIdioma extends ActionBarActivity implements RefreshFragment {

    Fragment f;
    String idioma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestiona_un_idioma);
        Bundle b = getIntent().getExtras();
        idioma = null;
        if (b != null) {
            idioma = b.getString("idioma", null);
        }
        f = new GestionaUnIdiomaFragment();
        f.setArguments(b);
        getSupportFragmentManager().beginTransaction().add(R.id.containerParaules,f,"gestio").commit();
    }

    public String getIdioma(){
        return idioma;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gestiona_un_idioma, menu);
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
    public void onFinishDialog(boolean b) {
        if (b) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("refresh", true);
            bundle.putString("idioma", idioma);
            f = new GestionaUnIdiomaFragment();
            f.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.containerParaules,f,"gestio").commit();
        }
    }
}
