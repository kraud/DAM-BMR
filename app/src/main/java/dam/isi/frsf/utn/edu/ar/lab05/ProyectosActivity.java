package dam.isi.frsf.utn.edu.ar.lab05;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

    AlertDialog.Builder builder;
    View dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyectos);
        new ProyectosAPIrest(this, "GET")
                .execute();
        setTitle("Proyectos");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.nuevo_proyecto);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = createDialog("GET", 0);
                dialog.show();
            }
        });

        proyectos = (ListView)findViewById(R.id.proyectos);
        registerForContextMenu(proyectos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new ProyectosAPIrest(this, "GET").execute();
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
        Proyecto proyecto = lista_proyectos.get(info.position);
        switch (item.getItemId()) {
            case R.id.modificar_proyecto:
                Dialog dialog = createDialog("PUT", proyecto.getId());
                EditText editText_proyecto = (EditText) dialogView.findViewById(R.id.nombre_proyecto);
                editText_proyecto.setText(proyecto.getNombre());
                dialog.show();
                return true;

            case R.id.eliminar_proyecto:
                new ProyectosAPIrest(ProyectosActivity.this, "DELETE", proyecto.getId())
                        .execute();
                Toast.makeText(getApplicationContext(),
                        "Eliminado!",
                        Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private Dialog createDialog(final String verbo, final int idProyecto) {
        builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        dialogView = inflater.inflate(R.layout.dialog_proyecto, null, false);
        builder.setView(dialogView)
                // Add action buttons
                .setTitle("Proyecto")
                .setPositiveButton(R.string.guardar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editText = (EditText) dialogView.findViewById(R.id.nombre_proyecto);
                        new ProyectosAPIrest(ProyectosActivity.this, verbo, idProyecto, editText.getText().toString())
                                .execute();
                        onResume();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }
}
