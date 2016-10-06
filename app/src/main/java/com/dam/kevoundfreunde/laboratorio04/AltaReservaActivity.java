package com.dam.kevoundfreunde.laboratorio04;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.dam.kevoundfreunde.laboratorio04.modelo.Departamento;

import java.util.ArrayList;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.laboratorio04.R;

public class AltaReservaActivity extends AppCompatActivity {

    public static List<Departamento> reservados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_reserva);
        setTitle("Reservas");
    }

    @Override
    protected void onStart() {
        super.onStart();
        ListView listaReservados = (ListView) findViewById(R.id.listaReservados);
        DepartamentoAdapter adapter = new DepartamentoAdapter(AltaReservaActivity.this, reservados);
        listaReservados.setAdapter(adapter);
    }

    public static void agregarReserva(Departamento nuevo){
        reservados.add(nuevo);
    }
}
