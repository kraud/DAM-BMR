package dam.isi.frsf.utn.edu.ar.lab05;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import dam.isi.frsf.utn.edu.ar.lab05.modelo.Proyecto;
import dam.isi.frsf.utn.edu.ar.lab05.rest.ConsultaProyectosListener;

/**
 * Created by ke on 9/1/2017.
 */

public class ProyectosAPIrest extends AsyncTask<String, String, String>{

    HttpURLConnection urlConnection;
    private ProgressDialog mDialog;
    ConsultaProyectosListener _listener;

    public ProyectosAPIrest(ConsultaProyectosListener listener) {
        _listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL("http://192.168.0.108:4000/proyectos");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }

        return result.toString();
    }

    @Override
    protected void onPostExecute(String result){
        JSONArray arrayProyectos;
        ArrayList<Proyecto> listaProyectos = new ArrayList<>();
        try {
            arrayProyectos = new JSONArray(result);
            for(int i = 0; i<arrayProyectos.length(); i++){
                JSONObject object = arrayProyectos.getJSONObject(i);
                Proyecto proyecto = new Proyecto(object.getInt("id"), object.getString("nombre"));

                Log.v("ProyectosAPIrest", object.getString("nombre"));

                listaProyectos.add(proyecto);
                _listener.consultaFinalizada(listaProyectos);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
