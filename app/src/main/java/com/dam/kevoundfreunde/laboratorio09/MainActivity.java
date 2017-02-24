package com.dam.kevoundfreunde.laboratorio09;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private float[] linear_acceleration = new float[3];
    private float[] gravity = new float[3];

    private float[] max_acceleration = new float[] {0f, 0f, 0f};

    private TextView[] textViews = new TextView[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        textViews[0] = (TextView) findViewById(R.id.acc_x);
        textViews[1] = (TextView) findViewById(R.id.acc_y);
        textViews[2] = (TextView) findViewById(R.id.acc_z);

        Intent i = new Intent(getApplicationContext(), ReceptorMensaje.class);
        startService(i);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        // In this example, alpha is calculated as t / (t + dT),
        // where t is the low-pass filter's time-constant and
        // dT is the event delivery rate.

        final float alpha = 0.8f;

        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = event.values[0] - gravity[0]; // X
        linear_acceleration[1] = event.values[1] - gravity[1]; // Y
        linear_acceleration[2] = event.values[2] - gravity[2]; // Z
        isMaxAcceleration();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void isMaxAcceleration() {
        String[] axis = new String[] {"X", "Y", "Z"};
        for (int i = 0; i<3; i++) {
            if (linear_acceleration[i] > max_acceleration[i]) {
                max_acceleration[i] = linear_acceleration[i];
                String ahora = Calendar.getInstance().getTime().toLocaleString();
                String mensaje = "Hora Maximo "+axis[i]+": "+ahora
                        +"\nMagnitud: "+max_acceleration[i];
                textViews[i].setText(mensaje);
            }
        }
    }
}
