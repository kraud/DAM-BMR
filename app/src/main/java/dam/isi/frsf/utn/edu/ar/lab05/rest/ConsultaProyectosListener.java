package dam.isi.frsf.utn.edu.ar.lab05.rest;

import org.json.JSONArray;

import java.util.List;

import dam.isi.frsf.utn.edu.ar.lab05.modelo.Proyecto;

/**
 * Created by ke on 12/1/2017.
 */

public interface ConsultaProyectosListener {
    public void consultaFinalizada(List<Proyecto> listaProyectos);
}
