package com.example.owner.movieapp;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Owner on 17-Oct-16.
 */

public class prefs extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }
}
