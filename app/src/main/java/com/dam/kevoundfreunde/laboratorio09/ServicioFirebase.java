package com.dam.kevoundfreunde.laboratorio09;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Juxtar on 23/02/2017.
 */

public class ServicioFirebase extends FirebaseInstanceIdService {
    final String TAG = "ServicioFirebase";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "Servicio creado!");
    }

    @Override
    public void onTokenRefresh() {
        // obtener token InstanceID
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        saveTokenToPrefs(refreshedToken);
        Log.v(TAG, "Recibido token.");
    }

    private void saveTokenToPrefs(String _token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("registration_id", _token);
        editor.apply();
        // Luego en cualquier parte de la aplicación pondremos recuperar el token con
        // SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        // preferences.getString("registration_id", null);
    }
}
