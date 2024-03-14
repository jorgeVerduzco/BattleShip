import java.util.Scanner;
import java.util.List;


public class TerminalBattleship {
    public BattleshipModel model;
    public Scanner scanner;

    public TerminalBattleship() {
        model = new BattleshipModel();
        scanner = new Scanner(System.in);
    }

    public void chooseShipPlacementMethod() {
        System.out.println("Choose ship placement method: ");
        System.out.println("1. Randomly place ships");
        System.out.println("2. Manually place ships");
        System.out.println("Enter choice(1 or 2): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if(choice == 1) {
            model.placeShipsRandomlyOnUserBoard();
            model.placeShipsRandomlyOnOpponentBoard();
        } else if (choice == 2) {
            model.placeShipsManuallyUserBoard();
            model.placeShipsRandomlyOnOpponentBoard();
        } else {
            System.out.println("Invalid choice, please enter 1 or 2.");
            chooseShipPlacementMethod();
        }
    }

    public void makeAttack() {
        System.out.println("Enter coordinates for attack (row column): ");
        model.attack();
    }

    public void startGame() {
        System.out.println("Welcome to Battleship!");

        chooseShipPlacementMethod();
        System.out.println("Ships have been placed.");

        while(!model.checkWinner()) {
            System.out.println("\nYour board: ");
           model.printUserBoard();

           System.out.println("\nOpponent's board: ");
           model.printOpponentBoard();

            makeAttack();
        }
        System.out.println("Game over");
    }
   
    public static void main(String[] args) {
        TerminalBattleship game = new TerminalBattleship();
        game.startGame();
    }
}