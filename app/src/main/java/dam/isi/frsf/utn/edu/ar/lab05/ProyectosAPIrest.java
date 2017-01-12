package dam.isi.frsf.utn.edu.ar.lab05;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import dam.isi.frsf.utn.edu.ar.lab05.rest.ConsultaProyectosListener;

/**
 * Created by ke on 9/1/2017.
 */

public class ProyectosAPIrest extends AsyncTask<String, String, String>{

    HttpURLConnection urlConnection;
    private ProgressDialog mDialog;


    @Override
    protected String doInBackground(String... params) {
        StringBuilder result = new StringBuilder();

        ConsultaProyectosListener listener;

        try {
            URL url = new URL("http://192.168.0.106:4000/proyectos");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        }catch( Exception e) {
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
        try {
            arrayProyectos = new JSONArray(result);
            arrayProyectos.getJSONObject()
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
