package com.dam.kevoundfreunde.lab01c2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    int cantDias = 0;
    int importe = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String mensajeGanancia = getString(R.string.labelGananciaNum, 0.0);
        TextView mensajeGananciaText = (TextView) findViewById(R.id.labelGananciaNum);
        mensajeGananciaText.setText(mensajeGanancia);
        TextView successText = (TextView) findViewById(R.id.successText);
        successText.setVisibility(View.INVISIBLE);


        Button botonPlazo = (Button) findViewById(R.id.buttonPlazoFijo);
        botonPlazo.setOnClickListener(this);

        SeekBar seekBarDia = (SeekBar) findViewById(R.id.seekBarDias);
        seekBarDia.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View view) {

        double valor = mostrarGanancias();
        Log.v("valor", "valor: " + valor);
        valor += importe;
        String mensaje = getString(R.string.labelSuccess, valor);
        TextView successText = (TextView) findViewById(R.id.successText);
        successText.setText(mensaje);
        successText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        cantDias = i;
        TextView diasText = (TextView) findViewById(R.id.cantidadDiasText);
        diasText.setText(String.valueOf(cantDias));
        mostrarGanancias();
    }

    private double mostrarGanancias(){
        EditText importeField = (EditText) findViewById(R.id.inputImporte);
        try {
            importe = Integer.parseInt(importeField.getText().toString());
        }
        catch (NumberFormatException e){
            importe = 0;
        }
        double valor = calcularImporte(importe,cantDias);
        TextView gananciaText = (TextView) findViewById(R.id.labelGananciaNum);
        String mensajeGananciaNum = getString(R.string.labelGananciaNum, valor);
        gananciaText.setText(mensajeGananciaNum);
        TextView successText = (TextView) findViewById(R.id.successText);
        successText.setVisibility(View.INVISIBLE);
        return valor;

    }

    private double calcularImporte(int importe, int cantDias) {
        int techo1 = Integer.parseInt(getResources().getString(R.string.techo1));
        int techo2 = Integer.parseInt(getResources().getString(R.string.techo2));
        int dias = Integer.parseInt(getResources().getString(R.string.dias));
        float tasa1 = Float.parseFloat(getResources().getString(R.string.tasa1));
        float tasa2 = Float.parseFloat(getResources().getString(R.string.tasa2));
        float tasa3 = Float.parseFloat(getResources().getString(R.string.tasa3));
        float tasa4 = Float.parseFloat(getResources().getString(R.string.tasa4));
        float tasa5 = Float.parseFloat(getResources().getString(R.string.tasa5));
        float tasa6 = Float.parseFloat(getResources().getString(R.string.tasa6));

        if (importe<techo1){
            if(cantDias<dias){
                return calcularImporteAux(tasa1,importe,cantDias);
            }
            else{
                return calcularImporteAux(tasa2,importe,cantDias);
            }
        }
        else if (importe>techo1 && importe<techo2){
            if(cantDias<dias){
                return calcularImporteAux(tasa3,importe,cantDias);
            }
            else{
                return calcularImporteAux(tasa4,importe,cantDias);
            }
        }
        else {
            if(cantDias<dias){
                return calcularImporteAux(tasa5,importe,cantDias);
            }
            else{
                return calcularImporteAux(tasa6,importe,cantDias);
            }
        }
    }

    private double calcularImporteAux(float tasa, int importe, int cantDias) {
        return importe*((Math.pow(1+(tasa/100.0),(cantDias/360.0))-1));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
