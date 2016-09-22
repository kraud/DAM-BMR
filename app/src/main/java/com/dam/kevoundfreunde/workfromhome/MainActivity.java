package com.dam.kevoundfreunde.workfromhome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Trabajo[] listTrabajos = Trabajo.TRABAJOS_MOCK;
        Log.v("Trabajos:", listTrabajos[0].getDescripcion());
        RowAdapter adaptador = new RowAdapter(listTrabajos, this);
        ListView lista = (ListView)findViewById(R.id.listView);
        lista.setAdapter(adaptador);
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(getApplicationContext(), listTrabajos[position].getDescripcion(),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
