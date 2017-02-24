package de.kevoundfreun.labmaps;

import android.*;
import android.Manifest;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class ReclamoActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap myMap;
    PolylineOptions line;
    Polyline myPoly = null;

    private int REQUEST_CODE = 6;
    private int CODIGO_RESULTADO_ALTA_RECLAMO = 10;
    private ArrayList<Reclamo> reclamos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamo);

        reclamos = new ArrayList<>();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        // Here, thisActivity is the current activity
        actualizarMapa();

        myMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Intent i = new Intent(ReclamoActivity.this, AltaReclamoActivity.class);
                i.putExtra("coordenadas",latLng);
                startActivityForResult(i, CODIGO_RESULTADO_ALTA_RECLAMO);
            }
        });
        myMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final Marker marker) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ReclamoActivity.this);
                // Get the layout inflater
                LayoutInflater inflater = getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                final View dialogView = inflater.inflate(R.layout.alert_distancia_busqueda, null, false);
                builder.setView(dialogView)
                .setTitle("Buscar mas reclamos")
                .setPositiveButton("Buscar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        line = new PolylineOptions();
                        //medir diistancia
                        float[] results = new float[1];
                        ArrayList<Reclamo> lista_temp = new ArrayList<Reclamo>();

                        line.width(5).color(Color.RED);
                        line.add(marker.getPosition());

                        //Asegurarse que se ingrese un numero entero en el EditText
                        int distancia = Integer.valueOf(((EditText) dialogView.findViewById(R.id.distanciaReclamo)).getText().toString());

                        for(Reclamo rec : reclamos){
                            Location.distanceBetween(rec.getLatitud(),
                                    rec.getLongitud(),
                                    marker.getPosition().latitude,
                                    marker.getPosition().longitude,
                                    results);

                            if(results[0]<distancia && results[0]>0){
                                lista_temp.add(rec);
                                line.add(new LatLng(rec.getLatitud(),rec.getLongitud()));
                                Log.v("VALUES_STRING",rec.getTitulo()+" esta a: "+String.valueOf(results[0])+" metros.");
                            }
                        }
                        if(myPoly != null){
                            myPoly.remove();
                        }
                        myPoly = myMap.addPolyline(line);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .create().show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to

        Reclamo recl = (Reclamo) data.getSerializableExtra("result");

        if (requestCode == 10) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                myMap.addMarker(new MarkerOptions()
                        .position(new LatLng(recl.getLatitud(), recl.getLongitud()))
                        .title(recl.getTitulo()));
                reclamos.add(recl);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 6: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    actualizarMapa();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void actualizarMapa(){
        if (ContextCompat.checkSelfPermission(ReclamoActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(ReclamoActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE);
            return;

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
        else{
            myMap.setMyLocationEnabled(true);
            myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reclamo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
