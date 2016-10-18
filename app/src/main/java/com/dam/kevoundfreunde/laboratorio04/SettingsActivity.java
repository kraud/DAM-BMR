package com.dam.kevoundfreunde.laboratorio04;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Juxtar on 06/10/2016.
 */
public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
