package com.dam.kevoundfreunde.workfromhome;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Trabajo> listaTrabajos;
    /*AdapterView.OnItemLongClickListener itemListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) {
            Toast.makeText(getApplicationContext(), listaTrabajos.get(position).getDescripcion(),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Trabajo[] listTrabajosArr = Trabajo.TRABAJOS_MOCK;
        listaTrabajos = crearLista(listTrabajosArr);
        Log.v("Trabajos:", listaTrabajos.get(0).getDescripcion());
        RowAdapter adaptador = new RowAdapter(listaTrabajos, this);
        ListView lista = (ListView)findViewById(R.id.listView);
        registerForContextMenu(lista);
        lista.setAdapter(adaptador);
        //lista.setOnItemLongClickListener(itemListener);
    }

    @Override
    public void onRestart(){
        super.onRestart();
        RowAdapter adaptador = new RowAdapter(listaTrabajos, this);
        ListView lista = (ListView)findViewById(R.id.listView);
        lista.setAdapter(adaptador);
    }

    private ArrayList<Trabajo> crearLista(Trabajo[] listTrabajosArr) {
        ArrayList<Trabajo> resultado = new ArrayList<Trabajo>();
        for (int i= 0; i<listTrabajosArr.length; i++){
            resultado.add(listTrabajosArr[i]);
        }
        return resultado;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.idMenu1:
                Intent i = new Intent(this, AltaTrabajoActivity.class);
                i.putExtra("listaTrabajos", listaTrabajos);
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
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menucontextual, menu);
        menu.setHeaderTitle(R.string.contextTitle);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()) {
            case R.id.context1:
                Toast.makeText(getApplicationContext(),
                        "Te postulaste a "+listaTrabajos.get(info.position).getDescripcion(),
                        Toast.LENGTH_SHORT).show();
                return true;

            case R.id.context2:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT,"Compartir oferta");
                i.putExtra(Intent.EXTRA_TEXT,
                        "Che, mirá este ofertón!\n"+
                                listaTrabajos.get(info.position).getDescripcion());
                startActivity(Intent.createChooser(i,"Compartir"));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                // Extraemos objeto de retorno del Bundle
                Bundle bundle = data.getExtras();
                Trabajo resultado = (Trabajo) bundle.getSerializable("nuevo");
                listaTrabajos.add(resultado);
                Toast.makeText(getApplicationContext(),
                        resultado.getDescripcion() + " creado con éxito!",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                Log.v("altaTrabajoActivity","Salio sin tocar boton");
            }
        }
    }


}
