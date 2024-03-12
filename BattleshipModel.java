package BattleshipTest;

import java.util.Random;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BattleshipModel {
    private char[][] userBoard;
    private char[][] opponentBoard;
    private Ship[][] opponentShipPositions;
    private final int size = 10;
    private List<Ship> playerShips;
    private List<Ship> opponentShips = new ArrayList<>();
    private List<Ship> recentlySunkShips = new ArrayList<>();
    private Random random = new Random();

    public BattleshipModel() {
        userBoard = new char[size][size];
        opponentBoard = new char[size][size];
        opponentShipPositions = new Ship[size][size];
        playerShips = new ArrayList<>();
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

  public boolean cellUsed(int row, int col) {
    Ship ship = opponentShipPositions[row][col];
    if(ship != null) {
        boolean hit = ship.markHit(row, col);
        opponentBoard[row][col] = hit ? 'H' : 'M';
        if(hit && ship.isSunk()) {
            if(!recentlySunkShips.contains(ship)) {
                recentlySunkShips.add(ship);
            }
        }
        return hit;
    } else {
        opponentBoard[row][col] = 'M';
        return false;
    }
  }

  public void checkForSunkShip(Ship ship) {
    if(ship.isSunk()) {
        if(!recentlySunkShips.contains(ship)) {
            recentlySunkShips.add(ship);
        }
    }
  }
  public Ship getRecentlySunkShip() {
   if(!recentlySunkShips.isEmpty()) {
    return recentlySunkShips.get(recentlySunkShips.size() - 1);
   }
   return null;
}

  public List<Ship> getAndClearRecentlySunkShips() {
    List<Ship> sunkShips = new ArrayList<>(recentlySunkShips);
    recentlySunkShips.clear();
    return sunkShips;
  }
  public char[][] getOpponentBoard() {
    return opponentBoard;
  }
  public Ship[][] getOpponentShipPosition() {
    return opponentShipPositions;
  }
  public boolean areAllShipsSunk() {
    return opponentShips.stream().allMatch(Ship :: isSunk);
  }

    private void initializeShips(List<Ship> ship)
    {
        ship.add(new Ship(null, size, size, size, isGameOver()));
        ship.add(new Ship(null, size, size, size, isGameOver()));
        ship.add(new Ship(null, size, size, size, isGameOver()));
        ship.add(new Ship(null, size, size, size, isGameOver()));
        ship.add(new Ship(null, size, size, size, isGameOver()));
        ship.set(0,new Ship("Carrier", 5, 0, 0, false));
        ship.set(1,new Ship("Battleship", 4, 0, 0, false));
        ship.set(2,new Ship("Cruise", 3, 0, 0, false));
        ship.set(3,new Ship("Submarine", 3, 0, 0, false));
        ship.set(4,new Ship("Destroyer", 2, 0, 0, false));
        
        
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
                    Ship newShip = new Ship(shipTypes[i], shipSize[i], row, col, isHorizontal);

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
                    Ship newShip = new Ship(shipTypes[i], shipSize[i], row, col, isHorizontal);

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
                this.opponentShipPositions[row][col + i] = ship;
            } else {
                board[row + i][col] = 'S';
                this.opponentShipPositions[row + i][col] = ship;
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
   
    private void markBoard(char[][] board, int row, int col, boolean hit) {
        if(row >= 0 && row < size && col >= 0 && col < size) {
            board[row][col] = hit ? 'H' : 'M';
        }
        
    }
    public char[][] getUserBoard() {
        return userBoard;
    }
}