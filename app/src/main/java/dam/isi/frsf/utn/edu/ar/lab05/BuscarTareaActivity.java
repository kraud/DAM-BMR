package dam.isi.frsf.utn.edu.ar.lab05;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import dam.isi.frsf.utn.edu.ar.lab05.dao.ProyectoDAO;
import dam.isi.frsf.utn.edu.ar.lab05.modelo.Tarea;

/**
 * Created by MatiBlanco on 6/1/2017.
 */
public class BuscarTareaActivity  extends AppCompatActivity {

    private ProyectoDAO proyectoDAO;
    private CheckBox terminado;
    private int numeros;
    private EditText minutos;
    private TextView resultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_tarea);
        Button buscar = (Button) findViewById(R.id.boton_buscar);
        buscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                minutos = (EditText) findViewById(R.id.minutos_desvios);
                numeros = Integer.parseInt(minutos.getText().toString());
                proyectoDAO = new ProyectoDAO(BuscarTareaActivity.this);
                terminado = (CheckBox) findViewById(R.id.terminado_check);
                List<Tarea> nuevaLista = proyectoDAO.listarDesviosPlanificacion(terminado.isChecked(), numeros);
                resultado = (TextView) findViewById(R.id.resultado_text);
                String label = new String();
                for (Tarea t : nuevaLista){
                    String exlabel = label;
                    label = exlabel + '\n' + t.getDescripcion();
                }
                resultado.setText(label);

            }
        });
    }
}