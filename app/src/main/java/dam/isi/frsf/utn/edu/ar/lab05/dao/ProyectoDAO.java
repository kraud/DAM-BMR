package dam.isi.frsf.utn.edu.ar.lab05.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.lab05.modelo.Prioridad;
import dam.isi.frsf.utn.edu.ar.lab05.modelo.Tarea;
import dam.isi.frsf.utn.edu.ar.lab05.modelo.Usuario;

/**
 * Created by mdominguez on 06/10/16.
 */
public class ProyectoDAO {

    private static final String _SQL_TAREAS_X_PROYECTO = "SELECT "+ProyectoDBMetadata.TABLA_TAREAS_ALIAS+"."+ProyectoDBMetadata.TablaTareasMetadata._ID+" as "+ProyectoDBMetadata.TablaTareasMetadata._ID+
            ", "+ProyectoDBMetadata.TablaTareasMetadata.TAREA +
            ", "+ProyectoDBMetadata.TablaTareasMetadata.HORAS_PLANIFICADAS +
            ", "+ProyectoDBMetadata.TablaTareasMetadata.MINUTOS_TRABAJADOS +
            ", "+ProyectoDBMetadata.TablaTareasMetadata.FINALIZADA +
            ", "+ProyectoDBMetadata.TablaTareasMetadata.PRIORIDAD +
            ", "+ProyectoDBMetadata.TABLA_PRIORIDAD_ALIAS+"."+ProyectoDBMetadata.TablaPrioridadMetadata.PRIORIDAD +" as "+ProyectoDBMetadata.TablaPrioridadMetadata.PRIORIDAD_ALIAS+
            ", "+ProyectoDBMetadata.TablaTareasMetadata.RESPONSABLE +
            ", "+ProyectoDBMetadata.TABLA_USUARIOS_ALIAS+"."+ProyectoDBMetadata.TablaUsuariosMetadata.USUARIO +" as "+ProyectoDBMetadata.TablaUsuariosMetadata.USUARIO_ALIAS+
            " FROM "+ProyectoDBMetadata.TABLA_PROYECTO + " "+ProyectoDBMetadata.TABLA_PROYECTO_ALIAS+", "+
            ProyectoDBMetadata.TABLA_USUARIOS + " "+ProyectoDBMetadata.TABLA_USUARIOS_ALIAS+", "+
            ProyectoDBMetadata.TABLA_PRIORIDAD + " "+ProyectoDBMetadata.TABLA_PRIORIDAD_ALIAS+", "+
            ProyectoDBMetadata.TABLA_TAREAS + " "+ProyectoDBMetadata.TABLA_TAREAS_ALIAS+
            " WHERE "+ProyectoDBMetadata.TABLA_TAREAS_ALIAS+"."+ProyectoDBMetadata.TablaTareasMetadata.PROYECTO+" = "+ProyectoDBMetadata.TABLA_PROYECTO_ALIAS+"."+ProyectoDBMetadata.TablaProyectoMetadata._ID +" AND "+
            ProyectoDBMetadata.TABLA_TAREAS_ALIAS+"."+ProyectoDBMetadata.TablaTareasMetadata.RESPONSABLE+" = "+ProyectoDBMetadata.TABLA_USUARIOS_ALIAS+"."+ProyectoDBMetadata.TablaUsuariosMetadata._ID +" AND "+
            ProyectoDBMetadata.TABLA_TAREAS_ALIAS+"."+ProyectoDBMetadata.TablaTareasMetadata.PRIORIDAD+" = "+ProyectoDBMetadata.TABLA_PRIORIDAD_ALIAS+"."+ProyectoDBMetadata.TablaPrioridadMetadata._ID +" AND "+
            ProyectoDBMetadata.TABLA_TAREAS_ALIAS+"."+ProyectoDBMetadata.TablaTareasMetadata.PROYECTO+" = ?";

    private ProyectoOpenHelper dbHelper;
    private SQLiteDatabase db;

    public ProyectoDAO(Context c){
        this.dbHelper = new ProyectoOpenHelper(c);
    }

    public void open(){
        this.open(false);
    }

