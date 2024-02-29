package GameTest;

import java.util.Random;

public class BattleshipModel {
    public char board[][];
    public Boat[] battleBoats;
    public final int boardRows = 7;
    public final int boardCols = 8;
    public final int numberOfBoats = 5;

    public class Boat {
        public int size;
        public String shipName;
        public char symbol;
        public int livecells;
        public String orientation = "horizontal";
        public boolean isSunk;

        public Boat(String name, int size, char symbol, int livecells) {
            this.shipName = name;
            this.size = size;
            this.symbol = symbol;
        }
    }

    public BattleshipModel () {
        board = new char[boardRows][boardCols];
        battleBoats = new Boat[numberOfBoats];
        initializeBoats();
        initializeBoard();
    }

    private void initializeBoats() {
        battleBoats[0] = new Boat("carrier", 5, 'c',5);
        battleBoats[1] = new Boat("battleship", 4, 'b',4);
        battleBoats[2] = new Boat("cruiser", 3, 'r',3);
        battleBoats[3] = new Boat("submarine", 3, 's',3);
        battleBoats[4] = new Boat("destroyer", 2, 'd',2);
    }

    private void initializeBoard() {
        for(int i = 0; i < boardRows; i++) {
            for(int j = 0; j < boardCols; j++) {
                board[i][j] = ' ';
            }
        }
    }
    public void rotateShip(Boat ship) {
        ship.orientation = ship.orientation.equals("horizontal") ? "vertical" : "horizontal";
    }

    public void placeRandom() {
        Random rand = new Random();
        for(Boat boat : battleBoats) {
            boolean placed = false;
            while(!placed) {
                int row = rand.nextInt(board.length);
                int col = rand.nextInt(board[0].length);
                String orientation = rand.nextBoolean() ? "horizontal" : "vertical";
                boat.orientation = orientation;
                placed = placeBoat(row, col, boat,true);
            }

        }
       
    }
    public boolean placeBoat(int row, int col, Boat boat, boolean horizontal) {
        if(horizontal) {
            if(col + boat.size > boardCols) return false;
            for(int i = 0; i < boat.size; i++) {
                if(board[row][col + i] != ' ') return false;
            }
            for(int i =0; i < boat.size; i++) {
                board[row][col + i] = boat.symbol;
            }
        }else {
            if(row + boat.size > boardRows) return false;
            for(int i = 0; i < boat.size; i++) {
                if(board[row + i][col] != ' ') return false;
            }
            for(int i = 0; i < boat.size; i++) {
                board[row + i][col] = boat.symbol;
            }
        }
        return true;
    }

    //checks if cell is empty: checks if cell has been used at all
    public boolean isSquareOpen(int row, int col) {
         if(board[row][col] == ' ')
         {
            return true;
         }
         else
         return false;
    }
    public void playTurn(int row, int col) {
        if(board[row][col] == ' ') {
            board[row][col] = 'm';
        } 
        else  if(board[row][col] != ' '){
            char hitSymbol = board[row][col];
            board[row][col] = 'H';
            System.out.println("ship hit");
            
            boolean isSunk = true;
            outerLoop :
            for(int i = 0; i < boardRows; i++) {
                for(int j = 0; j < boardCols; j++) {
                    if(board[i][j] == hitSymbol) {
                        isSunk = false;
                        break outerLoop;
                    }
                }
            }
            if(isSunk) {
                for(Boat boat : battleBoats) {
                    if(boat.symbol == hitSymbol) {
                        boat.isSunk = true;
                        System.out.println(boat.shipName + "has been sunk!");
                        break;
                    }
                }
            }
            
        }
        
    }

    public char getBoardSquareStatus(int row, int col) {
        return board[row][col];
    }
    public boolean checkWin() {
        for(int row = 0; row < boardRows; row++) {
            for(int col = 0; col < boardCols; col++) {
                for(Boat boat : battleBoats) {
                    if(board[row][col] == boat.symbol) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}