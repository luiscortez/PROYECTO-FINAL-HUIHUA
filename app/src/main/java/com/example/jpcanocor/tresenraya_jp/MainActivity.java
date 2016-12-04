package com.example.jpcanocor.tresenraya_jp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

    MediaPlayer mplayer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        if (pref.getBoolean("music", true)) {
            mplayer = MediaPlayer.create(this, R.raw.audio);
            mplayer.start();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        if (pref.getBoolean("music", true)) {
            if (mplayer == null) {
                mplayer = MediaPlayer.create(this, R.raw.audio);
            }
            mplayer.start();
        } else {
            if (mplayer != null) {
                mplayer.pause();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mplayer != null) {
            mplayer.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // Método para guardar el estado de la actividad
    @Override
    protected void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);

        if (mplayer != null) {
            int musicPos = mplayer.getCurrentPosition();
            currentState.putInt("Music", musicPos);
        }
    }

    // Método para recuperar el estado de vida guardado de la actividad
    @Override
    protected void onRestoreInstanceState(Bundle saveState) {
        super.onRestoreInstanceState(saveState);

        if (mplayer != null && saveState != null) {
            int musicPos = saveState.getInt("Music");
            mplayer.seekTo(musicPos);
        }
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
            userSettingsDown(null);
            return true;
        }

        // Da funcionalidad al botón 'Acerca de' del menú
        if (id == R.id.aboutOf) {
            aboutOfDown(findViewById(R.id.aboutOf));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startGame(View v) {
        Intent intent = new Intent(this, VistaJuego.class);
        intent.putExtra("nPlayers", v.getTag().toString());
        startActivity(intent);
    }

    // Método para lanzar la actividad del botón Acerca de
    public void aboutOfDown(View v) {
        Intent intent = new Intent(this, AboutOf.class);
        startActivity(intent);
    }

    // Método para lanzar la actividad del botón ¿Cómo se juega?
    public void howToPlay(View v) {
        Intent intent = new Intent(this, HowToPlay.class);
        startActivity(intent);
    }



    // Método para lanzar la actividad del botón Preferencias
    public void userSettingsDown(View v) {
        Intent intent = new Intent(this, UserSettings.class);
        startActivity(intent);
    }

}
