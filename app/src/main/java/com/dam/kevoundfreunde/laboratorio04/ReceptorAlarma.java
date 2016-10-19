package com.dam.kevoundfreunde.laboratorio04;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.dam.kevoundfreunde.laboratorio04.modelo.Departamento;

/**
 * Created by Kevo on 18/10/2016.
 */
public class ReceptorAlarma extends BroadcastReceiver {
    private static final String tag = "ReceptorAlarma";

    @Override
    public void onReceive(Context context, Intent intent){
        Bundle bundle = intent.getExtras();
        Departamento depto = (Departamento) bundle.getSerializable("departamento");
        String descripcion = depto.getDescripcion();

        Intent notificationIntent = new Intent(context, AltaReservaActivity.class);
        PendingIntent notificationPIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setContentTitle("Reserva Confirmada")
                .setContentText("Su reserva "+descripcion+", ha sido confirmada. Puede verla en la sección de " +
                        "reservas en su cuenta.")
                .setSmallIcon(R.drawable.ic_menu_send)
                .setContentIntent(notificationPIntent)
                .setAutoCancel(true);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context
                .NOTIFICATION_SERVICE);
        
        manager.notify(1, notificationBuilder.build());
    }
}
