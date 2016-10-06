package com.dam.kevoundfreunde.laboratorio04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.laboratorio04.R;

import com.dam.kevoundfreunde.laboratorio04.utils.BuscarDepartamentosTask;
import com.dam.kevoundfreunde.laboratorio04.modelo.Departamento;
import com.dam.kevoundfreunde.laboratorio04.utils.BusquedaFinalizadaListener;
import com.dam.kevoundfreunde.laboratorio04.utils.FormBusqueda;

public class ListaDepartamentosActivity extends AppCompatActivity implements BusquedaFinalizadaListener<Departamento> {

    private TextView tvEstadoBusqueda;
    private ListView listaAlojamientos;
    private DepartamentoAdapter departamentosAdapter;
    private List<Departamento> lista;
    private List<Departamento> listaFiltrada;
    private Boolean busqueda = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alojamientos);
        lista= new ArrayList<>();
        listaAlojamientos= (ListView ) findViewById(R.id.listaAlojamientos);
        tvEstadoBusqueda = (TextView) findViewById(R.id.estadoBusqueda);
        registerForContextMenu(listaAlojamientos);
        setTitle("Departamentos");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Boolean esBusqueda = intent.getExtras().getBoolean("esBusqueda");
        busqueda = esBusqueda;
        if(esBusqueda){
            FormBusqueda fb = (FormBusqueda ) intent.getSerializableExtra("frmBusqueda");
            new BuscarDepartamentosTask(ListaDepartamentosActivity.this).execute(fb);
            tvEstadoBusqueda.setText("Buscando....");
            tvEstadoBusqueda.setVisibility(View.VISIBLE);
        }else{
            tvEstadoBusqueda.setVisibility(View.GONE);
            lista=Departamento.getAlojamientosDisponibles();
        }
        departamentosAdapter = new DepartamentoAdapter(ListaDepartamentosActivity.this,lista);
        listaAlojamientos.setAdapter(departamentosAdapter);
    }

    @Override
    public void busquedaFinalizada(List<Departamento> listaDepartamento) {
        listaFiltrada = listaDepartamento;
        Log.v("busquedaFinalizada","Busqueda finalizada: "+ listaDepartamento.toString());
        DepartamentoAdapter otro = new DepartamentoAdapter(ListaDepartamentosActivity.this,listaDepartamento);
        tvEstadoBusqueda.setVisibility(View.GONE);
        listaAlojamientos.setAdapter(otro);
    }

    @Override
    public void busquedaActualizada(String msg) {
        tvEstadoBusqueda.setText(" Buscando... "+msg);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_reserva, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()) {
            case R.id.context1:
                List<Departamento> departamentos = busqueda ? listaFiltrada : lista;
                Toast.makeText(getApplicationContext(),
                        "Reservaste "+departamentos.get(info.position).getDescripcion(),
                        Toast.LENGTH_SHORT).show();
                AltaReservaActivity.agregarReserva(departamentos.get(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
