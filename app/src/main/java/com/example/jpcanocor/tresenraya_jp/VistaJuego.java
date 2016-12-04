package com.example.jpcanocor.tresenraya_jp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Juan Pedro Cano on 17/11/2015.
 */
public class VistaJuego extends Activity implements View.OnClickListener {

    private final int cells[][] = {
            {R.id.c00, R.id.c01, R.id.c02},
            {R.id.c10, R.id.c11, R.id.c12},
            {R.id.c20, R.id.c21, R.id.c22}
    };

    private Juego game;
    private int secondPlayer;
    private int player;
    private int lastPlayer;

    private final int ROWS = Juego.ROWS;
    private final int COLUMNS = Juego.COLUMNS;
    private final int FREE = Juego.FREE;
    private final int PLAYER1 = Juego.PLAYER1;
    private final int PLAYER2 = Juego.PLAYER2;
    private final int ANDROID = Juego.ANDROID;
    private final int FINISHED_GAME = Juego.FINISHED_GAME;
    private final int DRAW_GAME = Juego.DRAW_GAME;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablero);

    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView player2Text = (TextView) findViewById(R.id.jugador2TextView);

        Intent intent = getIntent();
        if (intent.getStringExtra("nPlayers").equals("1")) {
            secondPlayer = ANDROID;
            player2Text.setText(R.string.androidTextView);
            player2Text.setRotation(0);
        } else {
            secondPlayer = PLAYER2;
            player2Text.setText(R.string.player2TextView);
        }

        registerListeners();
        game = new Juego();
        drawBoard();
        player = game.whoPlays(secondPlayer);
        alertsPlayer(player);

        if (secondPlayer == ANDROID && player == ANDROID) {
            WaitForMachine waitForMachine = new WaitForMachine();
            waitForMachine.execute();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    class WaitForMachine extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ImageButton button;
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    button = (ImageButton) findViewById(cells[i][j]);
                    button.setClickable(false);
                }
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            SystemClock.sleep(2000);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            game.playMachine();
            drawBoard();
            lastPlayer = player;
            player = game.whoPlays(secondPlayer);
            alertsPlayer(player);
            ImageButton button;
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    button = (ImageButton) findViewById(cells[i][j]);
                    button.setClickable(true);
                }
            }
        }
    }

    private void registerListeners() {
        ImageButton button;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                button = (ImageButton) findViewById(cells[i][j]);
                button.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        buttonDown(v);
    }

    public void buttonDown(View v) {
        int idCell = v.getId();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (cells[i][j] == idCell) {
                    if (game.selectableCell(i, j)) {
                        drawBoard();
                        lastPlayer = player;
                        player = game.whoPlays(secondPlayer);
                        alertsPlayer(player);
                    } else {
                        Toast.makeText(this, R.string.invalidCellToast, Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }

        if (secondPlayer == ANDROID && player == ANDROID) {
            WaitForMachine waitForMachine = new WaitForMachine();
            waitForMachine.execute();
        }
    }

    private void drawBoard() {

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                ImageButton button = (ImageButton) findViewById(cells[i][j]);
                switch (game.getBoard(i, j)) {
                    case FREE:
                        button.setImageResource(R.drawable.libre);
                        break;
                    case PLAYER1:
                        button.setContentDescription(getString(R.string.circleDescrContent));
                        button.setImageResource(R.drawable.circulo);
                        break;
                    case PLAYER2:
                        button.setContentDescription(getString(R.string.crossDescrContent));
                        button.setImageResource(R.drawable.equis);
                        break;
                    case ANDROID:
                        button.setContentDescription(getString(R.string.crossDescrContent));
                        button.setImageResource(R.drawable.equis);
                        break;
                }
            }
        }
    }

    private void alertsPlayer(int t) {
        switch (t) {
            case PLAYER1:
                findViewById(R.id.jugador1TextView).setBackgroundColor(0xFF00FF00);
                findViewById(R.id.jugador2TextView).setBackgroundColor(0x00FF0000);
                break;
            case PLAYER2:
                findViewById(R.id.jugador2TextView).setBackgroundColor(0xFFFF0000);
                findViewById(R.id.jugador1TextView).setBackgroundColor(0x0000FF00);
                break;
            case ANDROID:
                findViewById(R.id.jugador2TextView).setBackgroundColor(0xFFFF0000);
                findViewById(R.id.jugador1TextView).setBackgroundColor(0x0000FF00);
                break;

            case FINISHED_GAME:
                Intent intentFinish = new Intent(this, Winner.class);
                intentFinish.putExtra("winner", winnerName(lastPlayer));
                intentFinish.putExtra("secondPlayer", secondPlayer);
                startActivity(intentFinish);
                break;
            case DRAW_GAME:
                Intent intentDraw = new Intent(this, Winner.class);
                intentDraw.putExtra("winner", winnerName(player));
                intentDraw.putExtra("secondPlayer", secondPlayer);
                startActivity(intentDraw);
                break;
        }
    }


    private String winnerName(int winner) {
        String name = "";
        switch (winner) {
            case PLAYER1:
                name = getResources().getString(R.string.winnerText) + " PLAYER1";
                break;
            case PLAYER2:
                name = getResources().getString(R.string.winnerText) + " PLAYER2";
                break;
            case ANDROID:
                name = getResources().getString(R.string.winnerText) + " ANDROID";
                break;
            case DRAW_GAME:
                name = getResources().getString(R.string.drawText);
        }

        return name;
    }
}


