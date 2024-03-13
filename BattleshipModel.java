//package GameTest;

import java.util.Random;
import java.awt.Image;
import java.sql.ShardingKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BattleshipModel {
    private char[][] userBoard;
    private char[][] opponentBoard;
    private final int size = 10;
    private List<Ship> playerShips;
    private List<Ship> opponentShips;
    private Random random;

    public BattleshipModel() {
        this.random = new Random();
        userBoard = new char[size][size];
        opponentBoard = new char[size][size];
        playerShips = new ArrayList<>();
        opponentShips = new ArrayList<>();
        initializeBoard(userBoard);
        initializeBoard(opponentBoard);
        initializeShips(opponentShips);
        //initializeShips(playerShips);
        //randomizeShips();
        randomizeOpponentsShips();
        displayBoard(opponentBoard);
        System.out.println("");
        displayBoard(userBoard);
    }

    private void initializeBoard(char[][] board) {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                board[i][j] = '.';
            }
        }
    }

    public void displayBoard(char[][] board) {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public boolean areAllShipsSunk() {
        for(Ship ship : opponentShips) {
            if(!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    // public boolean placeShipManually(String shipType, int row, int col, boolean isHorizontal, int cells){
    //     int shipSize = getShipSize(shipType);
    //     if(shipSize == -1) return false;
    //     Ship newShip = new Ship(shipType, shipSize, row, col, isHorizontal, cells, '\0');
    //     if(!isValidPlacement(newShip, userBoard)) {
    //         return false;
    //     }
        
    //     playerShips.add(newShip);
    //     markShipOnBoard(newShip, userBoard);
    //     return true;
    // }

    private int getShipSize(String shipType) {
        switch (shipType) {
            case "Carrier": return 5;
            case "Battleship" : return 4;
            case "Cruise": return 3;
            case "Submarine": return 3;
            case "Destroyer": return 2;
            default: return -1; 
        }
    }

    private void initializeShips(List<Ship> ship)
    {
        ship.add(new Ship("Carrier", 5, 0, 0, false, 5, '\0'));
        ship.add(new Ship("Battleship", 4, 0, 0, false, 4, '\0'));
        ship.add(new Ship("Cruise", 3, 0, 0, false, 3, '\0'));
        ship.add(new Ship("Submarine", 3, 0, 0, false, 3, '\0'));
        ship.add(new Ship("Destroyer", 2, 0, 0, false, 2, '\0'));
    }

    private void randomizeShips() {
        char[] shipSymbols = {'C', 'B', 'R', 'S', 'D'};
        int [] shipSize = {5,4,3,3,2};
        String[] shipTypes = {"Carrier", "Battleship", "Cruise", "Submarine" , "Destroyer"};
        

        // for ship in ships
        // generate random row and col
        // UPDATE THE SHIPS ROW AND COL
        // generate horizontal or vertical
        // check if there's collision
        // place board on ship if there isn't, if there is then run again
        for (int i = 0; i < 5; i++ ) {
            boolean placed = false;
            while(!placed) {
                int row = random.nextInt(size-1);
                int col = random.nextInt(size-1);
                boolean isHorizontal = random.nextBoolean();
                playerShips.set(i, new Ship(shipTypes[i], shipSize[i], row, col, isHorizontal, shipSize[i], shipSymbols[i]));
            }
        }
    }

    private void randomizeOpponentsShips() {
        char[] shipSymbols = {'C', 'B', 'R', 'S', 'D'};
        int [] shipSize = {5,4,3,3,2};
        String[] shipTypes = {"Carrier", "Battleship", "Cruise", "Submarine" , "Destroyer"};
        

        // for ship in ships
        // generate random row and col
        // UPDATE THE SHIPS ROW AND COL
        // generate horizontal or vertical
        // check if there's collision
        // place board on ship if there isn't, if there is then run again
        for (int i = 0; i < 5; i++ ) {
            boolean placed = false;
            while(!placed) {
                int row = random.nextInt(size-1);
                int col = random.nextInt(size-1);
                boolean isHorizontal = random.nextBoolean();
                Ship newShip = new Ship(shipTypes[i], shipSize[i], row, col, isHorizontal, shipSize[i], shipSymbols[i]);

                if (!isCollision(newShip, opponentBoard)) {
                    opponentShips.set(i, newShip);
                    markShipOnBoard(newShip, opponentBoard);
                    placed = true;
                }

            }
        }

    }

    private boolean isCollision(Ship ship, char[][] board) {        
            System.out.println("in collision");
            int row = ship.getStartCoordinates()[0];
            int col = ship.getStartCoordinates()[1];

            if (ship.isHorizontal()) {
                System.out.printf("\nCol + ShipSize: %d\n", col + ship.getSize());
                if (col + ship.getSize() > size) return true; // Out of bounds
                for (int i = 0; i < ship.getSize(); i++) {
                    if (board[row][col + i] == 'C' || board[row][col + i] == 'B' || board[row][col + i] == 'R' || board[row][col + i] == 'S' || board[row][col + i] == 'D') return true; // Collision detected
                }
            } else {
                System.out.printf("\nRow + ShipSize: %d\n", row + ship.getSize());
                if (row + ship.getSize() > size) return true; // Out of bounds
                for (int i = 0; i < ship.getSize(); i++) {
                    if (board[row + i][col] == 'C' || board[row + i][col] == 'B' || board[row + i][col] == 'R' || board[row + i][col] == 'S' || board[row + i][col] == 'D') return true; // Collision detected
                }
            }
        return false; // No collision or out of bounds
    }
    
    private boolean isValidPlacement(Ship ship, char[][]board) {
        System.out.println("in placement");
        return !isCollision(ship, board);
    }

    private void markShipOnBoard(Ship ship, char[][] board) {
        int row = ship.getStartCoordinates()[0];
        int col = ship.getStartCoordinates()[1];
        for(int i = 0; i < ship.getSize(); i++) {
            System.out.printf("%d", i);

            if(ship.isHorizontal()) {
                System.out.printf("HRow: %d\nHCol: %d\n", row, col+i);
                if (col >= 9) {
                    col = 8;
                }
                board[row][col + i] = ship.getShipSymbol();
            } else {
                System.out.printf("vRow: %d\n, vRow+i: %d\nvCol: %d\n", row, row + i, col);
                if (row >= 9) {
                    row = 8;
                }
                System.out.printf("2nd vRow: %d\n", row+i);
                board[row + i][col] = ship.getShipSymbol();
            }
        }
    }

    public boolean isGameOver() {
        return allShipsSunk(playerShips) || allShipsSunk(opponentShips);
    }

    private boolean allShipsSunk(List<Ship> ships) {
        for(Ship ship : ships) {
            if(!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    public void markUserBoard(int row, int col, boolean hit) {
       markBoard(userBoard, row, col, hit);
    }

    public void markOpponentBoard(int row, int col, boolean hit) {
        markBoard(opponentBoard, row, col, hit);
    }

    private void markBoard(char[][] board, int row, int col, boolean hit) {
        if(row >= 0 && row < size && col >= 0 && col < size) {
            board[row][col] = hit ? 'H' : 'M';
        }   
    }

    public char[][] getUserBoard() {
        return userBoard;
    }
    public char[][] getOpponentBoard() {
        return opponentBoard;
    }

    public boolean cellUsed(int row, int col)
    {
        if(opponentBoard[row][col] == 'S' || opponentBoard[row][col] == 'H')
            return true;

        return false;
    }

   public Ship getRecentlySunkShip() {
    for (Ship ship : opponentShips) {
        if (ship.checkAndMarkSunk()) {
            return ship; // Return the recently sunk ship
        }
    }
    return null; // No new ships have been sunk since the last check
 }
}