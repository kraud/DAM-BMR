package dam.isi.frsf.utn.edu.ar.lab05;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
    String _verb;
    int _id;
    String _payload;
    private final String IP_server = "192.168.43.239";

    public ProyectosAPIrest(ConsultaProyectosListener listener, String verbo) {
        _listener = listener;
        _verb = verbo;
    }

    public ProyectosAPIrest(ConsultaProyectosListener listener, String verbo, String carga) {
        _listener = listener;
        _verb = verbo;
        _payload = carga;
    }

    public ProyectosAPIrest(ConsultaProyectosListener listener, String verbo, int id) {
        _listener = listener;
        _verb = verbo;
        _id = id;
    }

    public ProyectosAPIrest(ConsultaProyectosListener listener, String verbo, int id, String carga) {
        _listener = listener;
        _verb = verbo;
        _id = id;
        _payload = carga;
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder result = new StringBuilder();
        InputStream in;
        OutputStream out;
        URL url;
        JSONObject jsonObject;
        byte[] data;
        try {
            switch (_verb) {
                case "GET":
                    url = new URL("http://"+IP_server+":4000/proyectos");
                    urlConnection = (HttpURLConnection) url.openConnection();

                    in = new BufferedInputStream(urlConnection.getInputStream());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    break;
                case "POST":
                    url = new URL("http://"+IP_server+":4000/proyectos");
                    urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setDoOutput (true);
                    urlConnection.setRequestProperty("Content-Type","application/json");

                    jsonObject = new JSONObject();
                    jsonObject.put("nombre", _payload);
                    data = jsonObject.toString().getBytes();
                    urlConnection.setFixedLengthStreamingMode(data.length);

                    out = new DataOutputStream(urlConnection.getOutputStream());
                    writeStream(out, data);

                    in = new BufferedInputStream(urlConnection.getInputStream());
                    readStream(in, result);
                    break;
                case "PUT":
                    url = new URL("http://"+IP_server+":4000/proyectos/"+_id);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setDoOutput (true);
                    urlConnection.setRequestMethod(_verb);
                    urlConnection.setRequestProperty("Content-Type","application/json");

                    jsonObject = new JSONObject();
                    jsonObject.put("nombre", _payload);
                    data = jsonObject.toString().getBytes();
                    urlConnection.setFixedLengthStreamingMode(data.length);

                    out = new DataOutputStream(urlConnection.getOutputStream());
                    writeStream(out, data);

                    in = new BufferedInputStream(urlConnection.getInputStream());
                    readStream(in, result);
                    break;
                case "DELETE":
                    url = new URL("http://"+IP_server+":4000/proyectos/"+_id);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setRequestMethod(_verb);
                    in = new BufferedInputStream(urlConnection.getInputStream());
                    readStream(in, result);
                    break;
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

    private void writeStream(OutputStream stream, byte[] data){
        try {
            stream.write(data);
            stream.flush();
            stream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readStream(InputStream stream, StringBuilder result) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
