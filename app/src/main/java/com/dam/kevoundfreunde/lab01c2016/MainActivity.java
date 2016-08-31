package com.dam.kevoundfreunde.lab01c2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    int cantDias;
    int importe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button botonPlazo = (Button) findViewById(R.id.buttonPlazoFijo);
        botonPlazo.setOnClickListener(this);

        SeekBar seekBarDia = (SeekBar) findViewById(R.id.seekBarDias);
        seekBarDia.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText importeField = (EditText) findViewById(R.id.inputImporte);
        importe = Integer.parseInt((String)importeField.getText());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        cantDias = i;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
