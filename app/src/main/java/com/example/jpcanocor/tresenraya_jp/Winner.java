package com.example.jpcanocor.tresenraya_jp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Juan Pedro Cano on 16/11/2015.
 */
public class Winner extends Activity {

    private int secondPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner);
        Intent intent = getIntent();
        String winnerPlayerText = intent.getStringExtra("winner");
        secondPlayer = intent.getIntExtra("secondPlayer", Juego.ANDROID);
        TextView textWinner = (TextView) findViewById(R.id.winnerTextView);
        textWinner.setText(winnerPlayerText);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        anotherGame(null);
        return true;
    }

    // Método para lanzar el cuadro de diálogo para solicitar confirmación de borrado
    public void anotherGame(View v) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.anotherGameTitle))
                .setMessage(getString(R.string.anotherGameMessage))
                .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(Winner.this, VistaJuego.class);
                        if (secondPlayer == Juego.ANDROID) {
                            intent.putExtra("nPlayers", "1");
                        } else {
                            intent.putExtra("nPlayers", "2");
                        }
                        startActivity(intent);

                        finish();
                    }
                })
                .setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(Winner.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }

}
