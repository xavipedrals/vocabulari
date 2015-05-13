package com.example.xavivaio.vocabulari;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;

import com.avast.android.dialogs.fragment.ListDialogFragment;



public class CreaTraduccioActivity extends ActionBarActivity {

    Button button;
    CreaTraduccioActivity c = this;
    private static final int REQUEST_LIST_SIMPLE = 9;
    private static final int REQUEST_LIST_SINGLE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_traduccio);
        button = (Button) findViewById(R.id.buttonList);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ListDialogFragment dialog = new ListDialogFragment();
//                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//                dialog.show(fragmentManager, "tag");

//                ListDialogFragment
//                        .createBuilder(c, getSupportFragmentManager())
//                        .setTitle("Your favorite character:")
//                        .setItems(new String[]{"Jayne", "Malcolm", "Kaylee",
//                                "Wash", "Zoe", "River"})
//                        .setRequestCode(REQUEST_LIST_SIMPLE)
//                        .show();

                ListDialogFragment
                        .createBuilder(c, getSupportFragmentManager())
                        .setTitle("Your favorite character:")
                        .setItems(new String[]{"Jayne", "Malcolm", "Kaylee",
                                "Wash", "Zoe", "River"})
                        .setRequestCode(REQUEST_LIST_SINGLE)
                        .setChoiceMode(AbsListView.CHOICE_MODE_SINGLE)
                        .show();
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
}