    public void open(Boolean toWrite){
        if(toWrite) {
            db = dbHelper.getWritableDatabase();
        }
        else{
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close(){
        db = dbHelper.getReadableDatabase();
    }

    public Cursor listaTareas(Integer idProyecto){
        Cursor cursorPry = db.rawQuery("SELECT "+ProyectoDBMetadata.TablaProyectoMetadata._ID+ " FROM "+ProyectoDBMetadata.TABLA_PROYECTO,null);
        Integer idPry= 0;
        if(cursorPry.moveToFirst()){
            idPry=cursorPry.getInt(0);
        }
        Cursor cursor = null;
        Log.d("LAB05-MAIN","PROYECTO : _"+idPry.toString()+" - "+ _SQL_TAREAS_X_PROYECTO);
        cursor = db.rawQuery(_SQL_TAREAS_X_PROYECTO,new String[]{idPry.toString()});
        return cursor;
    }

    public void nuevaTarea(Tarea t){
        SQLiteDatabase mydb = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put(ProyectoDBMetadata.TablaTareasMetadata.FINALIZADA, t.getFinalizada());
        valores.put(ProyectoDBMetadata.TablaTareasMetadata.HORAS_PLANIFICADAS, t.getHorasEstimadas());
        valores.put(ProyectoDBMetadata.TablaTareasMetadata.MINUTOS_TRABAJADOS, t.getMinutosTrabajados());
        valores.put(ProyectoDBMetadata.TablaTareasMetadata.PROYECTO, 1); // hardcodeado porque solo existe un unico proyecto
        valores.put(ProyectoDBMetadata.TablaTareasMetadata.PRIORIDAD, t.getPrioridad().getId());
        valores.put(ProyectoDBMetadata.TablaTareasMetadata.RESPONSABLE, t.getResponsable().getId());
        valores.put(ProyectoDBMetadata.TablaTareasMetadata.TAREA, t.getDescripcion());

        int id = (int) mydb.insert(ProyectoDBMetadata.TABLA_TAREAS, null, valores);
        Log.v("ProyectoDAO", "El ID es: " + id);
        Cursor cursor = mydb.rawQuery("SELECT "+ProyectoDBMetadata.TablaTareasMetadata.TAREA +
                        " FROM "+ProyectoDBMetadata.TABLA_TAREAS+
                        " WHERE _id = "+id
                ,null);
        cursor.moveToFirst();
        String desc = cursor.getString(cursor.getColumnIndex(
                ProyectoDBMetadata.TablaTareasMetadata.TAREA));
        Log.v("ProyectoDAO", "La descripcion es: " + desc);
        mydb.close();
    }

    public void actualizarTarea(Tarea t){

    }

    public void registerTrabajo (Integer idTarea, long tiempo) {
        SQLiteDatabase mydb = dbHelper.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("SELECT "+ProyectoDBMetadata.TablaTareasMetadata.MINUTOS_TRABAJADOS+
                " FROM "+ProyectoDBMetadata.TABLA_TAREAS+
                " WHERE _id = "+idTarea
                ,null);
        Integer tiempoGuardado= 0;
        if(cursor.moveToFirst()){
            tiempoGuardado = cursor.getInt(0);
        }
        long minutos = tiempo / 5000;
        Log.d("ProyectoDAO", "Tiempo: "+tiempoGuardado+" Agregado "+minutos);
        Log.d("ProyectoDAO", "Tiempo: "+(tiempoGuardado+minutos));
        ContentValues valores = new ContentValues();
        valores.put(ProyectoDBMetadata.TablaTareasMetadata.MINUTOS_TRABAJADOS, tiempoGuardado+minutos);
        mydb.update(ProyectoDBMetadata.TABLA_TAREAS, valores, "_id=?", new String[]{idTarea.toString()});
        mydb.close();
    }

    public void borrarTarea(Tarea t){

    }

    public List<Prioridad> listarPrioridades(){
        return null;
    }

    public List<Usuario> listarUsuarios(){
        SQLiteDatabase mydb = dbHelper.getReadableDatabase();
        Cursor cursor = mydb.rawQuery(
                "SELECT "+ProyectoDBMetadata.TablaUsuariosMetadata.USUARIO + ", " +
                ProyectoDBMetadata.TablaUsuariosMetadata.MAIL + ", " +
                ProyectoDBMetadata.TablaUsuariosMetadata._ID +
                " FROM " + ProyectoDBMetadata.TABLA_USUARIOS, null);
        List<Usuario> usuarios = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                String nombre = cursor.getString(cursor.getColumnIndex(
                        ProyectoDBMetadata.TablaUsuariosMetadata.USUARIO));
                String email = cursor.getString(cursor.getColumnIndex(
                        ProyectoDBMetadata.TablaUsuariosMetadata.MAIL));

                Usuario user = new Usuario(null, nombre, email);
                usuarios.add(user);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return usuarios;
    }

    public void finalizar(Integer idTarea){
        //Establecemos los campos-valores a actualizar
        ContentValues valores = new ContentValues();
        valores.put(ProyectoDBMetadata.TablaTareasMetadata.FINALIZADA,1);
        SQLiteDatabase mydb =dbHelper.getWritableDatabase();
        mydb.update(ProyectoDBMetadata.TABLA_TAREAS, valores, "_id=?", new String[]{idTarea.toString()});
        mydb.close();
    }

    public List<Tarea> listarDesviosPlanificacion(Boolean soloTerminadas,Integer desvioMaximoMinutos){
        // retorna una lista de todas las tareas que tardaron más (en exceso) o menos (por defecto)
        // que el tiempo planificado.
        // si la bandera soloTerminadas es true, se busca en las tareas terminadas, sino en todas.
        return null;
    }


}
