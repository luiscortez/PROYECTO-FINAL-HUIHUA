package com.example.jpcanocor.tresenraya_jp;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Juan Pedro Cano on 20/11/2015.
 */
public class PreferencesFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }

}
