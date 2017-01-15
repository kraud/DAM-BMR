package dam.isi.frsf.utn.edu.ar.lab05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.lab05.modelo.Proyecto;
import dam.isi.frsf.utn.edu.ar.lab05.rest.ConsultaProyectosListener;

public class ProyectosActivity extends AppCompatActivity implements ConsultaProyectosListener {

    ListView proyectos;
    List<Proyecto> lista_proyectos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyectos);
        new ProyectosAPIrest(this).execute();
        setTitle("Proyectos");

        proyectos = (ListView)findViewById(R.id.proyectos);
        registerForContextMenu(proyectos);
    }

    public void consultaFinalizada(List<Proyecto> listaProyectos) {
        lista_proyectos = listaProyectos;
        ArrayAdapter<Proyecto> adaptador = new ArrayAdapter<>(this,
                android.R.layout.simple_selectable_list_item, listaProyectos);
        proyectos.setAdapter(adaptador);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_proyecto, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()) {
            case R.id.modificar_proyecto:
                Toast.makeText(getApplicationContext(),
                        "Modificaste proyecto: "+lista_proyectos.get(info.position),
                        Toast.LENGTH_SHORT).show();
                return true;

            case R.id.eliminar_proyecto:
                Toast.makeText(getApplicationContext(),
                        "TODO eliminar",
                        Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
