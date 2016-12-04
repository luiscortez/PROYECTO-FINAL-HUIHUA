package com.example.jpcanocor.tresenraya_jp;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Juan Pedro Cano on 20/11/2015.
 */
public class UserSettings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new PreferencesFragment())
                .commit();
    }

}
