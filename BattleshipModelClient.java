//package Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.Serializable;

public class BattleshipModelClient {
    public final int size = 10;
    public char[][] userBoard;
    public char[][] opponentBoard;
    public int numUserBoats;

    public int numOpponentBoats;
    public List<Ship> ships;
    public List<Ship> Opponentships;
    public Random random = new Random();
    public Scanner scanner;

    public Client client;

    public BattleshipModelClient() {
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
        //client = new Client("127.0.0.1");

        client = new Client("127.0.0.1");
        try {
            client.connectToServer();
            client.getStreams();
            client.input.readObject();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void clearBoard(char[][] board) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '.';
            }
        }
    }

    public void initializeShips() {
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

    public boolean isValidPlacement(Ship ship, int row, int col, char orientation, char[][] board) {
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

    public void placeShip(Ship ship, int row, int col, char orientation, char[][] board) {
       for(int i = 0; i < ship.getSize(); i ++) {
        int r = row + (orientation == 'V' ? i : 0);
        int c = col + (orientation == 'H' ? i : 0);
        board[r][c] = ship.getIdentifier();
        ship.addPosition(r, c);

       }
    }

    public void attack() {
        int row = scanner.nextInt();
        int col = scanner.nextInt();

        while (row < 0 || row >= size || col < 0 || col >= size && (opponentBoard[row][col] == 'X' || opponentBoard[row][col] == '*')) {
            System.out.println("Attack out of bounds.");
            System.out.print("Enter row and column: ");
            row = scanner.nextInt();
            col = scanner.nextInt();
        }
    
        sendShot(row, col);
    }

    public void sendShot(int row, int col) {
        String response = "";
        String rowStr = Integer.toString(row);
        String colStr = Integer.toString(col);
        try {
            client.sendData(rowStr);
            client.sendData(colStr);
            response = (String) client.input.readObject();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }

        boolean rsp;
        if ("hit".equals(response)) {
            rsp = true;
        } else {
            rsp = false;
        }

        markOppBoard(row, col, rsp);

    }


    public void markOppBoard(int row, int col, Boolean response) {
        if (response) {
            opponentBoard[row][col] = 'X';
        } else {
            opponentBoard[row][col] = '*';
        }
    }

    public void receiveShot() {
        String rowStr = "";
        String colStr = "";

        try {
            rowStr = (String) client.input.readObject();
            colStr = (String) client.input.readObject();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }

        int row = Integer.parseInt(rowStr);
        int col = Integer.parseInt(colStr);

        boolean isHit;
        char cell = userBoard[row][col];
        if (Character.isUpperCase(cell) && cell != 'X') {
            isHit = true;
            client.sendData("hit");
        } else {
            isHit = false;
            client.sendData("miss");
        }

        markUserBoard(row, col, isHit);
    }

    public void markUserBoard(int row, int col, Boolean isHit) {
        if (isHit) {
            userBoard[row][col] = 'X';
        } else {
            userBoard[row][col] = '*';
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