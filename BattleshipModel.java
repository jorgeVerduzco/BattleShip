package GameTest;

import java.util.Random;
import java.util.Arrays;

public class BattleshipModel {
    private char[][] userBoard;
    private char[][] opponenetBoard;
    private final int size = 10;

    public BattleshipModel() {
        userBoard = new char[size][size];
        opponenetBoard = new char[size][size];

    }

    private void InitializeBoard(char[][] board) {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                board[i][j] = ' ';
            }
        }
    }
    public boolean markUserBoard(int row, int col, boolean hit) {
        if(row >= 0 && row < size && col >= 0 && col < size) {
            board[row][col] = hit ? 'H' : 'M';
            return true;
        }

    }
   
}