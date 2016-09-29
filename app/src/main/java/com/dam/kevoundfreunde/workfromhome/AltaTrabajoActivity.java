package com.dam.kevoundfreunde.workfromhome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kevo on 22/09/2016.
 */
public class AltaTrabajoActivity extends AppCompatActivity implements View.OnClickListener{
    ArrayList<Trabajo> listaTrabajos = new ArrayList<Trabajo>();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trabajo_layout);
        // Setear spinner categorias
        Spinner categorias = (Spinner) findViewById(R.id.spinnerCategorias);
        ArrayList<String> listaCategorias = obtenerCategorias(Categoria.CATEGORIAS_MOCK);
        ArrayAdapter adapterCategorias = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, listaCategorias);
        categorias.setAdapter(adapterCategorias);

        // Setear spinner monedas
        Spinner monedas = (Spinner) findViewById(R.id.inputMoneda);
        ArrayAdapter<CharSequence> adapterMonedas = ArrayAdapter.createFromResource(this,
                R.array.monedas, android.R.layout.simple_spinner_item);
        adapterMonedas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monedas.setAdapter(adapterMonedas);

        Button guardar = (Button) findViewById(R.id.ingresarButton);
        guardar.setOnClickListener(this);
    }

    private ArrayList<String> obtenerCategorias (Categoria[] lista){
        ArrayList<String> retorno = new ArrayList<String>();
        for(int i=0;i<lista.length;i++){
            retorno.add(lista[i].getDescripcion());
        }
        return retorno;
    }


    @Override
    public void onClick(View v) {
        EditText descripcion = (EditText) findViewById(R.id.inputDescripcion);
        String descripcionString = descripcion.getText().toString();
        EditText horas = (EditText) findViewById(R.id.inputHoras);
        String horasString = horas.getText().toString();
        Integer horasInteger = Integer.parseInt(horasString);
        EditText precio = (EditText) findViewById(R.id.inputPrecio);
        String precioString = precio.getText().toString();
        Double precioDouble = Double.parseDouble(precioString);
        EditText fecha = (EditText) findViewById(R.id.inputDate);
        String fechaString = fecha.getText().toString();
        Date fechaDate = stringToDate(fechaString, "dd/MM/yyyy");
        Spinner categoria = (Spinner) findViewById(R.id.spinnerCategorias);
        String categoriaString = categoria.getSelectedItem().toString();
        Spinner moneda = (Spinner) findViewById(R.id.inputMoneda);
        String monedaString = moneda.getSelectedItem().toString();
        CheckBox ingles = (CheckBox) findViewById(R.id.inglesCheck);
        boolean inglesBool = ingles.isChecked();

        Categoria categoriaFinal = getCategoria(Categoria.CATEGORIAS_MOCK, categoriaString);
        Integer monedaFinal = transformarMoneda(monedaString) ;
        Bundle b = getIntent().getExtras();
        listaTrabajos = (ArrayList<Trabajo>) b.getSerializable("listaTrabajos");
        int idTrabajo = listaTrabajos.size() + 1;

        Trabajo nuevo = new Trabajo(idTrabajo, descripcionString, categoriaFinal,horasInteger,
                precioDouble, fechaDate, monedaFinal,inglesBool);
        Log.v("Fecha: ", String.valueOf(fechaDate));
        Log.v("Id:", " " + idTrabajo);

        // Crear nuevo Intent a retornar
        Intent returnIntent = new Intent();
        // Ponemos el objeto a retornar en un Bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable("nuevo", nuevo);
        returnIntent.putExtras(bundle);
        // Seteamos el resultado como OK y finalizamos
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }


    private Date stringToDate(String aDate,String aFormat) {

        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }

    private Integer transformarMoneda(String monedaString) {
        Integer resultado =0;
        switch (monedaString){
            case "Pesos ARS":
                resultado = 3;
                break;
            case "Reales":
                resultado = 5;
                break;
            case "Euros":
                resultado = 2;
                break;
            case "Libras Esterlinas":
                resultado = 4;
                break;
            case "Dólares":
                resultado = 1;
                break;
        }
        return resultado;
    }

    private Categoria getCategoria(Categoria[] categoriasMock, String categoriaString) {
        for (Categoria c : categoriasMock){
            if(c.getDescripcion() == categoriaString){
                return c;
            }
        }
        return null;
    }
}
