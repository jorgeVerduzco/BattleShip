package Game;

public class BattleshipModel {
    char board[][];
    int numberOfBoats = 5;
    Boat battleBoats[];

    public class Boat {
        public int size;
        public String shipName;
        public char symbol;
        public int livecells;
        public String orientation = "horizontal";

        public Boat(String name, int size, char symbol, int livecells) {
            this.shipName = name;
            this.size = size;
            this.symbol = symbol;
        }
    }

    public BattleshipModel () {
        board = new char[7][8];
        battleBoats = new Boat[5];
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
        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 8; j++) {
                board[i][j] = ' ';
            }
        }
    }
    public void rotateShip(Boat ship) {
        ship.orientation = ship.orientation.equals("horizontal") ? "vertical" : "horizontal";
    }
    public boolean placeBoat(int row, int col, Boat ship) {
        if(ship.orientation.equals("horizontal")) {
            if(col + ship.size > board[0].length) return false;
            for(int i = 0; i < ship.size; i++) {
                board[row][col + i] = ship.symbol;
            }
        } else {
            if(row + ship.size > board.length) return false;
            for(int i = 0; i < ship.size; i++) {
                board[row + i][col] = ship.symbol;
            }
        }
        return true;
    }
    public void playTurn(int row, int col) {
        if(board[row][col] == ' ') {
            board[row][col] = 'm';
        } 
        else 
        {
            for(int i = 0; i < 5; i++)
            {
                if(board[row][col] == battleBoats[i].symbol)
                {
                    battleBoats[i].livecells = battleBoats[i].livecells-1;
                    if(battleBoats[i].livecells == 0)
                    {
                        numberOfBoats = numberOfBoats-1;
                    }
                }
            }
            board[row][col] = 'h';

            
        }
    }
    public boolean checkWin() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                for(Boat boat : battleBoats) {
                    if(board[i][j] == boat.symbol) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}