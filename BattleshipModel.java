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
        randomizeShips();
        randomizeOpponentsShips();

    }
    private void initializeBoard(char[][] board) {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                board[i][j] = ' ';
            }
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
    public boolean placeShipManually(String shipType, int row, int col, boolean isHorizontal, int cells){
        int shipSize = getShipSize(shipType);
        if(shipSize == -1) return false;
        Ship newShip = new Ship(shipType, shipSize, row, col, isHorizontal, cells);
        if(!isValidPlacement(newShip, userBoard)) {
            return false;
        }
        
        playerShips.add(newShip);
        markShipOnBoard(newShip, userBoard);
        return true;
    }

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
        ship.add(new Ship(null, size, size, size, isGameOver(),0));
        ship.add(new Ship(null, size, size, size, isGameOver(),0));
        ship.add(new Ship(null, size, size, size, isGameOver(),0));
        ship.add(new Ship(null, size, size, size, isGameOver(),0));
        ship.add(new Ship(null, size, size, size, isGameOver(),0));
        ship.set(0,new Ship("Carrier", 5, 0, 0, false,5));
        ship.set(1,new Ship("Battleship", 4, 0, 0, false,4));
        ship.set(2,new Ship("Cruise", 3, 0, 0, false,3));
        ship.set(3,new Ship("Submarine", 3, 0, 0, false,3));
        ship.set(4,new Ship("Destroyer", 2, 0, 0, false,2));
        
        
    }
    private void randomizeShips() {
        String shipImageStrs[] = new String[5];
        for (int i = 0; i < shipImageStrs.length; i++) {
            if (i % 2 == 0) {
                shipImageStrs[i] = "blueSquare_" + i + ".png";
            }
        }
        int [] shipSize = {5,4,3,3,2};
        String[] shipTypes = {"Carrier", "Battleship", "Cruise", "Submarine" , "Destroyer"};

        for(int i = 0; i < shipSize.length; i++) {
            boolean placed = false;
                while(!placed) {
                    int row = random.nextInt(size-1);
                    int col = random.nextInt(size-1);
                    boolean isHorizontal = random.nextBoolean();
                    Ship newShip = new Ship(shipTypes[i], shipSize[i], row, col, isHorizontal,shipSize[i]);

                    if(isValidPlacement(newShip, userBoard)) {
                        playerShips.add(newShip);
                        markShipOnBoard(newShip, userBoard);
                        placed = true;
                }
           }
        }
    }
     private void randomizeOpponentsShips() {
        int [] shipSize = {5,4,3,3,2};
        String[] shipTypes = {"Carrier", "Battleship", "Cruise", "Submarine" , "Destroyer"};

        for(int i = 0; i < shipSize.length; i++) {
            boolean placed = false;
                while(!placed) {
                    int row = random.nextInt(size-1);
                    int col = random.nextInt(size-1);
                    boolean isHorizontal = random.nextBoolean();
                    Ship newShip = new Ship(shipTypes[i], shipSize[i], row, col, isHorizontal,shipSize[i]);

                    if(isValidPlacement(newShip, opponentBoard)) {
                        opponentShips.add(newShip);
                        markShipOnBoard(newShip, opponentBoard);
                        placed = true;
                }
           }
        }
    }

    private boolean isCollision(Ship newShip, char[][] board) {
         int row = newShip.getStartCoordinates()[0];
    int col = newShip.getStartCoordinates()[1];
    if (newShip.isHorizontal()) {
        if (col + newShip.getSize() > size) return true; // Out of bounds
        for (int i = 0; i < newShip.getSize(); i++) {
            if (board[row][col + i] == 'S') return true; // Collision detected
        }
    } else {
        if (row + newShip.getSize() > size) return true; // Out of bounds
        for (int i = 0; i < newShip.getSize(); i++) {
            if (board[row + i][col] == 'S') return true; // Collision detected
        }
    }
    return false; // No collision or out of bounds
}
    
    private boolean isValidPlacement(Ship newShip, char[][]board) {
        return !isCollision(newShip, board);
    }

    private void markShipOnBoard(Ship ship, char[][] board) {
        int row = ship.getStartCoordinates()[0];
        int col = ship.getStartCoordinates()[1];
        for(int i = 0; i < ship.getSize(); i++) {
            if(ship.isHorizontal()) {
                board[row][col + i] = 'S';
            } else {
                board[row + i][col] = 'S';
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