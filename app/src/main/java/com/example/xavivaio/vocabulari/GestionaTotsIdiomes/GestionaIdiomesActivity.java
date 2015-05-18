package com.example.xavivaio.vocabulari.GestionaTotsIdiomes;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.xavivaio.vocabulari.R;
import com.example.xavivaio.vocabulari.RefreshFragment;


public class GestionaIdiomesActivity extends ActionBarActivity implements RefreshFragment {

    Fragment f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestiona_idiomes);
        f = new GestionaIdiomesActivityFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.containerIdiomes,f,"gestio").commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_gestiona_idiomes, menu);

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
            f = new GestionaIdiomesActivityFragment();
            f.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.containerIdiomes,f,"gestio").commit();
        }
    }
}
