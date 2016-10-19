package com.dam.kevoundfreunde.laboratorio04;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Kevo on 18/10/2016.
 */
public class ReceptorAlarma extends BroadcastReceiver {
    private static final String tag = "ReceptorAlarma";

    @Override
    public void onReceive(Context context, Intent intent){
        Log.v("ReceptorAlarma", "Entro al receptor onReceive ReceptorAlarma");
        Toast.makeText(context,
                "Alarma recibida", Toast.LENGTH_SHORT).show();
    }
}
