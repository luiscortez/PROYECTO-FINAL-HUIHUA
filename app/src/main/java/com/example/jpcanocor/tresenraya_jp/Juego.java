package com.example.jpcanocor.tresenraya_jp;

/**
 * Created by Juan Pedro Cano on 16/11/2015.
 */
public class Juego {

    private int board[][];
    static int turn = -1;
    private int counter = -1;
    static final int ROWS = 3;
    static final int COLUMNS = 3;
    static final int FREE = 0;
    static final int PLAYER1 = 1;
    static final int PLAYER2 = 20;
    static final int ANDROID = 35;
    static final int FINISHED_GAME = 99;
    static final int DRAW_GAME = 98;

    public Juego() {

        // Creación del tablero donde guardar el estado del juego
        board = new int[ROWS][COLUMNS];
        // Inicialización del array con todos los elementos sin usar.
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                board[i][j] = FREE;
            }
        }
    }

    boolean selectableCell(int i, int j) {

        // Comprueba si es posible usar esa celda
        if (board[i][j] == FREE) {
            board[i][j] = turn;
            return true;
        } else {
            return false;
        }
    }

    public int whoPlays(int player2) {
        // Si la partida no ha finalizado, turno para el otro jugador
        if (!checkFinish()) {
            counter += 1;
            if (counter < 9) {
                switch (turn) {
                    case -1:
                        turn = (int) Math.round(Math.random()) + 1;
                        if (turn == 2 && player2 == 20) {
                            turn = PLAYER2;
                        } else if (turn == 2 && player2 == 35) {
                            turn = ANDROID;
                        }
                        break;
                    case PLAYER1:
                        turn = player2;
                        break;
                    default:
                        turn = PLAYER1;
                        break;
                }
            } else {
                turn = DRAW_GAME;
            }
        } else {
            turn = FINISHED_GAME;
        }
        return turn;
    }

    public boolean checkFinish() {

        int[] rows = {0, 0, 0};
        int[] columns = {0, 0, 0};
        int[] diagonals = {0, 0};

        for (int i = 0; i < ROWS; i++) {
            rows[i] = board[i][0] + board[i][1] + board[i][2];
            if (rows[i] == 3 || rows[i] == 60 || rows[i] == 105) {
                return true;
            }
        }
        for (int j = 0; j < COLUMNS; j++) {
            columns[j] = board[0][j] + board[1][j] + board[2][j];
            if (columns[j] == 3 || columns[j] == 60 || columns[j] == 105) {
                return true;
            }
        }
        diagonals[0] = board[0][0] + board[1][1] + board[2][2];
        diagonals[1] = board[2][0] + board[1][1] + board[0][2];
        for (int d = 0; d < 2; d++) {
            if (diagonals[d] == 3 || diagonals[d] == 60 || diagonals[d] == 105) {
                return true;
            }
        }

        return false;
    }


    public void playMachine() {

        int row;
        int column;

        if (checkRow(2 * ANDROID)) {
            return;
        }

        if (checkColumn(2 * ANDROID)) {
            return;
        }

        if (checkDiagonal(2 * ANDROID)) {
            return;
        }
        if (checkRow(2 * PLAYER1)) {
            return;
        }

        if (checkColumn(2 * PLAYER1)) {
            return;
        }

        if (checkDiagonal(2 * PLAYER1)) {
            return;
        }

        do {
            row = ((int) (Math.random() * 100)) % 3;
            column = ((int) (Math.random() * 100)) % 3;
        } while (!selectableCell(row, column));
    }

    private boolean checkDiagonal(int sum) {
        int[] diagonals = {0, 0};

        diagonals[0] = board[0][0] + board[1][1] + board[2][2];
        diagonals[1] = board[2][0] + board[1][1] + board[0][2];
        if (diagonals[0] == sum) {
            selectableCell(0,0);
            selectableCell(1,1);
            selectableCell(2,2);
            return true;
        }
        if (diagonals[1] == sum) {
            selectableCell(2,0);
            selectableCell(1,1);
            selectableCell(0,2);
            return true;
        }
        return false;
    }

    private boolean checkRow(int sum) {
        int[] rows = {0, 0, 0};

        for (int i = 0; i < ROWS; i++) {
            rows[i] = board[i][0] + board[i][1] + board[i][2];
            if (rows[i] == sum) {
                for (int col = 0; col < 3; col++) {
                    if (selectableCell(i, col)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkColumn(int sum) {
        int[] columns = {0, 0, 0};

        for (int j = 0; j < COLUMNS; j++) {
            columns[j] = board[0][j] + board[1][j] + board[2][j];
            if (columns[j] == sum) {
                for (int ro = 0; ro < 3; ro++) {
                    if (selectableCell(ro, j)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getBoard(int i, int j) {
        return board[i][j];
    }

}
