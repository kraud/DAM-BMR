package com.dam.kevoundfreunde.workfromhome;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.idMenu1:
                Intent i = new Intent(this, AltaTrabajoActivity.class);
                startActivityForResult(i,1);
                break;
            case R.id.menu2:
                Toast.makeText(getApplicationContext(), "Matias es un buen muchacho.",
                        Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuprincipal, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                // Extraemos objeto de retorno del Bundle
                Bundle bundle = data.getExtras();
                Trabajo resultado = (Trabajo) bundle.getSerializable("nuevo");
                Toast.makeText(getApplicationContext(), resultado.getDescripcion(),
                        Toast.LENGTH_SHORT).show();
            }
            else{
                Log.v("altaTrabajoActivity","Salio sin tocar boton");
            }
        }
    }


}
