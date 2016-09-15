package com.dam.kevoundfreunde.workfromhome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Trabajo[] listTrabajos = Trabajo.TRABAJOS_MOCK;
        Log.v("Trabajos:", listTrabajos[0].getDescripcion());
        RowAdapter adaptador = new RowAdapter(listTrabajos, this);
        ListView lista = (ListView)findViewById(R.id.listView);
        lista.setAdapter(adaptador);
        // TODO No muestra nada en la app, algo esta MAL! chauchis. kissis and hugsiss
    }
}
