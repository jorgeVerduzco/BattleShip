package Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.Serializable;

public class BattleshipModel {
    private final int size = 10;
    private char[][] userBoard;
    private char[][] opponentBoard;
    private int numUserBoats;
    private int numOpponentBoats;
    private List<Ship> ships;
    private List<Ship> Opponentships;
    private Random random = new Random();
    private Scanner scanner;

    public BattleshipModel() {
        userBoard = new char[size][size];
        opponentBoard = new char[size][size];
        numUserBoats = 5;
        numOpponentBoats = 5;
        ships = new ArrayList<>();
        Opponentships = new ArrayList<>();
        scanner = new Scanner(System.in);
        clearBoard(userBoard);
        clearBoard(opponentBoard);
        initializeShips();
    }

    private void clearBoard(char[][] board) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '.';
            }
        }
    }

    private void initializeShips() {
        ships.add(new Ship("Carrier", 5, 'C'));
        ships.add(new Ship("Battleship", 4, 'B'));
        ships.add(new Ship("Cruiser", 3, 'R'));
        ships.add(new Ship("Submarine", 3, 'S'));
        ships.add(new Ship("Destroyer", 2, 'D'));
        Opponentships.add(new Ship("Carrier", 5, 'C'));
        Opponentships.add(new Ship("Battleship", 4, 'B'));
        Opponentships.add(new Ship("Cruiser", 3, 'R'));
        Opponentships.add(new Ship("Submarine", 3, 'S'));
        Opponentships.add(new Ship("Destroyer", 2, 'D'));

    }
    public void placeShipsRandomlyOnUserBoard() {
        placeShipsRandomly(userBoard);
    }

    public void placeShipsRandomlyOnOpponentBoard() {
        placeShipsRandomly(opponentBoard);
    }

    public void placeShipsRandomly(char[][] board) {
        ships.forEach(ship ->{
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(size);
                int col = random.nextInt(size);
                char orientation = random.nextBoolean() ? 'H' : 'V';
                if (isValidPlacement(ship, row, col, orientation, board)) {
                    placeShip(ship, row, col, orientation, board);
                    placed = true;
                }
            }
        });
    }

    public void placeShipsManuallyUserBoard() {
        manualShipPlacement(userBoard);
    }
    
    public void manualShipPlacement(char[][] board) {
        ships.forEach(ship -> {
            boolean placed = false;
            while (!placed) {
                System.out.println("Placing " + ship.getName() + " (" + ship.getSize() + "):");
                System.out.print("Enter row, column, and orientation (H/V): ");
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                char orientation = scanner.next().toUpperCase().charAt(0);

                if (isValidPlacement(ship, row, col, orientation, board)) {
                    placeShip(ship, row, col, orientation, board);
                    placed = true;
                } else {
                    System.out.println("Invalid placement. Try again.");
                }
            }
        });
    }

    private boolean isValidPlacement(Ship ship, int row, int col, char orientation, char[][] board) {
      if(orientation == 'H' && (col + ship.getSize() > this.size)) {
        return false;
      }
        if(orientation == 'V' && (row + ship.getSize() > this.size)){
            return false;
        }
        for(int i = 0; i < ship.getSize(); i++) {
            int checkRow = orientation == 'V' ? row + i : row;
            int checkCol = orientation == 'H' ? col + i : col;
            if(board[checkRow][checkCol] != '.') {
                return false;
            }
        }
      return true;
    }

    private void placeShip(Ship ship, int row, int col, char orientation, char[][] board) {
       for(int i = 0; i < ship.getSize(); i ++) {
        int r = row + (orientation == 'V' ? i : 0);
        int c = col + (orientation == 'H' ? i : 0);
        board[r][c] = ship.getIdentifier();
        ship.addPosition(r, c);

       }
    }

    public boolean attack(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            System.out.println("Attack out of bounds.");
            return false;
        }
    
        char cell = opponentBoard[row][col];
        if (cell == '*' || cell == 'X') {
            System.out.println("Miss or already attacked.");
            return false;
        }
    
        if (Character.isUpperCase(cell)) { // It's a hit on a ship
            opponentBoard[row][col] = 'X'; // Mark the hit on the board
            System.out.println("Hit at (" + row + ", " + col + ")");
    
            // Check which ship was hit
            for (Ship ship : Opponentships) {
                if (ship.getIdentifier() == cell) {
                    ship.hit(); // Record the hit
    
                    if (ship.isSunk()) {
                        System.out.println("You sunk the " + ship.getName() + "!");
                        numOpponentBoats--; // Decrease the count of opponent ships
                        if (numOpponentBoats == 0) {
                            System.out.println("All opponent ships have been sunk! You win!");
                        }
                    }
                    break; // Exit the loop once the hit ship is found and processed
                }
            }
            return true; // Attack was successful
        } else {
            opponentBoard[row][col] = '*'; // Mark the miss on the board
            System.out.println("Miss at (" + row + ", " + col + ")");
            return true; // Attack was processed, even though it was a miss
        }
    }
    public void printUserBoard() {
       printBoard(userBoard, true);
    }

    public void printOpponentBoard() {
        printBoard(opponentBoard, false);
     }



    public boolean checkWinner() {
      for(Ship ship : Opponentships) {
        if(!ship.isSunk()) {
            return false;
        }
      }
      return true;
    }

    public void printBoard(char[][] board,boolean showShips) {
        System.out.print("  ");
        for (int i = 0; i < size; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    
        for (int row = 0; row < size; row++) {
            System.out.print(row + " ");
            for (int col = 0; col < size; col++) {
                char cell = board[row][col];
                if(cell == 'X') { // Show hits
                    System.out.print("X ");
                } else if(cell == '*') { // Show misses
                    System.out.print("* ");
                } else if(showShips && Character.isUpperCase(cell)) { // Optionally show ships
                    System.out.print(cell + " ");
                } else {
                    System.out.print(". "); // Show water or hidden ship
                }
            }
            System.out.println();
        }
}

    public void printBoard(char[][] board)
    {
            for (int row = 0; row < size; row++) {
            System.out.print(row + " ");
            for (int col = 0; col < size; col++) {
               System.out.print(board[row][col] + " ");
               
        }
        System.out.println();
    }
}

    public char[][] getUserBoard()
    {
        return userBoard;
    }

    public char[][] getOpponentBoard()
    {
        return opponentBoard;
    }
 }