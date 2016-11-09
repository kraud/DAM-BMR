package dam.isi.frsf.utn.edu.ar.lab05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

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

    private int prioridadValor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_tarea);

        proyectoDAO = new ProyectoDAO(AltaTareaActivity.this);
        List<Usuario> listaUsuarios = proyectoDAO.listarUsuarios();

        usuarios = (Spinner) findViewById(R.id.spinner);
        descripcion = (EditText) findViewById(R.id.editText);
        horasEstimadas = (EditText) findViewById(R.id.editText2);
        prioridad = (SeekBar) findViewById(R.id.seekBar);

        List<CharSequence> nombreUsuarios = new ArrayList<>();

        for(int i=0; i<listaUsuarios.size(); i++){
            nombreUsuarios.add(listaUsuarios.get(i).getNombre());
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, nombreUsuarios);
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

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGuardar:
                Tarea tarea = new Tarea(0,
                        Integer.parseInt(horasEstimadas.getText().toString()),
                        0,
                        false,
                        null,
                        new Prioridad(prioridadValor, null),
                        new Usuario(0, null, null), //TODO Mandar usuario del spinner
                        descripcion.getText().toString());
                proyectoDAO.nuevaTarea(tarea);
                Log.v("Guardar","Guardar Tarea");
            case R.id.btnCanelar:
                finish();
                break;
        }
    }
}
