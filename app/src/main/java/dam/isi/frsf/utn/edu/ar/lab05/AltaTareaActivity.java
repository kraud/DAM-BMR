package dam.isi.frsf.utn.edu.ar.lab05;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.lab05.dao.ProyectoDAO;
import dam.isi.frsf.utn.edu.ar.lab05.modelo.Prioridad;
import dam.isi.frsf.utn.edu.ar.lab05.modelo.Tarea;
import dam.isi.frsf.utn.edu.ar.lab05.modelo.Usuario;

public class AltaTareaActivity extends AppCompatActivity implements View.OnClickListener {

    private ProyectoDAO proyectoDAO;
    private Spinner usuarios;
    private EditText descripcion;
    private EditText horasEstimadas;
    private SeekBar prioridad;
    private int idTarea;
    private int minutosTrabajados = 0;

    private int prioridadValor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_tarea);

        proyectoDAO = new ProyectoDAO(AltaTareaActivity.this);
        Cursor cursor = proyectoDAO.listarUsuarios();

        usuarios = (Spinner) findViewById(R.id.spinner);
        descripcion = (EditText) findViewById(R.id.editText);
        horasEstimadas = (EditText) findViewById(R.id.editText2);
        prioridad = (SeekBar) findViewById(R.id.seekBar);

        int[] adapterRowViews = new int[]{android.R.id.text1};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item, cursor, new String[]{"NOMBRE"},
                adapterRowViews, 0);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        usuarios.setAdapter(adapter);

        prioridad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                prioridadValor = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button btnGuardar = (Button) findViewById(R.id.btnGuardar);
        Button btnCancelar = (Button) findViewById(R.id.btnCanelar);

        btnGuardar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idTarea = bundle.getInt("ID_TAREA");
        if (idTarea != 0) {
            Tarea tarea = proyectoDAO.getTarea(idTarea);
            descripcion.setText(tarea.getDescripcion());
            horasEstimadas.setText(tarea.getHorasEstimadas().toString());
            prioridad.setProgress(tarea.getPrioridad().getId());
            minutosTrabajados = tarea.getMinutosTrabajados();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGuardar:
                Tarea tarea = new Tarea(idTarea,
                        Integer.parseInt(horasEstimadas.getText().toString()),
                        minutosTrabajados,
                        false,
                        null,
                        new Prioridad(prioridadValor, null),
                        new Usuario((int) usuarios.getSelectedItemId(), null, null), //TODO Mandar usuario del spinner
                        descripcion.getText().toString());
                if(idTarea==0){
                    proyectoDAO.nuevaTarea(tarea);
                }
                else{
                    proyectoDAO.actualizarTarea(tarea);
                }
                Log.v("Guardar","Guardar Tarea");
            case R.id.btnCanelar:
                finish();
                break;
        }
    }
}
