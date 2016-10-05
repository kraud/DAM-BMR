package com.dam.kevoundfreunde.laboratorio04.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.dam.kevoundfreunde.laboratorio04.modelo.Ciudad;
import com.dam.kevoundfreunde.laboratorio04.modelo.Departamento;

/**
 * Created by martdominguez on 22/09/2016.
 */
public class BuscarDepartamentosTask extends AsyncTask<FormBusqueda,Integer,List<Departamento>> {

    private BusquedaFinalizadaListener<Departamento> listener;

    public BuscarDepartamentosTask(BusquedaFinalizadaListener<Departamento> dListener){
        this.listener = dListener;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Departamento> departamentos) {
        listener.busquedaFinalizada(departamentos);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        listener.busquedaActualizada(values[0]+" departamentos encontrados.");
    }

    @Override
    protected List<Departamento> doInBackground(FormBusqueda... busqueda) {
        List<Departamento> todos = Departamento.getAlojamientosDisponibles();
        List<Departamento> resultado;
        int contador = 0;
        Ciudad ciudadBuscada = busqueda[0].getCiudad();
        Double precioMin = busqueda[0].getPrecioMinimo();
        Double precioMax = busqueda[0].getPrecioMaximo();
        Boolean permiteFumar = busqueda[0].getPermiteFumar();
        Integer huespedes = busqueda[0].getHuespedes();
        resultado = filterDepartamentos(todos, ciudadBuscada, precioMin, precioMax, permiteFumar,
                huespedes, contador);
        return resultado;
    }

    private List<Departamento> filterDepartamentos(List<Departamento> todos, Ciudad ciudadBuscada,
                                                   Double precioMin, Double precioMax,
                                                   Boolean permiteFumar, Integer huespedes,
                                                   int contador) {


        List<Departamento> resultado = new ArrayList<Departamento>();
        for(Departamento d: todos){
            Log.v("Datos1",d.getCiudad()+ " "+ d.getPrecio()+" "+d.getNoFumador()+ " "+d.getCantidadCamas());
            Log.v("Datos2",ciudadBuscada+ " "+ precioMax+" "+precioMin+" "+ permiteFumar+ " "+huespedes);
            if(d.getCiudad() == ciudadBuscada
                    && (d.getPrecio() < precioMax && d.getPrecio() > precioMin)
                    && (d.getNoFumador() == permiteFumar)
                    && (d.getCantidadCamas() > huespedes)
            ){
                resultado.add(d);
                contador++;
                Log.v("Contador", "Contador: "+contador);
                publishProgress(contador);
            }
        }
        return resultado;
    }
}
